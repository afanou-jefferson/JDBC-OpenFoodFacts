package datas;

import java.util.ArrayList;

public class Marque extends InfoProduit{
	
	public String nom_Marque;

	public Marque(String nomMarque) {
		super();
		this.nom_Marque = nomMarque;
	}

	public String getNomMarque() {
		return nom_Marque;
	}

	public void setNomMarque(String nomMarque) {
		this.nom_Marque = nomMarque;
	}

	@Override
	public String toString() {
		return "Marque [nomMarque=" + nom_Marque + "]";
	}
	
	public String getEntite() {
		return this.nom_Marque;
	}

	@Override
	public String getValeurIdentifiant() {
		// TODO Auto-generated method stub
		return this.nom_Marque;
	}

	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(this.nom_Marque);
		
		return listeValeurs;
	}
	
	
	

}
