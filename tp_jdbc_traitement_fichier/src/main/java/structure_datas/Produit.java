package structure_datas;

import java.util.ArrayList;

public class Produit extends Table {

	public int id_Produit;
	public String nom_Produit;
	public String grade_nutri_produit;
	public int id_Categorie;
	public ArrayList<Marque> listeMarquesDuProduit;
	public ArrayList<Ingredient> listeIngredientsDuProduit;
	public ArrayList<Allergene> listeAllergenesDuProduit;
	public ArrayList<Additif> listeAdditifsDuProduit;
	

	public Produit(int id_Produit, String nom_Produit, String grade_nutri_produit, int id_Categorie) {
		super();
		this.id_Produit = id_Produit;
		this.nom_Produit = nom_Produit;
		this.grade_nutri_produit = grade_nutri_produit;
		this.id_Categorie = id_Categorie;
	}

	public Produit(int id_Produit, String nom_Produit, String grade_nutri_produit, int id_Categorie,
			ArrayList<Marque> listeMarquesDuProduit, ArrayList<Ingredient> listeIngredientsDuProduit,
			ArrayList<Allergene> listeAllergenesDuProduit, ArrayList<Additif> listeAdditifsDuProduit) {
		super();
		this.id_Produit = id_Produit;
		this.nom_Produit = nom_Produit;
		this.grade_nutri_produit = grade_nutri_produit;
		this.id_Categorie = id_Categorie;
		this.listeMarquesDuProduit = listeMarquesDuProduit;
		this.listeIngredientsDuProduit = listeIngredientsDuProduit;
		this.listeAllergenesDuProduit = listeAllergenesDuProduit;
		this.listeAdditifsDuProduit = listeAdditifsDuProduit;
	}

	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();

		listeValeurs.add(Integer.toString(this.id_Produit));
		listeValeurs.add(this.nom_Produit);
		listeValeurs.add(this.grade_nutri_produit);
		listeValeurs.add(Integer.toString(this.id_Categorie));

		return listeValeurs;
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Produit;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Produit;
	}

}