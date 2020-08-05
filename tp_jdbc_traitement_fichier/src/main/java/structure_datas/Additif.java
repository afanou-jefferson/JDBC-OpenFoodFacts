package structure_datas;

import java.util.ArrayList;

/**
 * Représente la table Additif de la BDD qui receuilleles Additifs présent dans les produits
 * @author Exost
 *
 */
public class Additif extends Table{
	
	//Attributs en public obligatoire pour accès reflection
	public int id_Additif;
	public String nom_Additif;

	public Additif(int id_Additif, String nom_Additif) {
		super();
		this.id_Additif = id_Additif;
		this.nom_Additif = nom_Additif;
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Additif;
	}
	
	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(Integer.toString(this.id_Additif));
		listeValeurs.add(this.nom_Additif);
		
		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Additif;
	}


	


}
