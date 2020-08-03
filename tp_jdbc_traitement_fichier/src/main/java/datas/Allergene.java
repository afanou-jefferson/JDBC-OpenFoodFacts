package datas;

import java.util.ArrayList;

public class Allergene extends Entite{
	
	public String nom_Allergene;

	public Allergene(String nomAllergene) {
		super();
		this.nom_Allergene = nomAllergene;
	}

	public String getLibelleAllergene() {
		return nom_Allergene;
	}

	public void setLibelleAllergene(String nomAllergene) {
		this.nom_Allergene = nomAllergene;
	}

	@Override
	public String toString() {
		return "Allergene [nomAllergene=" + nom_Allergene + "]";
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Allergene;
	}
	
	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(this.nom_Allergene);
		
		return listeValeurs;
	}
	
	
	

}
