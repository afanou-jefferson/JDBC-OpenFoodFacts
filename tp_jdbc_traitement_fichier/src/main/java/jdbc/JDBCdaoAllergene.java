package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import datas.Allergene;

import exceptions.TraitementFichierException;
import utils.ConnectionBDD;

public class JDBCdaoAllergene implements IAllergeneDao {

	private static final Logger LOGGER = Logger.getLogger(JDBCdaoAllergene.class.getName());

	/**
	 * Insert la catégorie placée en paramètre si elle n'est pas déjà enregistrée dans la BDD
	 *@param Objet Allergene
	 *@return l'ID de la catégorie inserrée si effecutée correctement ou xistante,sinon retourne un int >= 1 correspondant à l'id_Cat dans la BDD 
	 **/
	public int insert(Allergene allergene) {
		// TODO Auto-generated method stub
		Connection connection = null;
		int idAllergene = 0;

		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			// Si la catégorie n'est pas déjà enregistrée en BDD, on l'insert
			if (!AllergeneDejaExistante(allergene.getLibelleAllergene())) {
				PreparedStatement insertAllergene = connection
						.prepareStatement("INSERT INTO `allergene`(`nom_Allergene`) VALUES (?)");
				insertAllergene.setString(1, allergene.getLibelleAllergene());
				insertAllergene.execute();
			} 
			
			// on retourne l'ID de la allergene existante ou que l'on vient de crée
			idAllergene = getId_Allergene(allergene.getLibelleAllergene());
			
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return idAllergene;
	}

	/**
	 * Extrait l'objet Allergene ayant le nomAllergene placée en param
	 * 
	 * @param nomAllergene
	 * @return Objet de type Allergene
	 */
	public Allergene selectAllergene(String nomAllergene) {

		Allergene selectedCat = null;

		Connection connection = null;
		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertAllergene = connection
					.prepareStatement("SELECT * FROM `allergene` WHERE nom_allergene= ?");
			insertAllergene.setString(1, nomAllergene);
			ResultSet result = insertAllergene.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Allergene(nomCat);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return selectedCat;

	}
	
	public Allergene selectAllergene(int idAllergene) {
		Allergene selectedCat = null;

		Connection connection = null;
		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertAllergene = connection
					.prepareStatement("SELECT * FROM `allergene` WHERE id_allergene= ?");
			insertAllergene.setInt(1, idAllergene);
			ResultSet result = insertAllergene.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Allergene(nomCat);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return selectedCat;

	}

	/**
	 * Sert à obtenir l'ID de la catégorie en BDD
	 * 
	 * @param nomAllergene
	 * @return Retourne l'id_Cat en BDD de la Allergene dont le nom est égal au
	 *         param
	 */
	public int getId_Allergene(String nomAllergene) {

		int idCat = 0;

		Connection connection = null;
		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertAllergene = connection
					.prepareStatement("SELECT * FROM `allergene` WHERE nom_allergene= ?");
			insertAllergene.setString(1, nomAllergene);
			ResultSet result = insertAllergene.executeQuery();
			if (result.next()) {
				idCat = result.getInt(1);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			ConnectionBDD.closeConnection(connection, LOGGER);
		}

		return idCat;
	}
	
	public boolean AllergeneDejaExistante (String nomCat) {
		boolean bool = false;
		if ( getId_Allergene(nomCat)!= 0) {
			bool = true;
		}
		return bool;
	}
}