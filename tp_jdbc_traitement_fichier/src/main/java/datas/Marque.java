package datas;

import java.util.ArrayList;

public class Marque extends InfoProduit{
	
	private String nomMarque;

	public Marque(String nomMarque) {
		super();
		this.nomMarque = nomMarque;
	}

	public String getNomMarque() {
		return nomMarque;
	}

	public void setNomMarque(String nomMarque) {
		this.nomMarque = nomMarque;
	}

	@Override
	public String toString() {
		return "Marque [nomMarque=" + nomMarque + "]";
	}
	
	public String getEntite() {
		return this.nomMarque;
	}

	@Override
	public String getValeurIdentifiant() {
		// TODO Auto-generated method stub
		return this.nomMarque;
	}

	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(this.nomMarque);
		
		return listeValeurs;
	}
	
	
	

}
