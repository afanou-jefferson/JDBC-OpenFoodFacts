package structure_datas;

import java.util.ArrayList;

/**
 * Représente la table Categorie de la BDD qui receuille les catégories qui classifient les produits
 * @author Exost
 *
 */
public class Categorie extends Table{ 
	
	//Attributs en public obligatoire pour accès reflection
	public int id_Categorie;
	public String nom_Categorie;
	
	public Categorie(int id_Categorie, String nom_Categorie) {
		super();
		this.id_Categorie = id_Categorie;
		this.nom_Categorie = nom_Categorie;
	}

	@Override
	public String toString() {
		return "Categorie [nomCategorie=" + nom_Categorie + "]";
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Categorie;
	}
	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(Integer.toString(this.id_Categorie));
		listeValeurs.add(this.nom_Categorie);
		
		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Categorie;
	}
	
	
	
	
}
