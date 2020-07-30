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
import jdbc.JDBCdaoProduit;
import utils.Chrono;
import utils.ConnectionBDD;

public class ApplicationOpenFoodFacts {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

		Connection connectionDB = ConnectionBDD.getConnection();
		File fichier = new File(System.getProperty("user.dir") + "/src/main/resources/open-food-facts.csv");
		System.out.println(fichier.canRead());

		Chrono chrono = new Chrono();
		chrono.start(); // démarrage du chrono

		JDBCdaoProduit daoProduit = new JDBCdaoProduit(connectionDB);
		Datas myDB = new Datas(fichier, connectionDB);

		chrono.stop(); // arrêt
		System.out.println(chrono.getDureeTxt()); // affichage au format "1 h 26 min 32 s"

		try {
			connectionDB.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
