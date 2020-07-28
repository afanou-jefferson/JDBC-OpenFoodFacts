package datas;

public class Allergene {
	
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
	
	
	
	

}
