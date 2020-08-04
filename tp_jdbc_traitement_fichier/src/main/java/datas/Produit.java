package datas;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Produit extends Entite {

	public static int compteurIDProduit;
	
	private String NomProduit;
	private String gradeNutri;
	private int idCategorie;
	
	ArrayList<Additif> listeAdditifsDuProduit = new ArrayList<Additif>();
	ArrayList<Ingredient> listeIngredientsDuProduit = new ArrayList<Ingredient>();
	ArrayList<Marque> listeMarquesDuProduit = new ArrayList<Marque>();
	ArrayList<Allergene> listeAllergenesDuProduit = new ArrayList<Allergene>();




	public Produit(String nomProduit, String gradeNutri, int idCategorie, ArrayList<Additif> listeAdditifsDuProduit,
			ArrayList<Ingredient> listeIngredientsDuProduit, ArrayList<Marque> listeMarquesDuProduit,
			ArrayList<Allergene> listeAllergenesDuProduit) {
		super();
		NomProduit = nomProduit;
		this.gradeNutri = gradeNutri;
		this.idCategorie = idCategorie;
		this.listeAdditifsDuProduit = listeAdditifsDuProduit;
		this.listeIngredientsDuProduit = listeIngredientsDuProduit;
		this.listeMarquesDuProduit = listeMarquesDuProduit;
		this.listeAllergenesDuProduit = listeAllergenesDuProduit;
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




	public int getIdCategorie() {
		return idCategorie;
	}




	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}




	public ArrayList<Additif> getListeAdditifsDuProduit() {
		return listeAdditifsDuProduit;
	}




	public void setListeAdditifsDuProduit(ArrayList<Additif> listeAdditifsDuProduit) {
		this.listeAdditifsDuProduit = listeAdditifsDuProduit;
	}




	public ArrayList<Ingredient> getListeIngredientsDuProduit() {
		return listeIngredientsDuProduit;
	}




	public void setListeIngredientsDuProduit(ArrayList<Ingredient> listeIngredientsDuProduit) {
		this.listeIngredientsDuProduit = listeIngredientsDuProduit;
	}




	public ArrayList<Marque> getListeMarquesDuProduit() {
		return listeMarquesDuProduit;
	}




	public void setListeMarquesDuProduit(ArrayList<Marque> listeMarquesDuProduit) {
		this.listeMarquesDuProduit = listeMarquesDuProduit;
	}




	public ArrayList<Allergene> getListeAllergenesDuProduit() {
		return listeAllergenesDuProduit;
	}




	public void setListeAllergenesDuProduit(ArrayList<Allergene> listeAllergenesDuProduit) {
		this.listeAllergenesDuProduit = listeAllergenesDuProduit;
	}




	@Override
	public ArrayList<String> getValeurAttributsModel() {
		// TODO Auto-generated method stub
		ArrayList<String> valeursAttributs = new ArrayList<>();
		
		valeursAttributs.add(this.NomProduit);
		valeursAttributs.add(this.gradeNutri);
		valeursAttributs.add(Integer.toString(this.idCategorie));
		
		return valeursAttributs;
		
	}


	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return null;
	}

}
