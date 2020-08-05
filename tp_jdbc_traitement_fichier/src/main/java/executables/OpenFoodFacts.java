package executables;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import core.App;
import utils.Chrono;
import utils.ConnectionBDD;

public class OpenFoodFacts {
	
	private static final Logger LOGGER = Logger.getLogger(OpenFoodFacts.class.getName());

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

		Connection connectionDB = ConnectionBDD.getConnection();
		File fichier = new File(System.getProperty("user.dir") + "/src/main/resources/open-food-facts.csv");
		//System.out.println(fichier.canRead());

		Chrono chrono = new Chrono();
		chrono.start(); // démarrage du chrono
		System.out.println("Lancement Programme");
		
		App myApp = new App(fichier, connectionDB);

		chrono.stop(); // arrêt
		System.out.println("Temps pour opérations : " + chrono.getDureeTxt()); // affichage au format "1 h 26 min 32 s"
		

		System.out.println("Produits insérés = " + myApp.compteurInsertProduits);
		System.out.println("Catégories insérés = " + myApp.compteurInsertCategorie);
		System.out.println("Ingrédients insérés = " + myApp.compteurInsertIngredients);
		System.out.println("Allergenes insérés = " + myApp.compteurInsertAllergenes);
		System.out.println("Additifs insérés = " + myApp.compteurInsertAdditifs);
		

		try {
			connectionDB.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			ConnectionBDD.closeConnection(connectionDB, LOGGER);
		}
	}
}
