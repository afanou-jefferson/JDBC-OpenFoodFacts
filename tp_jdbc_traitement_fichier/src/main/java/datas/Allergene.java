package datas;

import java.util.ArrayList;

public class Allergene extends InfoProduit{
	
	private String nomAllergene;

	public Allergene(String nomAllergene) {
		super();
		this.nomAllergene = nomAllergene;
	}

	public String getLibelleAllergene() {
		return nomAllergene;
	}

	public void setLibelleAllergene(String nomAllergene) {
		this.nomAllergene = nomAllergene;
	}

	@Override
	public String toString() {
		return "Allergene [nomAllergene=" + nomAllergene + "]";
	}

	@Override
	public String getValeurIdentifiant() {
		// TODO Auto-generated method stub
		return this.nomAllergene;
	}
	
	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(this.nomAllergene);
		
		return listeValeurs;
	}
	
	
	

}
