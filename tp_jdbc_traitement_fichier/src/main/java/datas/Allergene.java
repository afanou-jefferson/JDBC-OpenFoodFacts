package datas;

public class Allergene implements Entite{
	
	private String libelleAllergene;

	public Allergene(String libelleAllergene) {
		super();
		this.libelleAllergene = libelleAllergene;
	}

	public String getLibelleAllergene() {
		return libelleAllergene;
	}

	public void setLibelleAllergene(String libelleAllergene) {
		this.libelleAllergene = libelleAllergene;
	}

	@Override
	public String toString() {
		return "Allergene [libelleAllergene=" + libelleAllergene + "]";
	}

	@Override
	public String getEntite() {
		// TODO Auto-generated method stub
		return this.libelleAllergene;
	}
	
	@Override
	public String getClassEntite() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}
	
	
	
	

}
