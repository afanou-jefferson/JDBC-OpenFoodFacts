package structure_datas;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Table abstraite parente de tous les objets de la table visant à factoriser les attributs et méthodes permettant de manipulé les tables.
 * @author Exost
 *
 */
public abstract class Table {
	
	private int idEnBDD;
	private String nomTable = getNomTable();
	
	/**
	 * Utilise la reflection pour obtenir le nom de la table de l'objet this. 
	 * Exemple : 
	 * Produit p1 = new Produit();
	 * String nomTable = p1.getNomTable();
	 * nomTable.equals("Produit") == true 
	 * @return String du nom de la table this
	 */
	public String getNomTable() {
		String nomTable = this.getClass().getSimpleName();	
		return nomTable;
	}

	/**
	 * Utilise la reflextion pour obtenir le nombre d'attributs de la table this.
	 * Exemple : 
	 * Objet objet1 = new Objet(a,b);
	 * int nbAttributObjet = objet1.getNbAttributsModel();
	 * (nbAttibutObjet == 2) == true
	 * @return Un Integer du nombre d'attribut de l'objet this 
	 */
	public int getNbAttributsTable() {
		return this.getClass().getFields().length;
	}
	
	/** 
	 * Sert à obtenir le nom des colonnes(/attributs) de la table this
	 * @return ArrayList contenant les attributs de l'objet this dans l'ordre de déclaration
	 */
	public ArrayList<String> getNomAttributsTable() {
		ArrayList<String> tabNomAttributs = new ArrayList<String>();
		for ( Field field : this.getClass().getFields() ) {
			tabNomAttributs.add(field.getName());
		}
		return tabNomAttributs;
	}
	
	/** 
	 * Sert à obtenir les valeurs des attributs de l'objet this
	 * @return une arrayList contenant les valeurs des attributs de this sous forme de Strings
	 */
	public abstract ArrayList<String> getValeurAttributsTable();
	
	/**
	 * Sert à obtenir le nom de l'objet, servant de clé dans les HashMap servant de BDD locale dans nos traitements 
	 * @return String du nom de l'objet servant de clé
	 */
	public abstract String getNomUnique();

	/**
	 * Sert à obtenir l'identifiant de l'objet this en BDD
	 * @return id_$NomTable en BDD en integer
	 */
	public abstract int getID();

}

