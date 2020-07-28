package datas;

public class Additif {
	
	public String libelleAdditif;

	public Additif(String libelleAdditif) {
		super();
		this.libelleAdditif = libelleAdditif;
	}

	public String getLibelleAdditif() {
		return libelleAdditif;
	}

	public void setLibelleAdditif(String libelleAdditif) {
		this.libelleAdditif = libelleAdditif;
	}

	@Override
	public String toString() {
		return "Additif [libelleAdditif=" + libelleAdditif + "]";
	}
	
	
	

}
