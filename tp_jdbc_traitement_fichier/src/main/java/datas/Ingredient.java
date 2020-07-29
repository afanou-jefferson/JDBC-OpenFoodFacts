package datas;

public class Ingredient implements Entite{
	
	private String libelleIngredient;

	public Ingredient(String libelleIngredient) {
		super();
		this.libelleIngredient = libelleIngredient;
	}

	public String getLibelleIngredient() {
		return libelleIngredient;
	}

	public void setLibelleIngredient(String libelleIngredient) {
		this.libelleIngredient = libelleIngredient;
	}

	@Override
	public String toString() {
		return "Ingredient [libelleIngredient=" + libelleIngredient + "]";
	}
	

	public String getEntite() {
		return this.getLibelleIngredient();
	}
	
	@Override
	public String getClassEntite() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}
	

}
