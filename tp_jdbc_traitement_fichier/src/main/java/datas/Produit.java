 package datas;

import java.util.ArrayList;

import utils.ArrayListToString;

public class Produit {
	
	private Categorie categorie;
	private ArrayList<Marque> marques;
	private String NomProduit;
	private String gradeNutri;
	private DonneesNutritionnelles donneesNutri;
	private ArrayList<Ingredient> listIngredients;
	private ArrayList<Allergene> listAllergenes;
	private ArrayList<Additif> listAdditifs;
	
	public Produit(Categorie categorie, ArrayList<Marque> listMarques, String nomProduit, String gradeNutri,
			DonneesNutritionnelles donneesNutri, ArrayList<Ingredient> listIngredients,
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

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public ArrayList<Marque> getMarques() {
		return marques;
	}

	public void setMarques(ArrayList<Marque> marques) {
		this.marques = marques;
	}

	public String getNomProduit() {
		return NomProduit;
	}

	public void setNomProduit(String nomProduit) {
		NomProduit = nomProduit;
	}

	public String getGradeNutri() {
		return gradeNutri;
	}

	public void setGradeNutri(String gradeNutri) {
		this.gradeNutri = gradeNutri;
	}

	public DonneesNutritionnelles getDonneesNutri() {
		return donneesNutri;
	}

	public void setDonneesNutri(DonneesNutritionnelles donneesNutri) {
		this.donneesNutri = donneesNutri;
	}

	public ArrayList<Ingredient> getListIngredients() {
		return listIngredients;
	}

	public void setListIngredients(ArrayList<Ingredient> listIngredients) {
		this.listIngredients = listIngredients;
	}

	public ArrayList<Allergene> getListAllergenes() {
		return listAllergenes;
	}

	public void setListAllergenes(ArrayList<Allergene> listAllergenes) {
		this.listAllergenes = listAllergenes;
	}

	public ArrayList<Additif> getListAdditifs() {
		return listAdditifs;
	}

	public void setListAdditifs(ArrayList<Additif> listAdditifs) {
		this.listAdditifs = listAdditifs;
	}
	
	
	

	


}
