package datas;

public class Additif implements Entite{
	
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

	@Override
	public String getEntite() {
		// TODO Auto-generated method stub
		return this.libelleAdditif;
	}

	@Override
	public String getClassEntite() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}
	
	
	


}
