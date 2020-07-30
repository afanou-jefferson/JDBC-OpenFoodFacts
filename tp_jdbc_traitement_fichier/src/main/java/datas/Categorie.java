package datas;

import java.util.ArrayList;

public class Categorie extends InfoProduit{ 
	
	private String nomCategorie;

	public Categorie(String nomCategorie) {
		super();
		this.nomCategorie = nomCategorie;
	}

	public String getLibelleCategorie() {
		return nomCategorie;
	}

	public void setLibelleCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	@Override
	public String toString() {
		return "Categorie [nomCategorie=" + nomCategorie + "]";
	}

	@Override
	public String getValeurIdentifiant() {
		// TODO Auto-generated method stub
		return this.nomCategorie;
	}
	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(this.nomCategorie);
		
		return listeValeurs;
	}
	
	
	
}
