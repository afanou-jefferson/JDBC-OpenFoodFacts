package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import java.util.logging.Logger;

import exceptions.TraitementFichierException;
import structure_datas.Additif;
import structure_datas.Allergene;
import structure_datas.Categorie;
import structure_datas.Table;
import structure_datas.Ingredient;
import structure_datas.Marque;
import structure_datas.Produit;
import utils.NettoyageString;

public class JDBCdaoGenerique {

	private static final Logger LOGGER = Logger.getLogger(JDBCdaoGenerique.class.getName());

	public Connection connection;

	public JDBCdaoGenerique(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Pour préparer la String qui va servir à insérer l'objet placé en paramètre
	 * 
	 * @param Table
	 * @return Une String qui a pour valeur la requête SQL à exécuter pour insérer
	 *         l'objet en paramètre
	 **/
	public String insert(Table model) {

		String bufferQuery = "";

		try {
			if (model.getNomTable() == Produit.class.getSimpleName()) {
				
				Produit newProduit =  (Produit) model;
				
				PreparedStatement insertProduit = connection.prepareStatement(
						"INSERT INTO `produit`(`id_Produit`, `nom_Produit`, `grade_Nutri_Produit`, `id_Categorie`) VALUES (?,?,?,?);");
				insertProduit.setInt(1, newProduit.id_Produit);
				insertProduit.setString(2, newProduit.nom_Produit);
				insertProduit.setString(3, newProduit.grade_nutri_produit);
				insertProduit.setInt(4, newProduit.id_Categorie);
				bufferQuery = bufferQuery + " " + insertProduit.toString();
				
				for (Marque marque : newProduit.listeMarquesDuProduit) {
					PreparedStatement insertJointure = connection.prepareStatement(
							"INSERT INTO `jointure_prod_marque`(`id_produit`, `id_Marque`) VALUES (?,?);");
					insertJointure.setInt(1, newProduit.id_Produit);
					insertJointure.setInt(2, marque.id_Marque);
					System.out.println(insertJointure.toString());
					bufferQuery = bufferQuery + " " + insertJointure.toString() ;
				}

				for (Ingredient ingredient : newProduit.listeIngredientsDuProduit) {
					PreparedStatement insertJointure = connection.prepareStatement(
							"INSERT INTO `jointure_prod_ingredient`(`id_produit`, `id_Ingredient`) VALUES (?,?);");
					insertJointure.setInt(1, newProduit.id_Produit);
					insertJointure.setInt(2, ingredient.id_Ingredient);
					bufferQuery = bufferQuery + " " +  insertJointure.toString();

				}

				for (Allergene allergene : newProduit.listeAllergenesDuProduit) {
					PreparedStatement insertJointure = connection.prepareStatement(
							"INSERT INTO `jointure_prod_allergene`(`id_produit`, `id_Allergene`) VALUES (?,?);");
					insertJointure.setInt(1, newProduit.id_Produit);
					insertJointure.setInt(2, allergene.id_Allergene);
					bufferQuery = bufferQuery + " " +  insertJointure.toString();
				}

				for (Additif additif : newProduit.listeAdditifsDuProduit) {
					PreparedStatement insertJointure = connection.prepareStatement(
							"INSERT INTO `jointure_prod_additif`(`id_produit`, `id_Additif`) VALUES (?,?);");
					insertJointure.setInt(1, newProduit.id_Produit);
					insertJointure.setInt(2, additif.id_Additif);
					bufferQuery = bufferQuery + " " +  insertJointure.toString();
				}		
			} else {
					StringBuilder builtString = new StringBuilder();
	
					builtString.append("INSERT INTO ").append(model.getNomTable())
							.append(" " + attributsToFormatSQL(model) + " ").append(" VALUES ")
							.append(nbAttributsToFormatSQL(model) + ";");
	
					PreparedStatement insert = connection.prepareStatement(builtString.toString());
					
					for (int i = 1; i <= model.getValeurAttributsTable().size() ; i++) {
						insert.setString(i, model.getValeurAttributsTable().get(i - 1));
					}
					
					bufferQuery = insert.toString();
			}

			} catch (SQLException e) {
				// transformer SQLException en ComptaException
				throw new TraitementFichierException("Erreur à l'insertion de la table : " + model.getNomTable() , e);
			}
		//System.out.println(NettoyageString.deletePrefixe(bufferQuery));
		return NettoyageString.deletePrefixe(bufferQuery);
	}
	
	public void insertAll(String buffedQuery) {

		try {
			PreparedStatement insert = connection.prepareStatement(buffedQuery);
			insert.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int selectIDRowLike(Table model) {

		int idRowAlike = -1;

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ").append(model.getNomTable()).append(" WHERE nom_")
					.append(model.getNomTable()).append(" = ?");
			PreparedStatement selectWhereString = connection.prepareStatement(builtString.toString());
			selectWhereString.setString(1, model.getNomUnique());
			ResultSet result = selectWhereString.executeQuery();
			if (result.next()) {
				idRowAlike = result.getInt(1);
			}
		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
		return idRowAlike;
	}

	public String nbAttributsToFormatSQL(Table model) {
		String nbAttributsToFormatSQL = "(";
		for (int i = 1; i <= model.getNbAttributsTable(); i++) {
			nbAttributsToFormatSQL += "?,";
		}
		nbAttributsToFormatSQL = nbAttributsToFormatSQL.substring(0, nbAttributsToFormatSQL.length() - 1); // Enlever la
																											// dernière
																											// virgule
		nbAttributsToFormatSQL += ")";
		return nbAttributsToFormatSQL;
	}

	public String attributsToFormatSQL(Table model) {

		String stringAttributs = "(";

		for (String nomAttribut : model.getNomAttributsTable()) {
			stringAttributs += "`" + nomAttribut + "`,";
		}
		stringAttributs = stringAttributs.substring(0, stringAttributs.length() - 1); // Enlever la dernière virgule
		stringAttributs += ")";
		return stringAttributs;
	}

	public String getNomFromID(int idACherche, Table model) {
		String nomRow = "ROW INNEXISTANT";

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ").append(model.getNomTable()).append(" WHERE id_")
					.append(model.getNomTable()).append(" = ?");
			PreparedStatement insertRow = connection.prepareStatement(builtString.toString());
			insertRow.setInt(1, idACherche);
			ResultSet result = insertRow.executeQuery();
			if (result.next()) {
				nomRow = result.getString(2);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
		return nomRow;
	}

	/**
	 * Sert à obtenir l'ID de la entite en BDD
	 * 
	 * @param nomEntite
	 * @return Retourne l'id_Cat en BDD de la Entite dont le nom est égal au param
	 */
	public int getIDFromNom(String nomAChercher, Table model) {

		int idRow = -1;
		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ").append(model.getNomTable()).append(" WHERE nom_")
					.append(model.getNomTable()).append(" = ?");
			PreparedStatement selectWhereString = connection.prepareStatement(builtString.toString());
			selectWhereString.setString(1, model.getNomUnique());
			ResultSet result = selectWhereString.executeQuery();

			// System.out.println( "Test controller.getIdFromNom : " +
			// builtString.toString() + " var = " + model.getValeurIdentifiant() );
			if (result.next()) {
				idRow = result.getInt(1);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
		return idRow;
	}

	public boolean rowDejaExistant(String stringAChercher, Table model) {
		boolean bool = false;
		if (getIDFromNom(stringAChercher, model) != 0) {
			bool = true;
		}
		return bool;
	}

	public HashMap<String, Table> selectAllFromTable(String nomTable) {

		HashMap<String, Table> tableExistanteEnBDD = new HashMap<String, Table>();

		String query = "SELECT * FROM " + nomTable;

		try {
			PreparedStatement selectTable = connection.prepareStatement(query);

			ResultSet result = selectTable.executeQuery();

			while (result.next()) {

				switch (nomTable) {
				case "Additif":
					Additif additifEnBDD = new Additif(result.getInt(1), result.getString(2));
					tableExistanteEnBDD.put(NettoyageString.nettoyerString(result.getString(2)), additifEnBDD);
					break;
				case "Allergene":
					Allergene allergeneEnBDD = new Allergene(result.getInt(1), result.getString(2));
					tableExistanteEnBDD.put(NettoyageString.nettoyerString(result.getString(2)), allergeneEnBDD);
				case "Categorie":
					Categorie categorieEnBDD = new Categorie(result.getInt(1), result.getString(2));
					tableExistanteEnBDD.put(NettoyageString.nettoyerString(result.getString(2)), categorieEnBDD);

					break;
				case "Ingredient":
					Ingredient ingredientEnBDD = new Ingredient(result.getInt(1), result.getString(2));
					tableExistanteEnBDD.put(NettoyageString.nettoyerString(result.getString(2)), ingredientEnBDD);
					break;
				case "Marque":
					Marque marqueEnBDD = new Marque(result.getInt(1), result.getString(2));
					tableExistanteEnBDD.put(NettoyageString.nettoyerString(result.getString(2)), marqueEnBDD);
					break;
				}
			}
			return tableExistanteEnBDD;

		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
	}

	public int selectMaxIDfromTable(String nomTable) {

		int maxID = -1;

		String query = "SELECT max(id_" + nomTable + ") FROM " + nomTable + ";";

		try {
			PreparedStatement selectMaxIDTable = connection.prepareStatement(query);
			// System.out.println(selectTable.toString());
			ResultSet result = selectMaxIDTable.executeQuery();

			if (result.next()) {
				maxID = result.getInt(1);
			}

		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		return maxID;
	}

}