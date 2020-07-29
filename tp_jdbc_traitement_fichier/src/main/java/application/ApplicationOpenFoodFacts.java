package application;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

import datas.Datas;
import jdbc.JDBCdaoCategorie;
import jdbc.JDBCdaoProduit;

public class ApplicationOpenFoodFacts {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
	

		File fichier = new File(System.getProperty("user.dir")+"/src/main/resources/open-food-facts.csv");
		//System.out.println(fichier.canRead());
		
		Datas myDB = new Datas(fichier);
		
		//myDB.printListeAllergenes();
		//myDB.printListeAdditifs(); 
		//myDB.printListeCategories();
		//myDB.printListeMarques();
		
		JDBCdaoProduit daoP = new JDBCdaoProduit();
		System.out.println(myDB.getListeProduit().get(11423).toString());
		daoP.insert(myDB.getListeProduit().get(11423)); // Test Produit
		
		
		JDBCdaoCategorie daoC = new JDBCdaoCategorie();
		//daoC.insert(myDB.getListeCategorie().get(0));
		//System.out.println("win = "  + daoC.getId_Categorie("Additifs alimentaires"));
	}
}
