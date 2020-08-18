package application;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import datas.Datas;
import jdbc.JDBCdaoProduit;
import utils.Chrono;
import utils.ConnectionBDD;


public class ApplicationOpenFoodFacts {
	
	private static final Logger LOGGER = Logger.getLogger(ApplicationOpenFoodFacts.class.getName());

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

		Connection connectionDB = ConnectionBDD.getConnection();
		File fichier = new File(System.getProperty("user.dir") + "/src/main/resources/open-food-facts.csv");
		//System.out.println(fichier.canRead());

		Chrono chrono = new Chrono();
		chrono.start(); // démarrage du chrono
		System.out.println("Lancement Programme");

		JDBCdaoProduit daoProduit = new JDBCdaoProduit(connectionDB);
		Datas myDB = new Datas(fichier, connectionDB);

		chrono.stop(); // arrêt
		System.out.println("Temps pour opérations : " + chrono.getDureeTxt()); // affichage au format "1 h 26 min 32 s"
		

		System.out.println("Produits insérés = " + myDB.compteurProduits);
		System.out.println("Catégories insérés = " + myDB.compteurCategorie);
		System.out.println("Ingrédients insérés = " + myDB.compteurIngredients);
		System.out.println("Allergenes insérés = " + myDB.compteurAllergenes);
		System.out.println("Additifs insérés = " + myDB.compteurAdditifs);
		

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
