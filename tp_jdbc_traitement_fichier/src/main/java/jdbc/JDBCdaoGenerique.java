package jdbc;

import java.awt.List;
import java.lang.ProcessHandle.Info;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import datas.Additif;
import datas.Allergene;
import datas.Categorie;
import datas.CustomRow;
import datas.Datas;
import datas.Entite;
import datas.Ingredient;
import datas.Marque;
import exceptions.TraitementFichierException;
import utils.ConnectionBDD;
import utils.NettoyageString;

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
	 * Insert l'Objet héritant d'InfoProduit placée en paramètre si elle n'est pas déjà enregistrée
	 * dans la BDD
	 * 
	 * @param Entite
	 * @return l'ID de la row inserrée si effecutée ou bien l'id de la row existante
	 *         si doublon
	 **/
	public int insert(Entite model) {

		int idRow = 0;
		try {
				int alikeID = selectIDRowLike(model);
				if (alikeID != -1) { // Si différent de -1 alors on a un enregistrement similaire donc on passe et on renvoit cet ID
					idRow = alikeID;
				} else { // Sinon insert classique
					StringBuilder builtString = new StringBuilder();
					builtString.append("INSERT INTO ")
							.append(model.getNomModel())
							.append(" " + attributsToFormatSQL(model) + " ")
							.append(" VALUES ")
							.append(nbAttributsToFormatSQL(model));

					PreparedStatement insert = connection.prepareStatement(builtString.toString(),
							Statement.RETURN_GENERATED_KEYS);

					for (int i = 1; i <= model.getNbAttributsModel(); i++) {
						insert.setString(i, model.getValeurAttributsModel().get(i - 1));
					}
				
					//System.out.println(insert.toString());
					insert.execute();
					
					ResultSet result = insert.getGeneratedKeys();
					if (result.next()) {
						idRow = result.getInt(1);
					}
				}
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
		return idRow;
	}

	public CustomRow selectRowLike(Entite model) {

		CustomRow infosRow = new CustomRow();

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ").append(model.getNomModel()).append(" WHERE nom_")
					.append(model.getNomModel()).append(" = ?");
			PreparedStatement selectWhereString = connection.prepareStatement(builtString.toString());
			selectWhereString.setString(1, model.getNomUnique());
			ResultSet result = selectWhereString.executeQuery();
			if (result.next()) {
				infosRow.setId_Row(result.getInt(1));
				infosRow.setNom_Row(result.getString(2));
			}

		} catch (SQLException e) {

			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
		return infosRow;
	}

	public int selectIDRowLike(Entite model) {

		int idRowAlike = -1;

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ")
						.append(model.getNomModel())
						.append(" WHERE nom_")
						.append(model.getNomModel())
						.append(" = ?");
			PreparedStatement selectWhereString = connection.prepareStatement(builtString.toString());
			selectWhereString.setString(1, model.getNomUnique());
			ResultSet result = selectWhereString.executeQuery();
			if (result.next()) {
				idRowAlike = result.getInt(1);
			}
		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
		return idRowAlike;
	}

	public String nbAttributsToFormatSQL(Entite model) {
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

	public String attributsToFormatSQL(Entite model) {

		String stringAttributs = "(";

		for (String nomAttribut : model.getNomAttributsModel()) {
			stringAttributs += "`" + nomAttribut + "`,";
		}
		stringAttributs = stringAttributs.substring(0, stringAttributs.length() - 1); // Enlever la dernière virgule
		stringAttributs += ")";
		return stringAttributs;
	}

	public String getNomFromID(int idACherche, Entite model) {
		String nomRow = "ROW INNEXISTANT";

		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ")
						.append(model.getNomModel())
						.append(" WHERE id_")
						.append(model.getNomModel())
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
	public int getIDFromNom(String nomAChercher, Entite model) {

		int idRow = -1;
		try {
			StringBuilder builtString = new StringBuilder();
			builtString.append("SELECT * FROM ")
						.append(model.getNomModel())
						.append(" WHERE nom_")
						.append(model.getNomModel())
						.append(" = ?");
			PreparedStatement selectWhereString = connection.prepareStatement(builtString.toString());
			selectWhereString.setString(1, model.getNomUnique());
			ResultSet result = selectWhereString.executeQuery();

			// System.out.println( "Test controller.getIdFromNom : " +
			// builtString.toString() + " var = " + model.getValeurIdentifiant() );
			if (result.next()) {
				idRow = result.getInt(1);
			}

		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
		return idRow;
	}

	public boolean rowDejaExistant(String stringAChercher, Entite model) {
		boolean bool = false;
		if (getIDFromNom(stringAChercher, model) != 0) {
			bool = true;
		}
		return bool;
	}

	public HashMap<String,Entite> selectAllFromTable(String nomTable) {
		
		HashMap<String,Entite> tableExistanteEnBDD = new HashMap<String,Entite>();
		
		String query = "SELECT * FROM "+ nomTable;
		
		try {
			PreparedStatement selectTable = connection.prepareStatement(query);
			//System.out.println(selectTable.toString());
			ResultSet result =  selectTable.executeQuery();
			
			while (result.next()) {
				
				switch (nomTable) {
				case "Additif" :
					tableExistanteEnBDD.put( NettoyageString.nettoyerString(result.getString(2)), new Additif(result.getString(2)));
					break;
				case "Allergene" :
					tableExistanteEnBDD.put( NettoyageString.nettoyerString(result.getString(2)), new Allergene(result.getString(2)));
					break;
				case "Categorie" : 
					tableExistanteEnBDD.put( NettoyageString.nettoyerString(result.getString(2)), new Categorie(result.getString(2)));
					break;
				case "Ingredient" :
					tableExistanteEnBDD.put( NettoyageString.nettoyerString(result.getString(2)), new Ingredient(result.getString(2)));
					break;
				case "Marque" : 
					tableExistanteEnBDD.put( NettoyageString.nettoyerString(result.getString(2)), new Marque(result.getString(2)));
					break;
				}
			}
			
		return tableExistanteEnBDD;	
			
		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
	}
}