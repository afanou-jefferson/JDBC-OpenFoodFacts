package application;

import java.io.File;
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
import jdbc.JDBCdaoAdditif;
import jdbc.JDBCdaoCategorie;
import jdbc.JDBCdaoIngredient;
import jdbc.JDBCdaoMarque;
import jdbc.JDBCdaoProduit;

public class ApplicationOpenFoodFacts {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
	

		File fichier = new File(System.getProperty("user.dir")+"/src/main/resources/open-food-facts.csv");

		Additif add = new Additif("ex additif");
		ArrayList<Additif> listAdditif = new ArrayList<Additif>();
		listAdditif.add(add);
		
		Categorie cat = new Categorie("ex category");
		
		Ingredient ing = new Ingredient("ex composant");
		ArrayList<Ingredient> listIngredient = new ArrayList<Ingredient>();
		listIngredient.add(ing);
		
		Marque marque = new Marque("ex brand");  
		ArrayList<Marque> listMarque = new ArrayList<Marque>();
		listMarque.add(marque);
		
		Allergene all = new Allergene("ex allergen");
		ArrayList<Allergene> listAllergene = new ArrayList<Allergene>();
		listAllergene.add(all);
		
		DonneesNutritionnelles nutri = new DonneesNutritionnelles();
		Produit prod = new Produit(cat, listMarque, "ex Produit", "a", nutri, listIngredient, listAllergene, listAdditif);
		
		JDBCdaoCategorie daoC = new JDBCdaoCategorie();
		daoC.insert(prod.getCategorie());
		System.out.println("win = "  + daoC.getId_Categorie("Additifs alimentaires"));
		
		JDBCdaoAdditif daoA = new JDBCdaoAdditif();
		System.out.println(prod.getListAdditifs().get(0));
		daoA.insert(prod.getListAdditifs().get(0));
		
		JDBCdaoIngredient daoI = new JDBCdaoIngredient();
		System.out.println(prod.getListIngredients().get(0));
		daoI.insert(prod.getListIngredients().get(0));
		
		JDBCdaoMarque daoM = new JDBCdaoMarque();
		System.out.println(prod.getMarques());
		daoM.insert(prod.getMarques().get(0));
		
		JDBCdaoProduit daoP = new JDBCdaoProduit();
		System.out.println(prod);
		daoP.insert(prod); // Test Produit
		
	}
}
