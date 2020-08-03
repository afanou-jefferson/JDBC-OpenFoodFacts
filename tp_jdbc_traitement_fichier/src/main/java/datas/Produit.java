package datas;

import java.util.ArrayList;

import utils.ArrayListToString;

public class Produit {

	private int idCategorie;
	private ArrayList<Integer> listIDsMarques;
	private String NomProduit;
	private String gradeNutri;
	// private DonneesNutritionnelles donneesNutri;
	private ArrayList<Integer> listIDsIngredients;
	private ArrayList<Integer> listIDsAllergenes;
	private ArrayList<Integer> listIDsAdditifs;

	public Produit(int idCategorie, ArrayList<Integer> listIDsMarques, String nomProduit, String gradeNutri,
			ArrayList<Integer> listIDsIngredients, ArrayList<Integer> listIDsAllergenes,
			ArrayList<Integer> listIDsAdditifs) {
		super();
		this.idCategorie = idCategorie;
		this.listIDsMarques = listIDsMarques;
		NomProduit = nomProduit;
		this.gradeNutri = gradeNutri;
		this.listIDsIngredients = listIDsIngredients;
		this.listIDsAllergenes = listIDsAllergenes;
		this.listIDsAdditifs = listIDsAdditifs;
	}
	

	public int getIdCategorie() {
		return idCategorie;
	}


	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}


	public ArrayList<Integer> getListIDsIngredients() {
		return listIDsIngredients;
	}

	public ArrayList<Integer> getListIDsMarques() {
		return listIDsMarques;
	}

	public void setListIDsMarques(ArrayList<Integer> listIDsMarques) {
		this.listIDsMarques = listIDsMarques;
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

	public void setListIDsIngredients(ArrayList<Integer> listIDsIngredients) {
		this.listIDsIngredients = listIDsIngredients;
	}

	public ArrayList<Integer> getListIDsAllergenes() {
		return listIDsAllergenes;
	}

	public void setListIDsAllergenes(ArrayList<Integer> listIDsAllergenes) {
		this.listIDsAllergenes = listIDsAllergenes;
	}

	public ArrayList<Integer> getListIDsAdditifs() {
		return listIDsAdditifs;
	}

	public void setListIDsAdditifs(ArrayList<Integer> listIDsAdditifs) {
		this.listIDsAdditifs = listIDsAdditifs;
	}

}
