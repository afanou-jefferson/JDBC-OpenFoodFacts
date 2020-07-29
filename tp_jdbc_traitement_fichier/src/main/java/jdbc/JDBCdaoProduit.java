package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import datas.Produit;
import exceptions.TraitementFichierException;
import utils.ConnectionBDD;

public class JDBCdaoProduit implements IProduitDao {

	private static final Logger LOGGER = Logger.getLogger(JDBCdaoProduit.class.getName());

	@Override
	public void insert(Produit produit) {
		// TODO Auto-generated method stub
		Connection connection = null;

		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			//On check déjà que la catégorie est présente pour respecter la contrainte de clé étrangère
			JDBCdaoCategorie daoCat = new JDBCdaoCategorie();
			int idCat = daoCat.insert(produit.getCategorie());

			PreparedStatement insertProduit = connection.prepareStatement(
					"INSERT INTO `produit`(`nom_Produit`, `grade_Nutri_Produit`, `id_Categorie`) VALUES (?,?,?)");
			insertProduit.setString(1, produit.getNomProduit());
			insertProduit.setString(2, produit.getGradeNutri());
			insertProduit.setInt(3, idCat);

			insertProduit.execute();
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			ConnectionBDD.closeConnection(connection, LOGGER);
		}
	}

}
