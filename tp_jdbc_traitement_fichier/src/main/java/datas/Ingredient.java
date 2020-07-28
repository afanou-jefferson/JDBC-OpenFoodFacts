package datas;

public class Ingredient {
	
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
	
	
	

}
