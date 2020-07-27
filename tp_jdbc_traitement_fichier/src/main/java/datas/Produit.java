package datas;

import java.util.ArrayList;

public class Produit {
	
	private Categorie categorie;
	private Marque marque;
	private String NomProduit;
	private String gradeNutri;
	private donneesNutritionnelles donneesNutri;
	private ArrayList<Ingredient> listIngredients;
	private ArrayList<Allergene> listAllergenes;
	private ArrayList<Additif> listAdditifs;
	
	public Produit(Categorie categorie, Marque marque, String nomProduit, String gradeNutri,
			donneesNutritionnelles donneesNutri, ArrayList<Ingredient> listIngredients,
			ArrayList<Allergene> listAllergenes, ArrayList<Additif> listAdditifs) {
		super();
		this.categorie = categorie;
		this.marque = marque;
		this.NomProduit = nomProduit;
		this.gradeNutri = gradeNutri;
		this.donneesNutri = donneesNutri;
		this.listIngredients = listIngredients;
		this.listAllergenes = listAllergenes;
		this.listAdditifs = listAdditifs;
	}

	@Override
	public String toString() {
		return "Produit [categorie=" + categorie.getLibelleCategorie() + ", marque=" + marque.getNomMarque() + ", NomProduit=" + NomProduit + ", gradeNutri="
				+ gradeNutri + ", donneesNutri=" + donneesNutri + ", listIngredients=" + listIngredients.toString()
				+ ", listAllergenes=" + listAllergenes.toString() + ", listAdditifs=" + listAdditifs.toString() + "]";
	}
	
	
	

	


}
