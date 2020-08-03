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

public class JDBCdaoProduit {

	public Connection connection;

	public JDBCdaoProduit(Connection connection) {
		this.connection = connection;
	}

	public int insert(Produit produit) {
		// TODO Auto-generated method stub
		// Connection connection = null;

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(connection);
		JDBCdaoProduit daoProduit = new JDBCdaoProduit(connection);
		int idProduit = -1;

		try {

			// On check déjà que la catégorie est présente pour respecter la contrainte de
			// clé étrangère

			//int idCategorie = daoGenerique.getIDFromNom(produit.getCategorie().getNomCategorie(),produit.getCategorie());

			//if (!produitDejaExistant(produit.getNomProduit())) {
				PreparedStatement insertProduit = connection.prepareStatement(
						"INSERT INTO `produit`(`nom_Produit`, `grade_Nutri_Produit`, `id_Categorie`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
				insertProduit.setString(1, produit.getNomProduit());
				insertProduit.setString(2, produit.getGradeNutri());
				insertProduit.setInt(3, produit.getIdCategorie());
				insertProduit.execute();
				
				ResultSet result = insertProduit.getGeneratedKeys();
				if (result.next()) {
					idProduit = result.getInt(1);
				}
			//}

			// Si l'insert renvoit -1, cad pas d'insert effectif
			if ( idProduit ==-1 ) {
				idProduit = getId_Produit(produit.getNomProduit());
			}

			for (Integer idMarque : produit.getListIDsMarques()) {
				PreparedStatement insertJointure = connection
						.prepareStatement("INSERT INTO `jointure_prod_marque`(`id_produit`, `id_Marque`) VALUES (?,?)");
				insertJointure.setInt(1, idProduit);
				insertJointure.setInt(2, idMarque);
				insertJointure.execute();
			}

			for (Integer idIngredient : produit.getListIDsIngredients()) {
				PreparedStatement insertJointure = connection.prepareStatement(
						"INSERT INTO `jointure_prod_ingredient`(`id_produit`, `id_Ingredient`) VALUES (?,?)");
				insertJointure.setInt(1, idProduit);
				insertJointure.setInt(2, idIngredient);
				insertJointure.execute();

			}

			for (Integer idAllergene : produit.getListIDsAllergenes()) {
				PreparedStatement insertJointure = connection.prepareStatement(
						"INSERT INTO `jointure_prod_allergene`(`id_produit`, `id_Allergene`) VALUES (?,?)");
				insertJointure.setInt(1, idProduit);
				insertJointure.setInt(2, idAllergene);
				insertJointure.execute();
			}

			for (Integer idAdditif : produit.getListIDsAdditifs()) {
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
		
		return idProduit;
	}

	/**
	 * Sert à obtenir l'ID de la catégorie en BDD
	 * 
	 * @param nomProduit
	 * @return Retourne l'id_Cat en BDD de la Produit dont le nom est égal au param
	 */
	public int getId_Produit(String nomProduit) {

		int idCat = 0;

		// Connection connection = null;
		try {
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
