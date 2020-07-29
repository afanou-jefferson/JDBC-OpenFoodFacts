package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import datas.Marque;
import exceptions.TraitementFichierException;
import utils.ConnectionBDD;

public class JDBCdaoMarque implements IMarqueDao {

	public Connection connection;

	public JDBCdaoMarque(Connection connection) {
		this.connection = connection;
	}

	private static final Logger LOGGER = Logger.getLogger(JDBCdaoMarque.class.getName());

	/**
	 * Insert la ingrédient placée en paramètre si elle n'est pas déjà enregistrée
	 * dans la BDD
	 * 
	 * @param Objet Marque
	 * @return l'ID de la ingrédient inserrée si effecutée correctement,sinon
	 *         retourne un int >= 1 correspondant à l'id_Cat dans la BDD
	 **/
	public int insert(Marque marque) {
		// TODO Auto-generated method stub
		// Connection connection = null;
		int idCat = 0;

		try {
			// connection = ConnectionBDD.getConnection();
			// ConnectionBDD.testConnection(connection, LOGGER);

			// Si la ingrédient n'est pas déjà enregistrée en BDD, on l'insert
			if (!MarqueDejaExistante(marque.getNomMarque())) {
				PreparedStatement insertMarque = connection
						.prepareStatement("INSERT INTO `marque`(`nom_Marque`) VALUES (?)");
				insertMarque.setString(1, marque.getNomMarque());
				insertMarque.execute();
			}

			// on retourne l'ID de la marque existante ou que l'on vient de crée
			idCat = getId_Marque(marque.getNomMarque());

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			// ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return idCat;
	}

	/**
	 * Extrait l'objet Marque ayant le nomMarque placée en param
	 * 
	 * @param nomMarque
	 * @return Objet de type Marque
	 */
	public Marque selectMarque(String nomMarque) {

		Marque selectedCat = null;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertMarque = connection.prepareStatement("SELECT * FROM `marque` WHERE nom_marque= ?");
			insertMarque.setString(1, nomMarque);
			ResultSet result = insertMarque.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Marque(nomCat);
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

	public Marque selectMarque(int idMarque) {
		Marque selectedCat = null;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertMarque = connection.prepareStatement("SELECT * FROM `marque` WHERE id_marque= ?");
			insertMarque.setInt(1, idMarque);
			ResultSet result = insertMarque.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Marque(nomCat);
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
	 * Sert à obtenir l'ID de la ingrédient en BDD
	 * 
	 * @param nomMarque
	 * @return Retourne l'id_Cat en BDD de la Marque dont le nom est égal au param
	 */
	public int getId_Marque(String nomMarque) {

		int idCat = 0;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertMarque = connection.prepareStatement("SELECT * FROM `marque` WHERE nom_marque= ?");
			insertMarque.setString(1, nomMarque);
			ResultSet result = insertMarque.executeQuery();
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

	public boolean MarqueDejaExistante(String nomCat) {
		boolean bool = false;
		if (getId_Marque(nomCat) != 0) {
			bool = true;
		}
		return bool;
	}
}
