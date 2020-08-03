package datas;

import java.util.ArrayList;

public class Categorie extends Entite{ 
	
	public String nom_Categorie;

	public Categorie(String nomCategorie) {
		super();
		this.nom_Categorie = nomCategorie;
	}
	
	public Categorie(){};

	public String getNomCategorie() {
		return nom_Categorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nom_Categorie = nomCategorie;
	}

	@Override
	public String toString() {
		return "Categorie [nomCategorie=" + nom_Categorie + "]";
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Categorie;
	}
	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(this.nom_Categorie);
		
		return listeValeurs;
	}
	
	
	
}
