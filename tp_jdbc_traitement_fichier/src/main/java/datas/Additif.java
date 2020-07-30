package datas;

import java.util.ArrayList;

public class Additif extends InfoProduit{
	
	public String nomAdditif;

	public Additif(String nomAdditif) {
		super();
		this.nomAdditif = nomAdditif;
	}

	public String getLibelleAdditif() {
		return nomAdditif;
	}

	public void setLibelleAdditif(String nomAdditif) {
		this.nomAdditif = nomAdditif;
	}

	@Override
	public String toString() {
		return "Additif [nomAdditif=" + nomAdditif + "]";
	}


	@Override
	public String getValeurIdentifiant() {
		// TODO Auto-generated method stub
		return this.nomAdditif;
	}
	
	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(this.nomAdditif);
		
		return listeValeurs;
	}


}
