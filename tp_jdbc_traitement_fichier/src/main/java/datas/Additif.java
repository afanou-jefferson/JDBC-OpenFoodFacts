package datas;

import java.util.ArrayList;

public class Additif extends InfoProduit{
	
	public String nom_Additif;

	public Additif(String nomAdditif) {
		super();
		this.nom_Additif = nomAdditif;
	}

	public String getLibelleAdditif() {
		return nom_Additif;
	}

	public void setLibelleAdditif(String nomAdditif) {
		this.nom_Additif = nomAdditif;
	}

	@Override
	public String toString() {
		return "Additif [nomAdditif=" + nom_Additif + "]";
	}


	@Override
	public String getValeurIdentifiant() {
		// TODO Auto-generated method stub
		return this.nom_Additif;
	}
	
	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(this.nom_Additif);
		
		return listeValeurs;
	}


}
