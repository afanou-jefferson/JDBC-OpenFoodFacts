//package jdbc;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.logging.Logger;
//
//import datas.Entite;
//import datas.Entite;
//import exceptions.TraitementFichierException;
//import utils.ConnectionBDD;
//
//public class ControlerJDBCdao {
///*
//
//	private static final Logger LOGGER = Logger.getLogger(ControlerJDBCdao.class.getName());
//
//	
//	/**
//	 * Insert l'entite placée en paramètre si elle n'est pas déjà enregistrée
//	 * dans la BDD
//	 * 
//	 * @param Objet Entite
//	 * @return l'ID de la entite inserrée si effecutée correctement,sinon
//	 *         retourne un int >= 1 correspondant à l'id_Cat dans la BDD
//	 **/
//	public int insert(Entite entite) {
//		// TODO Auto-generated method stub
//		Connection connection = null;
//		int idEntite = 0;
//
//		try {
//			connection = ConnectionBDD.getConnection();
//			ConnectionBDD.testConnection(connection, LOGGER);
//
//			// Si la entite n'est pas déjà enregistrée en BDD, on l'insert
//			if (!EntiteDejaExistant(entite.getEntite())) {
//				PreparedStatement insertEntite = connection
//						.prepareStatement("INSERT INTO ? (?) VALUES (?)");
//				insertEntite.setString(1, entite.getClassEntite());
//				insertEntite.setString(2, entite.getEntite());
//				insertEntite.execute();
//			}
//
//			// on retourne l'ID de l entite existante ou que l'on vient de créer
//			idEntite = getId_Entite(entite.getEntite());
//
//		} catch (SQLException e) {
//			// transformer SQLException en ComptaException
//			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
//		}
//
//		finally {
//			ConnectionBDD.closeConnection(connection, LOGGER);
//		}
//		return idEntite;
//	}
//
//	/**
//	 * Extrait l'objet Entite ayant le nomEntite placée en param
//	 * 
//	 * @param nomEntite
//	 * @return Objet de type Entite
//	 */
//	public Entite selectEntite(String nomEntite) {
//
//		Entite selectedCat = null;
//
//		Connection connection = null;
//		try {
//			connection = ConnectionBDD.getConnection();
//			ConnectionBDD.testConnection(connection, LOGGER);
//
//			PreparedStatement insertEntite = connection
//					.prepareStatement("SELECT * FROM `entite` WHERE nom_entite= ?");
//			insertEntite.setString(1, nomEntite);
//			ResultSet result = insertEntite.executeQuery();
//			if (result.next()) {
//				String nomCat = result.getString(2);
//				selectedCat = new Entite(nomCat);
//			}
//
//		} catch (SQLException e) {
//			// transformer SQLException en ComptaException
//			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
//		}
//
//		finally {
//			ConnectionBDD.closeConnection(connection, LOGGER);
//		}
//		return selectedCat;
//
//	}
//
//	public Entite selectEntite(int idEntite) {
//		Entite selectedCat = null;
//
//		Connection connection = null;
//		try {
//			connection = ConnectionBDD.getConnection();
//			ConnectionBDD.testConnection(connection, LOGGER);
//
//			PreparedStatement insertEntite = connection
//					.prepareStatement("SELECT * FROM `entite` WHERE id_entite= ?");
//			insertEntite.setInt(1, idEntite);
//			ResultSet result = insertEntite.executeQuery();
//			if (result.next()) {
//				String nomCat = result.getString(2);
//				selectedCat = new Entite(nomCat);
//			}
//
//		} catch (SQLException e) {
//			// transformer SQLException en ComptaException
//			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
//		}
//
//		finally {
//			ConnectionBDD.closeConnection(connection, LOGGER);
//		}
//		return selectedCat;
//
//	}
//
//	/**
//	 * Sert à obtenir l'ID de la entite en BDD
//	 * 
//	 * @param nomEntite
//	 * @return Retourne l'id_Cat en BDD de la Entite dont le nom est égal au
//	 *         param
//	 */
//	public int getId_Entite(String nomEntite) {
//
//		int idCat = 0;
//
//		Connection connection = null;
//		try {
//			connection = ConnectionBDD.getConnection();
//			ConnectionBDD.testConnection(connection, LOGGER);
//
//			PreparedStatement insertEntite = connection.prepareStatement("SELECT * FROM `entite` WHERE nom_entite= ?");
//			insertEntite.setString(1, nomEntite);
//			ResultSet result = insertEntite.executeQuery();
//			if (result.next()) {
//				idCat = result.getInt(1);
//			}
//
//		} catch (SQLException e) {
//			// transformer SQLException en ComptaException
//			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
//		}
//
//		finally {
//			ConnectionBDD.closeConnection(connection, LOGGER);
//		}
//
//		return idCat;
//	}
//
//	public boolean EntiteDejaExistant(String nomCat) {
//		boolean bool = false;
//		if (getId_Entite(nomCat) != 0) {
//			bool = true;
//		}
//		return bool;
//	}
//} 