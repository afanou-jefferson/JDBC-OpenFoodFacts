package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import datas.Ingredient;
import exceptions.TraitementFichierException;
import utils.ConnectionBDD;

public class JDBCdaoIngredient implements IIngredientDao {
	
	private static final Logger LOGGER = Logger.getLogger(JDBCdaoIngredient.class.getName());

	/**
	 * Insert la ingredient placée en paramètre si elle n'est pas déjà enregistrée dans la BDD
	 *@param Objet Ingredient
	 *@return l'ID de la ingredient inserrée si effecutée correctement,sinon retourne un int >= 1 correspondant à l'id_Cat dans la BDD 
	 **/
	public int insert(Ingredient ingredient) {
		// TODO Auto-generated method stub
		Connection connection = null;
		int idCat = 0;

		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			// Si la ingredient n'est pas déjà enregistrée en BDD, on l'insert
			if (!IngredientDejaExistante(ingredient.getLibelleIngredient())) {
				PreparedStatement insertIngredient = connection
						.prepareStatement("INSERT INTO `ingredient`(`nom_Ingredient`) VALUES (?)");
				insertIngredient.setString(1, ingredient.getLibelleIngredient());
				insertIngredient.execute();
			} 
			
			// on retourne l'ID de la ingredient existante ou que l'on vient de crée
			idCat = getId_Ingredient(ingredient.getLibelleIngredient());
			
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return idCat;
	}

	/**
	 * Extrait l'objet Ingredient ayant le nomIngredient placée en param
	 * 
	 * @param nomIngredient
	 * @return Objet de type Ingredient
	 */
	public Ingredient selectIngredient(String nomIngredient) {

		Ingredient selectedCat = null;

		Connection connection = null;
		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertIngredient = connection
					.prepareStatement("SELECT * FROM `ingredient` WHERE nom_ingredient= ?");
			insertIngredient.setString(1, nomIngredient);
			ResultSet result = insertIngredient.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Ingredient(nomCat);
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
	
	public Ingredient selectIngredient(int idIngredient) {
		Ingredient selectedCat = null;

		Connection connection = null;
		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertIngredient = connection
					.prepareStatement("SELECT * FROM `ingredient` WHERE id_ingredient= ?");
			insertIngredient.setInt(1, idIngredient);
			ResultSet result = insertIngredient.executeQuery();
			if (result.next()) {
				String nomCat = result.getString(2);
				selectedCat = new Ingredient(nomCat);
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
	 * Sert à obtenir l'ID de la ingredient en BDD
	 * 
	 * @param nomIngredient
	 * @return Retourne l'id_Cat en BDD de la Ingredient dont le nom est égal au
	 *         param
	 */
	public int getId_Ingredient(String nomIngredient) {

		int idCat = 0;

		Connection connection = null;
		try {
			connection = ConnectionBDD.getConnection();
			ConnectionBDD.testConnection(connection, LOGGER);

			PreparedStatement insertIngredient = connection
					.prepareStatement("SELECT * FROM `ingredient` WHERE nom_ingredient= ?");
			insertIngredient.setString(1, nomIngredient);
			ResultSet result = insertIngredient.executeQuery();
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
	
	public boolean IngredientDejaExistante (String nomCat) {
		boolean bool = false;
		if ( getId_Ingredient(nomCat)!= 0) {
			bool = true;
		}
		return bool;
	}
}


