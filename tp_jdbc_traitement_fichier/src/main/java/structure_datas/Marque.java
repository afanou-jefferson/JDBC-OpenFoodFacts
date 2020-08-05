package structure_datas;

import java.util.ArrayList;

/**
 * Représente la table Categorie de la BDD qui receuille les catégories qui classifient les produits
 * @author Exost
 *
 */
public class Marque extends Table{
	
	public int id_Marque;
	public String nom_Marque;

	public Marque(int id_Marque, String nom_Marque) {
		super();
		this.id_Marque = id_Marque;
		this.nom_Marque = nom_Marque;
	}

	@Override
	public String toString() {
		return "Marque [id_Marque=" + id_Marque + ", nom_Marque=" + nom_Marque + "]";
	}

	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Marque;
	}

	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(Integer.toString(this.id_Marque));
		listeValeurs.add(this.nom_Marque);
		
		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Marque;
	}
	
	
	
	
	

}
