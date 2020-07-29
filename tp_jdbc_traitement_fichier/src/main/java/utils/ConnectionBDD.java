package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import exceptions.TraitementFichierException;

public class ConnectionBDD {

	public static Connection getConnection() {
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

	public static void testConnection(Connection connection, Logger LOGGER) {
		try {
			boolean valid = connection.isValid(500);
			if (valid) {
				LOGGER.log(Level.INFO, "La connection est ok");
			} else {
				LOGGER.log(Level.SEVERE, "Il y a une erreur de connection");
			}
		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur de communication avec la base de données", e);
		}
	}
	
	public static void closeConnection(Connection connection, Logger LOGGER) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.log(Level.INFO, "Requête effectuée, déconnexion de la BDD");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
