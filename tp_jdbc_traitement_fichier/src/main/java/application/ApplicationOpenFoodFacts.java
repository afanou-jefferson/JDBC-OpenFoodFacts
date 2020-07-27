package application;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

import datas.Datas;

public class ApplicationOpenFoodFacts {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
	

		File fichier = new File(System.getProperty("user.dir")+"/src/main/resources/open-food-facts.csv");
		//System.out.println(fichier.canRead());
		
		Datas myDB = new Datas(fichier);
		
		//myDB.printListeAllergenes();
		//myDB.printListeAdditifs(); 
		//myDB.printListeCategories();
		//myDB.printListeMarques();
	}
}
