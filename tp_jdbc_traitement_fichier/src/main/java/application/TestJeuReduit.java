
package application;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import datas.Additif;
import datas.Allergene;
import datas.Categorie;
import datas.Datas;
import datas.DonneesNutritionnelles;
import datas.Ingredient;
import datas.Marque;
import datas.Produit;
//import jdbc.JDBCdaoAdditif;
//import jdbc.JDBCdaoCategorie;
//import jdbc.JDBCdaoIngredient;
//import jdbc.JDBCdaoMarque;
import jdbc.JDBCdaoProduit;
import utils.Chrono;
import utils.ConnectionBDD;

public class TestJeuReduit {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SQLException {

		
		Connection connectionDB = ConnectionBDD.getConnection();
		
		File fichier = new File(System.getProperty("user.dir") + "/src/main/resources/jeuTest.csv");
		//System.out.println("Fichier lisible =" + fichier.canRead());
	
		Chrono chrono = new Chrono();
		chrono.start(); // démarrage du chrono
		System.out.println("Lancement Programme");
		
		JDBCdaoProduit daoProduit = new JDBCdaoProduit(connectionDB);
		Datas myDB = new Datas(fichier, connectionDB);
		
		 
		chrono.stop(); // arrêt
		System.out.println("Temps d'execution: " + chrono.getDureeTxt()); // affichage au format "1 h 26 min 32 s"
		
		//System.out.println("Test out " + myDB.memoireLocaleIngredientsBDD.toString());
		
		System.out.println("Produits insérés = " + myDB.compteurProduits);
		System.out.println("Catégories insérés = " + myDB.compteurCategorie);
		System.out.println("Ingrédients insérés = " + myDB.compteurIngredients);
		System.out.println("Allergenes insérés = " + myDB.compteurAllergenes);
		System.out.println("Additifs insérés = " + myDB.compteurAdditifs);
		
		
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
