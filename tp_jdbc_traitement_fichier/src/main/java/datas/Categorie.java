package datas;

public class Categorie implements Entite{ 
	
	private String libelleCategorie;

	public Categorie(String libelleCategorie) {
		super();
		this.libelleCategorie = libelleCategorie;
	}

	public String getLibelleCategorie() {
		return libelleCategorie;
	}

	public void setLibelleCategorie(String libelleCategorie) {
		this.libelleCategorie = libelleCategorie;
	}

	@Override
	public String toString() {
		return "Categorie [libelleCategorie=" + libelleCategorie + "]";
	}

	@Override
	public String getEntite() {
		// TODO Auto-generated method stub
		return this.getLibelleCategorie();
	}
	
	@Override
	public String getClassEntite() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}
	
	
	
}
