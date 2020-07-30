package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import datas.Additif;
import datas.Allergene;
import datas.Categorie;
import datas.Ingredient;
import datas.Marque;
import datas.Produit;
import exceptions.TraitementFichierException;
import utils.ConnectionBDD;

public class JDBCdaoProduit implements IProduitDao {

	public Connection connection;

	public JDBCdaoProduit(Connection connection) {
		this.connection = connection;
	}

	private static final Logger LOGGER = Logger.getLogger(JDBCdaoProduit.class.getName());

	public int insert(String nomProduit, String gradeNutri,int idCategorie ) {
		
		try {
		int idNewProduit = 0;
		
		if (!produitDejaExistant(nomProduit)) {
			PreparedStatement insertProduit = connection.prepareStatement(
					"INSERT INTO `produit`(`nom_Produit`, `grade_Nutri_Produit`, `id_Categorie`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			insertProduit.setString(1, nomProduit);
			insertProduit.setString(2, gradeNutri);
			insertProduit.setInt(3, idCategorie);
			insertProduit.execute();
			ResultSet result = insertProduit.getGeneratedKeys();
			if (result.next()) {
				idNewProduit = result.getInt(1); 
				System.out.println("Test OK");
			}
		}	
		return idNewProduit;
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

	}
	
	@Override
	public void insert(Produit produit) {
		// TODO Auto-generated method stub
		// Connection connection = null;

		try {
	
			// On check déjà que la catégorie est présente pour respecter la contrainte de
			// clé étrangère
			JDBCdaoCategorie daoCategorie = new JDBCdaoCategorie(connection);
			int idCat = daoCategorie.insert(produit.getCategorie());

			if (!produitDejaExistant(produit.getNomProduit())) {
				PreparedStatement insertProduit = connection.prepareStatement(
						"INSERT INTO `produit`(`nom_Produit`, `grade_Nutri_Produit`, `id_Categorie`) VALUES (?,?,?)");
				insertProduit.setString(1, produit.getNomProduit());
				insertProduit.setString(2, produit.getGradeNutri());
				insertProduit.setInt(3, idCat);
				insertProduit.execute();
			}

			int idProduit = getId_Produit(produit.getNomProduit());

			// Si non présente, on insert les caractéristiques de l'objet placé en param
			JDBCdaoMarque daoMarque = new JDBCdaoMarque(connection);
			for (Marque marque : produit.getMarques()) {
				int idMarque = daoMarque.insert(marque);
				PreparedStatement insertJointure = connection
						.prepareStatement("INSERT INTO `jointure_prod_marque`(`id_produit`, `id_Marque`) VALUES (?,?)");
				insertJointure.setInt(1, idProduit);
				insertJointure.setInt(2, idMarque);
				insertJointure.execute();
			}

			JDBCdaoIngredient daoIngredient = new JDBCdaoIngredient(connection);
			for (Ingredient ingredient : produit.getListIngredients()) {
				int idIngredient = daoIngredient.insert(ingredient);
				PreparedStatement insertJointure = connection.prepareStatement(
						"INSERT INTO `jointure_prod_ingredient`(`id_produit`, `id_Ingredient`) VALUES (?,?)");
				insertJointure.setInt(1, idProduit);
				insertJointure.setInt(2, idIngredient);
				insertJointure.execute();

			}

			JDBCdaoAllergene daoAllergene = new JDBCdaoAllergene(connection);
			for (Allergene allergene : produit.getListAllergenes()) {
				int idAllergene = daoAllergene.insert(allergene);
				PreparedStatement insertJointure = connection.prepareStatement(
						"INSERT INTO `jointure_prod_allergene`(`id_produit`, `id_Allergene`) VALUES (?,?)");
				insertJointure.setInt(1, idProduit);
				insertJointure.setInt(2, idAllergene);
				insertJointure.execute();
			}

			JDBCdaoAdditif daoAdditif = new JDBCdaoAdditif(connection);
			for (Additif add : produit.getListAdditifs()) {
				int idAdditif = daoAdditif.insert(add);
				PreparedStatement insertJointure = connection.prepareStatement(
						"INSERT INTO `jointure_prod_additif`(`id_produit`, `id_Additif`) VALUES (?,?)");
				insertJointure.setInt(1, idProduit);
				insertJointure.setInt(2, idAdditif);
				insertJointure.execute();
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			// ConnectionBDD.closeConnection(connection, LOGGER);
		}
	}

	/*
	 * public Produit selectProduit(String nomProduit) {
	 * 
	 * Produit selectedProduit = null;
	 * 
	 * Connection connection = null; try { connection =
	 * ConnectionBDD.getConnection(); ConnectionBDD.testConnection(connection,
	 * LOGGER);
	 * 
	 * PreparedStatement insertProduit = connection
	 * .prepareStatement("SELECT * FROM `Produit` WHERE nom_Produit= ?");
	 * insertProduit.setString(1, nomProduit); ResultSet result =
	 * insertProduit.executeQuery(); if (result.next()) { String BDDnomProduit =
	 * result.getString(2); String BDDgradeNutriProduit = result.getString(3);
	 * 
	 * JDBCdaoCategorie daoCat = new JDBCdaoCategorie(); Categorie
	 * BDDCategorieProduit = daoCat.selectCategorie(result.getInt(4)); // Select la
	 * catégorie correspondant à l'id stockée dans la colone id_Cat
	 * 
	 * selectedProduit = new Produit(BDDnomProduit, BDDgradeNutriProduit,
	 * BDDCategorieProduit); }
	 * 
	 * } catch (SQLException e) { // transformer SQLException en ComptaException
	 * throw new
	 * TraitementFichierException("Erreur de communication avec la base de données",
	 * e); }
	 * 
	 * finally { ConnectionBDD.closeConnection(connection, LOGGER); } return
	 * selectedCat;
	 * 
	 * }
	 */

	/**
	 * Sert à obtenir l'ID de la catégorie en BDD
	 * 
	 * @param nomProduit
	 * @return Retourne l'id_Cat en BDD de la Produit dont le nom est égal au param
	 */
	public int getId_Produit(String nomProduit) {

		int idCat = 0;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertProduit = this.connection
					.prepareStatement("SELECT * FROM `Produit` WHERE nom_Produit= ?");
			insertProduit.setString(1, nomProduit);
			ResultSet result = insertProduit.executeQuery();
			if (result.next()) {
				idCat = result.getInt(1);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			//ConnectionBDD.closeConnection(connection, LOGGER);
		}

		return idCat;
	}

	public boolean produitDejaExistant(String nomProduit) {
		boolean bool = false;
		if (getId_Produit(nomProduit) != 0) {
			bool = true;
		}
		return bool;
	}

}
