package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import datas.Categorie;

import exceptions.TraitementFichierException;
import utils.ConnectionBDD;

public class JDBCdaoCategorie implements ICategorieDao {

	public Connection connection;

	public JDBCdaoCategorie(Connection connection) {
		this.connection = connection;
	}


	private static final Logger LOGGER = Logger.getLogger(JDBCdaoCategorie.class.getName());

	/**
	 * Insert la catégorie placée en paramètre si elle n'est pas déjà enregistrée
	 * dans la BDD
	 * 
	 * @param Objet Categorie
	 * @return l'ID de la catégorie inserrée si effecutée correctement,sinon
	 *         retourne un int >= 1 correspondant à l'id_Cat dans la BDD
	 **/
	public int insert(Categorie categorie) {
		// TODO Auto-generated method stub
		// Connection connection = null;
		int idCategorie = 0;

		try {
			// connection = ConnectionBDD.getConnection();
			// ConnectionBDD.testConnection(connection, LOGGER);

			// Si la catégorie n'est pas déjà enregistrée en BDD, on l'insert
			if (!CategorieDejaExistante(categorie.getLibelleCategorie())) {
				PreparedStatement insertCategorie = connection
						.prepareStatement("INSERT INTO `categorie`(`nom_Categorie`) VALUES (?)");
				insertCategorie.setString(1, categorie.getLibelleCategorie());
				insertCategorie.execute();
			}

			// on retourne l'ID de la categorie existante ou que l'on vient de crée
			idCategorie = getId_Categorie(categorie.getLibelleCategorie());

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			// ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return idCategorie;
	}

	/**
	 * Extrait l'objet Categorie ayant le nomCategorie placée en param
	 * 
	 * @param nomCategorie
	 * @return Objet de type Categorie
	 */
	public Categorie selectCategorie(String nomCategorie) {

		Categorie selectedCat = null;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertCategorie = connection
					.prepareStatement("SELECT * FROM `categorie` WHERE nom_categorie= ?");
			insertCategorie.setString(1, nomCategorie);
			ResultSet result = insertCategorie.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Categorie(nomCat);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			//ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return selectedCat;

	}

	public Categorie selectCategorie(int idCategorie) {
		Categorie selectedCat = null;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertCategorie = connection
					.prepareStatement("SELECT * FROM `categorie` WHERE id_categorie= ?");
			insertCategorie.setInt(1, idCategorie);
			ResultSet result = insertCategorie.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Categorie(nomCat);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			//ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return selectedCat;

	}

	/**
	 * Sert à obtenir l'ID de la catégorie en BDD
	 * 
	 * @param nomCategorie
	 * @return Retourne l'id_Cat en BDD de la Categorie dont le nom est égal au
	 *         param
	 */
	public int getId_Categorie(String nomCategorie) {

		int idCat = 0;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertCategorie = connection
					.prepareStatement("SELECT * FROM `categorie` WHERE nom_categorie= ?");
			insertCategorie.setString(1, nomCategorie);
			ResultSet result = insertCategorie.executeQuery();
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

	public boolean CategorieDejaExistante(String nomCat) {
		boolean bool = false;
		if (getId_Categorie(nomCat) != 0) {
			bool = true;
		}
		return bool;
	}
}
