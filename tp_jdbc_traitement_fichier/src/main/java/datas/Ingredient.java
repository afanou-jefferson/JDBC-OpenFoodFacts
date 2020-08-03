package datas;

import java.sql.SQLException;
import java.util.ArrayList;

public class Ingredient extends Entite {

	public String nom_Ingredient;

	public Ingredient(String nomIngredient) {
		super();
		this.nom_Ingredient = nomIngredient;
	}

	public String getLibelleIngredient() {
		return nom_Ingredient;
	}

	public void setLibelleIngredient(String nomIngredient) {
		this.nom_Ingredient = nomIngredient;
	}

	@Override
	public String toString() {
		return "Ingredient [nomIngredient=" + nom_Ingredient + "]";
	}

	public String getEntite() {
		return this.getLibelleIngredient();
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Ingredient;
	}

	@Override
	public ArrayList<String> getValeurAttributsModel() {
		ArrayList<String> listeValeurs = new ArrayList<String>();

		listeValeurs.add(this.nom_Ingredient);

		return listeValeurs;
	}

}
