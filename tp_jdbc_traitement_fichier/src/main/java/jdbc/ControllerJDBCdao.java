package jdbc;

import java.lang.ProcessHandle.Info;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import datas.InfoProduit;
import exceptions.TraitementFichierException;
import utils.ConnectionBDD;

public class ControllerJDBCdao {

	private static final Logger LOGGER = Logger.getLogger(ControllerJDBCdao.class.getName());

	public Connection connection;
	public String nomTable;
	public int nbAttributs;
	public ArrayList<String> nomsAttributs;

	public ControllerJDBCdao(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Insert l'InfoProduit placée en paramètre si elle n'est pas déjà enregistrée
	 * dans la BDD
	 * 
	 * @param InfoProduit
	 * @return l'ID de la row inserrée si effecutée ou bien l'id de la row existante
	 *         si doublon
	 **/
	public int insert(InfoProduit model) {
		// TODO Auto-generated method stub

		int idNewRow = 0;

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("INSERT INTO ").append(model.getNomModel())
					.append(" " + attributsToFormatSQL(model) + " ").append(" VALUES ")
					.append(nbAttributsToFormatSQL(model));
			// Si l'objet n'est pas déjà enregistrée en BDD, on l'insert
			if (!rowDejaExistant(model.getValeurIdentifiant(),model)) {
				PreparedStatement insert = connection.prepareStatement(builtString.toString(), Statement.RETURN_GENERATED_KEYS);

				for (int i = 1; i <= model.getNbAttributsModel(); i++) {
					insert.setString(i, model.getValeurAttributsModel().get(i-1));
				}
				insert.execute();
				ResultSet result = insert.getGeneratedKeys();
				if (result.next()) {
					idNewRow = result.getInt(1);
				}
			}
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		return idNewRow;
	}

	public String nbAttributsToFormatSQL(InfoProduit model) {
		String nbAttributsToFormatSQL = "(";
		for (int i = 1; i <= model.getNbAttributsModel(); i++) {
			nbAttributsToFormatSQL += "?,";
		}
		nbAttributsToFormatSQL = nbAttributsToFormatSQL.substring(0, nbAttributsToFormatSQL.length() - 1); // Enlever la
																											// dernière
																											// virgule
		nbAttributsToFormatSQL += ")";
		return nbAttributsToFormatSQL;
	}

	public String attributsToFormatSQL(InfoProduit model) {
		String stringAttributs = "(";

		for (String nomAttribut : model.getNomAttributsModel()) {
			stringAttributs += "`" + nomAttribut + "`,";
		}
		stringAttributs = stringAttributs.substring(0, stringAttributs.length() - 1); // Enlever la dernière virgule
		stringAttributs += ")";
		return stringAttributs;
	}

	public String getNomFromID(int idACherche) {
		String nomRow = null;

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ").append(this.nomTable).append(" WHERE id_").append(this.nomTable)
					.append(" = ?");
			PreparedStatement insertEntite = connection.prepareStatement("SELECT * FROM `entite` WHERE id_entite= ?");
			insertEntite.setInt(1, idACherche);
			ResultSet result = insertEntite.executeQuery();
			if (result.next()) {
				nomRow = result.getString(2);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		finally {
			ConnectionBDD.closeConnection(connection, LOGGER);
		}
		return nomRow;

	}

	/**
	 * Sert à obtenir l'ID de la entite en BDD
	 * 
	 * @param nomEntite
	 * @return Retourne l'id_Cat en BDD de la Entite dont le nom est égal au param
	 */
	public int getIDFromNom(String nomAChercher, InfoProduit model) {

		int idRow = 0;

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ").append(model.getNomModel()).append(" WHERE nom_").append(model.getNomModel())
					.append(" = ?");
			PreparedStatement selectWhereString = connection.prepareStatement(builtString.toString());
			selectWhereString.setString(1, nomAChercher);
			ResultSet result = selectWhereString.executeQuery();
			if (result.next()) {
				idRow = result.getInt(1);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
		return idRow;
	}

	public boolean rowDejaExistant(String stringAChercher, InfoProduit model) {
		boolean bool = false;
		if (getIDFromNom(stringAChercher, model ) != 0) {
			bool = true;
		}
		return bool;
	}
}