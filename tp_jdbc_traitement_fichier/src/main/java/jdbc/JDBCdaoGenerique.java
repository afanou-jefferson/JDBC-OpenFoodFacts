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

public class JDBCdaoGenerique {

	private static final Logger LOGGER = Logger.getLogger(JDBCdaoGenerique.class.getName());

	public Connection connection;
	public String nomTable;
	public int nbAttributs;
	public ArrayList<String> nomsAttributs;

	public JDBCdaoGenerique(Connection connection) {
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

		int idRow = 0;
		
		//System.out.println("Taille nb Attributs : " + model.getNbAttributsModel() );

		try {

			if (!rowDejaExistant(model.getValeurIdentifiant(), model)) {
				
				StringBuilder builtString = new StringBuilder();
				builtString.append("INSERT INTO ").append(model.getNomModel())
						.append(" " + attributsToFormatSQL(model) + " ").append(" VALUES ")
						.append(nbAttributsToFormatSQL(model));
				
				PreparedStatement insert = connection.prepareStatement(builtString.toString(),
						Statement.RETURN_GENERATED_KEYS);

				for (int i = 1; i <= model.getNbAttributsModel(); i++) {
					insert.setString(i, model.getValeurAttributsModel().get(i - 1));
					//System.out.println("Test controller.insert() : " + model.getValeurAttributsModel().get(i - 1) );
				}
				
				insert.execute();
				ResultSet result = insert.getGeneratedKeys();
				if (result.next()) {
					idRow = result.getInt(1);
				}
			} else {
				idRow = getIDFromNom(model.getNomModel(), model);
			}
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}

		return idRow;
	}

	public String selectRowLike(InfoProduit model) {
		String nomDansBDD = "NON ENREGISTRE";
		
		try {
				StringBuilder builtString = new StringBuilder();
				builtString.append("SELECT * FROM ").append(model.getNomModel()).append(" WHERE nom_")
						.append(model.getNomModel()).append(" = ?");
				PreparedStatement selectWhereString = connection.prepareStatement(builtString.toString());
				selectWhereString.setString(1, model.getValeurIdentifiant());
				ResultSet result = selectWhereString.executeQuery();
				if (result.next()) {
					nomDansBDD = result.getString(2);
			}
			
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}	
		return nomDansBDD;
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

	public String getNomFromID(int idACherche, InfoProduit model) {
		String nomRow = "ROW INNEXISTANT";

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ").append(model.getNomModel()).append(" WHERE id_").append(model.getNomModel())
					.append(" = ?");
			PreparedStatement insertRow = connection.prepareStatement(builtString.toString());
			insertRow.setInt(1, idACherche);
			ResultSet result = insertRow.executeQuery();
			if (result.next()) {
				nomRow = result.getString(2);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
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
			builtString.append("SELECT * FROM ").append(model.getNomModel()).append(" WHERE nom_")
					.append(model.getNomModel()).append(" = ?");
			PreparedStatement selectWhereString = connection.prepareStatement(builtString.toString());
			selectWhereString.setString(1, model.getValeurIdentifiant());
			ResultSet result = selectWhereString.executeQuery();
			
			//System.out.println( "Test controller.getIdFromNom : " + builtString.toString() + " var = " + model.getValeurIdentifiant() );
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
		if (getIDFromNom(stringAChercher, model) != 0) {
			bool = true;
		}
		return bool;
	}
}