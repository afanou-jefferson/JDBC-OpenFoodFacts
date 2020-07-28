package datas;

import java.util.ArrayList;

import utils.ArrayListToString;

public class Produit {
	
	private Categorie categorie;
	private ArrayList<Marque> marques;
	private String NomProduit;
	private String gradeNutri;
	private donneesNutritionnelles donneesNutri;
	private ArrayList<Ingredient> listIngredients;
	private ArrayList<Allergene> listAllergenes;
	private ArrayList<Additif> listAdditifs;
	
	public Produit(Categorie categorie, ArrayList<Marque> listMarques, String nomProduit, String gradeNutri,
			donneesNutritionnelles donneesNutri, ArrayList<Ingredient> listIngredients,
			ArrayList<Allergene> listAllergenes, ArrayList<Additif> listAdditifs) {
		super();
		this.categorie = categorie;
		this.marques = listMarques;
		this.NomProduit = nomProduit;
		this.gradeNutri = gradeNutri;
		this.donneesNutri = donneesNutri;
		this.listIngredients = listIngredients;
		this.listAllergenes = listAllergenes;
		this.listAdditifs = listAdditifs;
	}

	@Override
	public String toString() {
		return "Produit [categorie=" + categorie.getLibelleCategorie() + ", marque=" + marques + ", NomProduit=" + NomProduit + ", gradeNutri="
				+ gradeNutri + ", donneesNutri=" + donneesNutri + ", listIngredients=" + ArrayListToString.getStringArrayList(this.listIngredients)
				+ ", listAllergenes=" + ArrayListToString.getStringArrayList(this.listAllergenes) + ", listAdditifs=" + ArrayListToString.getStringArrayList(this.listAdditifs) + "]";
	}
	
	
	

	


}
