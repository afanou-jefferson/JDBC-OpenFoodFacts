package datas;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Cleaner.Cleanable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle.Control;

import org.apache.commons.io.FileUtils;

import jdbc.JDBCdaoGenerique;
import jdbc.JDBCdaoProduit;
import utils.NettoyageString;

public class Datas {

	Connection connection;

	Categorie categorieDuProduit;
	ArrayList<Additif> listeAdditifsDuProduit = new ArrayList<Additif>();
	ArrayList<Ingredient> listeIngredientsDuProduit = new ArrayList<Ingredient>();
	ArrayList<Marque> listeMarquesDuProduit = new ArrayList<Marque>();
	ArrayList<Allergene> listeAllergenesDuProduit = new ArrayList<Allergene>();
	ArrayList<Produit> listeProduitToInsert = new ArrayList<Produit>();

	HashMap<String, Entite> memoireLocaleAdditifsBDD;
	HashMap<String, Entite> memoireLocaleIngredientsBDD;
	HashMap<String, Entite> memoireLocaleMarquesBDD;
	HashMap<String, Entite> memoireLocaleAllergenesBDD;
	HashMap<String, Entite> memoireLocaleCategoriesBDD;
	HashMap<String, Entite> memoireLocaleProduitsBDD;

	String bufferRequetes = "";

	public int compteurCategorie = 0;
	public int compteurAdditifs = 0;
	public int compteurIngredients = 0;
	public int compteurMarques = 0;
	public int compteurAllergenes = 0;
	public int compteurProduits = 0;

	public Datas(File fichier, Connection connection) throws IllegalArgumentException, IllegalAccessException {

		this.connection = connection;

		JDBCdaoProduit daoProduit = new JDBCdaoProduit(this.connection);
		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);

		this.memoireLocaleAdditifsBDD = daoGenerique.selectAllFromTable(Additif.class.getSimpleName());
		Additif.compteurIDAdditif = daoGenerique.selectMaxIDfromTable(Additif.class.getSimpleName());
		
		this.memoireLocaleIngredientsBDD = daoGenerique.selectAllFromTable(Ingredient.class.getSimpleName());
		Ingredient.compteurIDIngredient = daoGenerique.selectMaxIDfromTable(Ingredient.class.getSimpleName());
		
		this.memoireLocaleMarquesBDD = daoGenerique.selectAllFromTable(Marque.class.getSimpleName());
		Marque.compteurIDMarque = daoGenerique.selectMaxIDfromTable(Marque.class.getSimpleName());
		
		this.memoireLocaleAllergenesBDD = daoGenerique.selectAllFromTable(Allergene.class.getSimpleName());
		Allergene.compteurIDAllergene = daoGenerique.selectMaxIDfromTable(Allergene.class.getSimpleName());
		
		this.memoireLocaleCategoriesBDD = daoGenerique.selectAllFromTable(Categorie.class.getSimpleName());
		Categorie.compteurIDCategorie = daoGenerique.selectMaxIDfromTable(Categorie.class.getSimpleName());
		
		this.memoireLocaleProduitsBDD = daoGenerique.selectAllFromTable(Produit.class.getSimpleName());
		Produit.compteurIDProduit = daoGenerique.selectMaxIDfromTable(Produit.class.getSimpleName());
		

		try {
			File file = fichier;
			List<String> lignes = FileUtils.readLines(file, "UTF-8");

			// Start 1 pour sauter ligne intitules categories
			for (int i = 1; i < lignes.size(); i++) {

				String[] morceaux = lignes.get(i).split("\\|", -1);

				String nomProduit = NettoyageString.nettoyerString(morceaux[2]);
				String gradeNutriProduit = morceaux[3];
				int idCategorie = traitementCategorie(morceaux[0]);
				this.listeMarquesDuProduit = traitementMarque(morceaux[1]);
				this.listeIngredientsDuProduit = traitementIngredient(morceaux[4]);
				this.listeAllergenesDuProduit = traitementAllergenes(morceaux[28]);
				this.listeAdditifsDuProduit = traitementAdditifs(morceaux[29]);
				
				String cleanNomProduit = NettoyageString.nettoyerString(nomProduit.toLowerCase());	
				Produit newProduit = new Produit(cleanNomProduit, gradeNutriProduit, idCategorie, listeAdditifsDuProduit, listeIngredientsDuProduit, listeMarquesDuProduit, listeAllergenesDuProduit);
				if (memoireLocaleProduitsBDD.get(cleanNomProduit) == null) {
					//int idNewProduit = daoProduit.insert(newProduit);
					memoireLocaleProduitsBDD.put(cleanNomProduit, newProduit);
					listeProduitToInsert.add(newProduit);
					compteurProduits++;
				}
				System.out.println(i);
			}
			
			daoGenerique.insertAll(this.bufferRequetes);
			
			/*for ( Produit produit : this.listeProduitToInsert) {
				daoProduit.insert(produit);
			}*/
			
			
			
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private ArrayList<Additif> traitementAdditifs(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		ArrayList<Additif> listeAdditifsProduit = new ArrayList<Additif>();

		String[] elemStringAdditif = morceauString.split(",");
		for (String nomAdditif : elemStringAdditif) {

			// Nettoyage spécial
			String cleanAdditif = nomAdditif.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,%]", " ")
					.replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").trim();

			Additif additifEnLecture = new Additif("");

			if (cleanAdditif.equals("")) {
				additifEnLecture = new Additif("ERREUR");
			} else {
				additifEnLecture = new Additif(cleanAdditif);
			}

			if (memoireLocaleAdditifsBDD
					.get(NettoyageString.nettoyerString(additifEnLecture.getLibelleAdditif())) == null) {

				this.bufferRequetes = bufferRequetes + daoGenerique.insert(additifEnLecture);
				this.memoireLocaleAdditifsBDD.put(additifEnLecture.getNomUnique(), additifEnLecture);

				listeAdditifsProduit.add(additifEnLecture);
				this.compteurAdditifs++;
			}
		}
		return listeAdditifsProduit;
	}

	private ArrayList<Allergene> traitementAllergenes(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		ArrayList<Allergene> listIDAllergenesProduit = new ArrayList<Allergene>();

		String[] elemStringAllergene = morceauString.split(",");
		for (String nomAllergene : elemStringAllergene) {

			String cleanAllergene = NettoyageString.nettoyerString(nomAllergene);

			Allergene allergeneEnLecture = new Allergene("");

			if (cleanAllergene.equals("")) {
				allergeneEnLecture = new Allergene("ERREUR");
			} else {
				allergeneEnLecture = new Allergene(cleanAllergene);
			}

			if (memoireLocaleAllergenesBDD
					.get(NettoyageString.nettoyerString(allergeneEnLecture.getLibelleAllergene())) == null) {

				this.bufferRequetes = bufferRequetes + daoGenerique.insert(allergeneEnLecture);
				this.memoireLocaleAllergenesBDD.put(allergeneEnLecture.getLibelleAllergene(), allergeneEnLecture);

				listIDAllergenesProduit.add(allergeneEnLecture);
				this.compteurAllergenes++;
			}
		}
		return listIDAllergenesProduit;
	}

	private int traitementCategorie(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);

		String cleanCategorie = NettoyageString.nettoyerString(morceauString);
		Categorie categorieEnLecture = new Categorie("");

		if (cleanCategorie.equals("")) {
			categorieEnLecture = new Categorie("ERREUR");
		} else {
			categorieEnLecture = new Categorie(cleanCategorie);
		}

		int idCategorieBDD = 0;

		if (memoireLocaleCategoriesBDD.get(categorieEnLecture.getNomCategorie()) == null) {
			bufferRequetes = bufferRequetes + daoGenerique.insert(categorieEnLecture);
			this.memoireLocaleAdditifsBDD.put(categorieEnLecture.getNomCategorie(), categorieEnLecture);
			this.compteurCategorie++;
		} else {
			idCategorieBDD = daoGenerique.getIDFromNom(cleanCategorie, categorieEnLecture);
		}
		return idCategorieBDD;
	}

	private ArrayList<Marque> traitementMarque(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringMarque = morceauString.split(",");
		ArrayList<Marque> listeMarquesProduit = new ArrayList<Marque>();

		for (String nomMarque : elemStringMarque) {

			String cleanMarque = NettoyageString.nettoyerString(nomMarque);

			Marque marqueEnLecture = new Marque("");

			if (cleanMarque.equals("")) {
				marqueEnLecture = new Marque("ERREUR");
			} else {
				marqueEnLecture = new Marque(cleanMarque);
			}

			// String nomMarqueFromBDD = //
			daoGenerique.selectRowLike(marqueEnLecture).getNom_Row();

			if (memoireLocaleMarquesBDD
					.get(NettoyageString.nettoyerString(marqueEnLecture.getNomMarque().toLowerCase())) == null) {

				bufferRequetes = bufferRequetes + daoGenerique.insert(marqueEnLecture);
				this.memoireLocaleMarquesBDD.put(marqueEnLecture.getNomMarque(), marqueEnLecture);
				listeMarquesDuProduit.add(marqueEnLecture);
				this.compteurMarques++;
			}

		}
		return listeMarquesProduit;
	}

	private ArrayList<Ingredient> traitementIngredient(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringIngredient = morceauString.split(",");
		ArrayList<Ingredient> listeIngredientsProduit = new ArrayList<Ingredient>();

		for (String nomIngredient : elemStringIngredient) {

			String cleanIngredient = NettoyageString.nettoyerString(nomIngredient);

			Ingredient ingredientEnLecture = new Ingredient("");

			if (cleanIngredient.equals("")) {
				ingredientEnLecture = new Ingredient("ERREUR");
			} else {
				ingredientEnLecture = new Ingredient(cleanIngredient);
			}

			// String nomIngredientFromBDD = //
			daoGenerique.selectRowLike(ingredientEnLecture).getNom_Row();

			if (memoireLocaleIngredientsBDD.get(
					NettoyageString.nettoyerString(ingredientEnLecture.getLibelleIngredient().toLowerCase())) == null) {

				bufferRequetes = bufferRequetes + daoGenerique.insert(ingredientEnLecture);
				this.memoireLocaleIngredientsBDD.put(ingredientEnLecture.getLibelleIngredient(), ingredientEnLecture);
				listeIngredientsDuProduit.add(ingredientEnLecture);
				this.compteurIngredients++;
			}
		}
		return listeIngredientsProduit;
	}
}
