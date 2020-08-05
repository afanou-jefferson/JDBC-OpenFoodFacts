
package executables;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import core.App;

import utils.Chrono;
import utils.ConnectionBDD;

public class TestJeuReduit {
	
	private static final Logger LOGGER = Logger.getLogger(TestJeuReduit.class.getName());

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SQLException {

		Connection connectionDB = ConnectionBDD.getConnection();
		ConnectionBDD.testConnection(connectionDB, LOGGER);
	
		File fichier = new File(System.getProperty("user.dir") + "/src/main/resources/jeuTest.csv");
		//System.out.println("Fichier lisible =" + fichier.canRead());
	
		Chrono chrono = new Chrono();
		chrono.start(); // démarrage du chrono
		System.out.println("Lancement Programme");
		
		App myApp = new App(fichier, connectionDB);
		
		 
		chrono.stop(); // arrêt
		System.out.println("Temps d'execution: " + chrono.getDureeTxt()); // affichage au format "1 h 26 min 32 s"
		
		System.out.println("Produits insérés = " + myApp.compteurInsertProduits);
		System.out.println("Catégories insérés = " + myApp.compteurInsertCategorie);
		System.out.println("Ingrédients insérés = " + myApp.compteurInsertIngredients);
		System.out.println("Allergenes insérés = " + myApp.compteurInsertAllergenes);
		System.out.println("Additifs insérés = " + myApp.compteurInsertAdditifs);
		
		
		try {
			connectionDB.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			connectionDB.close();
		}
	}
}
