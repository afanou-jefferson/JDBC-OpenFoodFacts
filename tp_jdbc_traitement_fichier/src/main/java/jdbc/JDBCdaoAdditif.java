package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import datas.Additif;
import exceptions.TraitementFichierException;
import utils.ConnectionBDD;

public class JDBCdaoAdditif implements IAdditifDao {
	
	public Connection connection;
	
	public JDBCdaoAdditif(Connection connection) {
		this.connection = connection;
	}

	private static final Logger LOGGER = Logger.getLogger(JDBCdaoProduit.class.getName());

	/**
	 * Insert l'additif placée en paramètre si elle n'est pas déjà enregistrée
	 * dans la BDD
	 * 
	 * @param Objet Additif
	 * @return l'ID de la additif inserrée si effecutée correctement,sinon
	 *         retourne un int >= 1 correspondant à l'id_Cat dans la BDD
	 **/
	public int insert(Additif additif) {
		// TODO Auto-generated method stub
		//Connection connection = null;
		int idAdditif = 0;

		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			// Si la additif n'est pas déjà enregistrée en BDD, on l'insert
			if (!AdditifDejaExistant(additif.getLibelleAdditif())) {
				PreparedStatement insertProduit = this.connection
						.prepareStatement("INSERT INTO `additif`(`nom_Additif`) VALUES (?)");
				insertProduit.setString(1, additif.getLibelleAdditif());
				insertProduit.execute();
			}

			// on retourne l'ID de l additif existante ou que l'on vient de créer
			idAdditif = getId_Additif(additif.getLibelleAdditif());

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			//ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return idAdditif;
	}

	/**
	 * Extrait l'objet Additif ayant le nomAdditif placée en param
	 * 
	 * @param nomAdditif
	 * @return Objet de type Additif
	 */
	public Additif selectAdditif(String nomAdditif) {

		Additif selectedCat = null;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertProduit = this.connection
					.prepareStatement("SELECT * FROM `additif` WHERE nom_additif= ?");
			insertProduit.setString(1, nomAdditif);
			ResultSet result = insertProduit.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Additif(nomCat);
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

	public Additif selectAdditif(int idAdditif) {
		Additif selectedCat = null;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertProduit = this.connection
					.prepareStatement("SELECT * FROM `additif` WHERE id_additif= ?");
			insertProduit.setInt(1, idAdditif);
			ResultSet result = insertProduit.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Additif(nomCat);
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
	 * Sert à obtenir l'ID de la additif en BDD
	 * 
	 * @param nomAdditif
	 * @return Retourne l'id_Cat en BDD de la Additif dont le nom est égal au
	 *         param
	 */
	public int getId_Additif(String nomAdditif) {

		int idCat = 0;

		//Connection connection = null;
		try {
			//connection = ConnectionBDD.getConnection();
			//ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertProduit = this.connection
					.prepareStatement("SELECT * FROM `additif` WHERE nom_additif= ?");
			insertProduit.setString(1, nomAdditif);
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

	public boolean AdditifDejaExistant(String nomCat) {
		boolean bool = false;
		if (getId_Additif(nomCat) != 0) {
			bool = true;
		}
		return bool;
	}
}
