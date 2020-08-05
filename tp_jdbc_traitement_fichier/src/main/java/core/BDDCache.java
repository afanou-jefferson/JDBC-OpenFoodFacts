package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import exceptions.TraitementFichierException;
import structure_datas.Additif;
import structure_datas.Allergene;
import structure_datas.Categorie;
import structure_datas.Table;
import structure_datas.Ingredient;
import structure_datas.Marque;
import structure_datas.Produit;
import utils.NettoyageString;

/**
 * Classe visant à simuler et stocker le contenu de la BDD distante en locale afin de minimiser le nombre de requête.
 * @author Exost
 *
 */
public class BDDCache {
	
	Connection connection;
	
	private HashMap<String, Table> memoireLocaleAdditifsBDD;
	private HashMap<String, Table> memoireLocaleIngredientsBDD;
	private HashMap<String, Table> memoireLocaleMarquesBDD;
	private HashMap<String, Table> memoireLocaleAllergenesBDD;
	private HashMap<String, Table> memoireLocaleCategoriesBDD;
	private HashMap<String, Table> memoireLocaleProduitsBDD;

	private int compteurIDCategorie;
	private int compteurIDAdditif;
	private int compteurIDIngredient;
	private int compteurIDMarque;
	private int compteurIDAllergene;
	private int compteurIDProduit;
	
	/**
	 * Instanciée au lancement du programme afin de récupéré l'ensemble de la BDD existante sous forme d'objets et gérer les ID des objets à insérer.
	 * @param connectionParam = Un objet java.sql.Connection relié à la BDD
	 */
	public BDDCache(Connection connectionParam) {
		
		this.connection = connectionParam;
		
		this.memoireLocaleAdditifsBDD = selectAllFromTable(Additif.class.getSimpleName());
		this.compteurIDAdditif = selectMaxIDfromTable(Additif.class.getSimpleName());
		
		this.memoireLocaleIngredientsBDD = selectAllFromTable(Ingredient.class.getSimpleName());
		this.compteurIDIngredient = selectMaxIDfromTable(Ingredient.class.getSimpleName());
		
		this.memoireLocaleMarquesBDD = selectAllFromTable(Marque.class.getSimpleName());
		this.compteurIDMarque = selectMaxIDfromTable(Marque.class.getSimpleName());
		
		this.memoireLocaleAllergenesBDD = selectAllFromTable(Allergene.class.getSimpleName());
		this.compteurIDAllergene = selectMaxIDfromTable(Allergene.class.getSimpleName());
		
		this.memoireLocaleCategoriesBDD = selectAllFromTable(Categorie.class.getSimpleName());
		this.compteurIDCategorie = selectMaxIDfromTable(Categorie.class.getSimpleName());
		
		this.memoireLocaleProduitsBDD = selectAllFromTable(Produit.class.getSimpleName());
		this.compteurIDProduit = selectMaxIDfromTable(Produit.class.getSimpleName());	
	}
	
	/**
	 * Selectionne tous les enregistrements de la table placée en paramètre et les convertis en objets stockée dans une Hashmap
	 * @param nomTable
	 * @return Hashmap dont la clé est nom_$nomTable et la valeur l'objet correspondant
	 */
	public HashMap<String, Table> selectAllFromTable(String nomTable) {

		HashMap<String, Table> tableExistanteEnBDD = new HashMap<String, Table>();

		String query = "SELECT * FROM " + nomTable;

		try {
			PreparedStatement selectTable = this.connection.prepareStatement(query);

			ResultSet result = selectTable.executeQuery();

			while (result.next()) {

				switch (nomTable) {
				case "Additif":
					Additif additifEnBDD = new Additif((result.getInt(1)),result.getString(2));
					tableExistanteEnBDD.put(additifEnBDD.nom_Additif, additifEnBDD);
					break;
					
				case "Allergene":
					Allergene allergeneEnBDD = new Allergene(result.getInt(1),result.getString(2));
					tableExistanteEnBDD.put(allergeneEnBDD.nom_Allergene, allergeneEnBDD);
					break;
					
				case "Categorie" :
					Categorie categorieEnBDD = new Categorie(result.getInt(1),result.getString(2));
					tableExistanteEnBDD.put(categorieEnBDD.nom_Categorie, categorieEnBDD);
					break;
					
				case "Ingredient":
					Ingredient ingredientEnBDD = new Ingredient(result.getInt(1),result.getString(2));
					tableExistanteEnBDD.put(ingredientEnBDD.nom_Ingredient, ingredientEnBDD);
					break;
				
				case "Marque":
					Marque marqueEnBDD = new Marque(result.getInt(1),result.getString(2));
					tableExistanteEnBDD.put(marqueEnBDD.nom_Marque, marqueEnBDD);
					break;
				}
			}
			return tableExistanteEnBDD;

		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur SQL : Echec lors de l'importation de la table " + nomTable + " en mémoire cache", e);
		}
	}

	/**
	 * Recupère le plus grand id_$nomTable (primary key) de la table placée en paramètre
	 * @param nomTable
	 * @return Entier du MAX(id_$nomTable) from $nomTable
	 */
	public int selectMaxIDfromTable(String nomTable) {
		
		int maxID = 0;
		
		String query = "SELECT max(id_"+ nomTable +") FROM " + nomTable + ";";
		
		try {
			PreparedStatement selectMaxIDTable = this.connection.prepareStatement(query);

			ResultSet result = selectMaxIDTable.executeQuery();
			
			if ( result.next()) {
				maxID = result.getInt(1);
			}
		} catch (SQLException e) {
			throw new TraitementFichierException("Erreur SQL : Echec lors de la récupération du MAX(ID_table) de la table " + nomTable, e);
		}
		
		return maxID;		
	}


	
	public int getNewID(String nomTable) {
		int newID = 0;
		
		return newID;
	}
	
	// GETTERS AND SETTERS 
	public HashMap<String, Table> getMemoireLocaleAdditifsBDD() {
		return memoireLocaleAdditifsBDD;
	}


	public void setMemoireLocaleAdditifsBDD(HashMap<String, Table> memoireLocaleAdditifsBDD) {
		this.memoireLocaleAdditifsBDD = memoireLocaleAdditifsBDD;
	}


	public HashMap<String, Table> getMemoireLocaleIngredientsBDD() {
		return memoireLocaleIngredientsBDD;
	}


	public void setMemoireLocaleIngredientsBDD(HashMap<String, Table> memoireLocaleIngredientsBDD) {
		this.memoireLocaleIngredientsBDD = memoireLocaleIngredientsBDD;
	}


	public HashMap<String, Table> getMemoireLocaleMarquesBDD() {
		return memoireLocaleMarquesBDD;
	}


	public void setMemoireLocaleMarquesBDD(HashMap<String, Table> memoireLocaleMarquesBDD) {
		this.memoireLocaleMarquesBDD = memoireLocaleMarquesBDD;
	}


	public HashMap<String, Table> getMemoireLocaleAllergenesBDD() {
		return memoireLocaleAllergenesBDD;
	}


	public void setMemoireLocaleAllergenesBDD(HashMap<String, Table> memoireLocaleAllergenesBDD) {
		this.memoireLocaleAllergenesBDD = memoireLocaleAllergenesBDD;
	}


	public HashMap<String, Table> getMemoireLocaleCategoriesBDD() {
		return memoireLocaleCategoriesBDD;
	}


	public void setMemoireLocaleCategoriesBDD(HashMap<String, Table> memoireLocaleCategoriesBDD) {
		this.memoireLocaleCategoriesBDD = memoireLocaleCategoriesBDD;
	}


	public HashMap<String, Table> getMemoireLocaleProduitsBDD() {
		return memoireLocaleProduitsBDD;
	}


	public void setMemoireLocaleProduitsBDD(HashMap<String, Table> memoireLocaleProduitsBDD) {
		this.memoireLocaleProduitsBDD = memoireLocaleProduitsBDD;
	}


	public int getCompteurIDCategorie() {
		return compteurIDCategorie;
	}


	public void setCompteurIDCategorie(int compteurIDCategorie) {
		this.compteurIDCategorie = compteurIDCategorie;
	}


	public int getCompteurIDAdditif() {
		return compteurIDAdditif;
	}


	public void setCompteurIDAdditif(int compteurIDAdditif) {
		this.compteurIDAdditif = compteurIDAdditif;
	}


	public int getCompteurIDIngredient() {
		return compteurIDIngredient;
	}


	public void setCompteurIDIngredient(int compteurIDIngredient) {
		this.compteurIDIngredient = compteurIDIngredient;
	}


	public int getCompteurIDMarque() {
		return compteurIDMarque;
	}


	public void setCompteurIDMarque(int compteurIDMarque) {
		this.compteurIDMarque = compteurIDMarque;
	}


	public int getCompteurIDAllergene() {
		return compteurIDAllergene;
	}


	public void setCompteurIDAllergene(int compteurIDAllergene) {
		this.compteurIDAllergene = compteurIDAllergene;
	}


	public int getCompteurIDProduit() {
		return compteurIDProduit;
	}


	public void setCompteurIDProduit(int compteurIDProduit) {
		this.compteurIDProduit = compteurIDProduit;
	}
	
}
