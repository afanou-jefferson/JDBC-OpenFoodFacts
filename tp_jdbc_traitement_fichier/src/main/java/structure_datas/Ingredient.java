package structure_datas;

import java.util.ArrayList;

public class Ingredient extends Table {
	
	public int id_Ingredient;
	public String nom_Ingredient;

	/**
	 * Représente la table Ingredient de la BDD qui receuille les ingrédients composants les produits
	 * @author Exost
	 *
	 */
	public Ingredient(int id_Ingredient, String nom_Ingredient) {
		super();
		this.id_Ingredient = id_Ingredient;
		this.nom_Ingredient = nom_Ingredient;
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Ingredient;
	}

	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();

		listeValeurs.add(Integer.toString(this.id_Ingredient));
		listeValeurs.add(this.nom_Ingredient);

		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Ingredient;
	}
	
	

}
