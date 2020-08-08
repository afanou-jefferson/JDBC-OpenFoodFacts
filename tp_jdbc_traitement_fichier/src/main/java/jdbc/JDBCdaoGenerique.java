package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

/**
 * Classe regroupant les méthodes permettant l'interaction avec la BDD via JDBC
 * @author Exost
 *
 */
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
	public ArrayList<String> insert(Table model) {

		ArrayList<String> bufferRequetes = new ArrayList<String>();

		try {
			if (model.getNomTable() == Produit.class.getSimpleName()) {
				
				Produit newProduit =  (Produit) model;
				
				PreparedStatement insertProduit = connection.prepareStatement(
						"INSERT INTO `produit`(`id_Produit`, `nom_Produit`, `grade_Nutri_Produit`, `id_Categorie`) VALUES (?,?,?,?);");
				insertProduit.setInt(1, newProduit.id_Produit);
				insertProduit.setString(2, newProduit.nom_Produit);
				insertProduit.setString(3, newProduit.grade_nutri_produit);
				insertProduit.setInt(4, newProduit.id_Categorie);
				bufferRequetes.add(NettoyageString.deletePrefixe(insertProduit.toString()));
				
				for (Marque marque : newProduit.listeMarquesDuProduit) {
					PreparedStatement insertJointure = connection.prepareStatement(
							"INSERT INTO `jointure_prod_marque`(`id_produit`, `id_Marque`) VALUES (?,?);");
					insertJointure.setInt(1, newProduit.id_Produit);
					insertJointure.setInt(2, marque.id_Marque);
					bufferRequetes.add(NettoyageString.deletePrefixe(insertJointure.toString()));
				}

				for (Ingredient ingredient : newProduit.listeIngredientsDuProduit) {
					PreparedStatement insertJointure = connection.prepareStatement(
							"INSERT INTO `jointure_prod_ingredient`(`id_produit`, `id_Ingredient`) VALUES (?,?);");
					insertJointure.setInt(1, newProduit.id_Produit);
					insertJointure.setInt(2, ingredient.id_Ingredient);
					bufferRequetes.add(NettoyageString.deletePrefixe(insertJointure.toString()));
				}

				for (Allergene allergene : newProduit.listeAllergenesDuProduit) {
					PreparedStatement insertJointure = connection.prepareStatement(
							"INSERT INTO `jointure_prod_allergene`(`id_produit`, `id_Allergene`) VALUES (?,?);");
					insertJointure.setInt(1, newProduit.id_Produit);
					insertJointure.setInt(2, allergene.id_Allergene);
					bufferRequetes.add(NettoyageString.deletePrefixe(insertJointure.toString()));
				}

				for (Additif additif : newProduit.listeAdditifsDuProduit) {
					PreparedStatement insertJointure = connection.prepareStatement(
							"INSERT INTO `jointure_prod_additif`(`id_produit`, `id_Additif`) VALUES (?,?);");
					insertJointure.setInt(1, newProduit.id_Produit);
					insertJointure.setInt(2, additif.id_Additif);
					bufferRequetes.add(NettoyageString.deletePrefixe(insertJointure.toString()));
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
					bufferRequetes.add(NettoyageString.deletePrefixe(insert.toString()));			}

			} catch (SQLException e) {
				// transformer SQLException en ComptaException
				throw new TraitementFichierException("Erreur à l'insertion de la table : " + model.getNomTable() , e);
			}

		return bufferRequetes;
	}
	
	/**
	 * Execute la liste de requêtes placées en paramètres.
	 * Utilise le batching pour de meilleurs performances.
	 * @param stockRequetes
	 */
	public void insertAll(ArrayList<String> stockRequetes) {

		try {
			this.connection.setAutoCommit(false);
			Statement statement = this.connection.createStatement();
			
			for (String requete : stockRequetes) {
				statement.addBatch(requete);
			}
			statement.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Permet d'obtenir une String à insérer au sein PreparedStatement SQL en fonction du nombre d'attributs de l'objet
	 * @param model
	 * @return String au format (?,?,?,?) si l'objet Table en paramètre dispose de 4 attributs
	 */
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

	/**
	 * Récupère la valeur des l'objet model placée en paramètre et retourne les valeurs de ses attributs au format SQL
	 * @param model
	 * @return String au format (valeurAttribut1, valeurAttribut2, valeurAttribut3)
	 */
	public String attributsToFormatSQL(Table model) {

		String stringAttributs = "(";

		for (String nomAttribut : model.getNomAttributsTable()) {
			stringAttributs += "`" + nomAttribut + "`,";
		}
		stringAttributs = stringAttributs.substring(0, stringAttributs.length() - 1); // Enlever la dernière virgule
		stringAttributs += ")";
		return stringAttributs;
	}

	/**
	 * Interoge la BDD distante pour obtenir tous les enregistrements de la table nomTable placée en paramètres, 
	 * puis les retourne sous forme d'une hashMap.
	 * @param nomTable
	 * @return Hashmap au format <nomTable, objetDeTypeTable>
	 */
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
	
	/**
	 * Intéroge la BDD pour obtenir le plus grand ID unique de la table correspondant à la String nomTable placée en paramètre
	 * @param nomTable
	 * @return integer correspondant au max(ID) de la table nomTable
	 */
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