package datas;

public class Marque {
	
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
	
	

}
