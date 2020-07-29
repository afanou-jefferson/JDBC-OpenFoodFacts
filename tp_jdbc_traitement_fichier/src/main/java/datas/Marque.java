package datas;

public class Marque implements Entite{
	
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
	public String getClassEntite() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}
	

}
