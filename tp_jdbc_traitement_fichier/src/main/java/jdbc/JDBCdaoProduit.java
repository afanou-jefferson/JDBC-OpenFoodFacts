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

public class JDBCdaoProduit implements IProduitDao {

	private static final Logger LOGGER = Logger.getLogger(ProduitDaoJdbc.class.getName());

	public Connection getConnection() {
		// recupere le fichier properties
		ResourceBundle db = ResourceBundle.getBundle("databaseTraitement_Fichier");

		try {
			// enregistre le pilote
			Class.forName(db.getString("db.driver"));

			return DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
					db.getString("db.pass"));

		} catch (ClassNotFoundException | SQLException e) {
			throw new TraitementFichierException(e);
		}
	}

	@Override
	public void insert(Produit produit) {
		// TODO Auto-generated method stub
		Connection connection = null;

		try {
			connection = getConnection();
			
			// affiche la connexion
			boolean valid = connection.isValid(500);
			if (valid) {
				LOGGER.log(Level.INFO, "La connection est ok");
			} else {
				LOGGER.log(Level.SEVERE, "Il y a une erreur de connection");
			}
			
			//reparedStatement insertCategorie 
			
			PreparedStatement insertProduit = connection.prepareStatement(
					"INSERT INTO `produit`(`nom_Produit`, `grade_Nutri_Produit`, `nom_Categorie`) VALUES (?,?,?)");
			insertProduit.setString(1, produit.getNomProduit());
			insertProduit.setString(2, produit.getGradeNutri());
			insertProduit.setString(3, produit.getCategorie().getEntite());
		
			
			insertProduit.execute();
			

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Déconnexion BDD");
			}
		}

	}

}
