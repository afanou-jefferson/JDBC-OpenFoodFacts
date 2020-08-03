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
		this.memoireLocaleIngredientsBDD = daoGenerique.selectAllFromTable(Ingredient.class.getSimpleName());
		this.memoireLocaleMarquesBDD = daoGenerique.selectAllFromTable(Marque.class.getSimpleName());
		this.memoireLocaleAllergenesBDD = daoGenerique.selectAllFromTable(Allergene.class.getSimpleName());
		this.memoireLocaleCategoriesBDD = daoGenerique.selectAllFromTable(Categorie.class.getSimpleName());
		this.memoireLocaleProduitsBDD = daoGenerique.selectAllFromTable(Produit.class.getSimpleName());

		try {
			File file = fichier;
			List<String> lignes = FileUtils.readLines(file, "UTF-8");

			// Start 1 pour sauter ligne intitules categories
			for (int i = 1; i < lignes.size(); i++) {

				String[] morceaux = lignes.get(i).split("\\|", -1);

				String nomProduit = NettoyageString.NettoyageString.nettoyerString(morceaux[2]);
				String gradeNutriProduit = morceaux[3];
				int idCategorie = traitementCategorie(morceaux[0]);
				this.listeMarquesDuProduit = traitementMarque(morceaux[1]);
				this.listIDsIngredients = traitementIngredients(morceaux[4]);
				this.listIDsAllergenes = traitementAllergenes(morceaux[28]);

				Produit newProduit = new Produit(idCategorie, listIDsMarques, nomProduit, gradeNutriProduit,
						listIDsIngredients, listIDsAllergenes, listIDsAdditifs);

				String cleanNomProduit = NettoyageString.nettoyerString(nomProduit.toLowerCase());

				if (memoireLocaleProduitsBDD.get(cleanNomProduit) == null) {
					System.out.println("Produit buggé :" + nomProduit);
					int idNewProduit = daoProduit.insert(newProduit);
					memoireLocaleProduitsBDD.put(cleanNomProduit, idNewProduit);
					compteurProduits++;
				}

				System.out.println(i);

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private ArrayList<Integer> traitementAdditifs(String morceauString) {
		
		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringAdditif = morceauString.split(","); ArrayList<Integer>
		listIDAdditifsProduit = new ArrayList<Integer>();
		
		for (String nomAdditif : elemStringAdditif) {
		
		// Nettoyage spécial 
		String cleanAdditif = nomAdditif.replaceAll("[^\\w]\\s",
		" ").replaceAll("[\\+\\.\\^,%]", " ") .replaceAll("[\\_\\-]",
		" ").replace("fr:", " ").replace("en:", " ").trim();
		
		Additif additifEnLecture = new Additif("");
		
		if (cleanAdditif.equals("")) { additifEnLecture = new Additif("ERREUR"); }
		else { additifEnLecture = new Additif(cleanAdditif); }
		
		if (memoireLocaleAdditifsBDD.get(NettoyageString.nettoyerString(additifEnLecture.
		getLibelleAdditif())) == null) {
		
		int idAdditifBDD = daoGenerique.insert(additifEnLecture);
		this.memoireLocaleAdditifsBDD.put(additifEnLecture.nom_Additif,additifEnLecture);
		
		listIDAdditifsProduit.add(idAdditifBDD); this.compteurAdditifs++; } 
		} 
		return listIDAdditifsProduit; 
		}

	private ArrayList<Integer> traitementAllergenes(String morceauString) {
		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringAllergene = morceauString.split(",");
		ArrayList<Integer> listIDAllergenesProduit = new ArrayList<Integer>();

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

				int idAllergeneBDD = daoGenerique.insert(allergeneEnLecture);
				this.memoireLocaleAllergenesBDD.put(allergeneEnLecture.getLibelleAllergene(), idAllergeneBDD);

				listIDAllergenesProduit.add(idAllergeneBDD);
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

		if (memoireLocaleCategoriesBDD
				.get(NettoyageString.nettoyerString(categorieEnLecture.getNomCategorie())) == null) {
			idCategorieBDD = daoGenerique.insert(categorieEnLecture);
			this.memoireLocaleAdditifsBDD.put(categorieEnLecture.getNomCategorie(), idCategorieBDD);
			this.compteurCategorie++;
		} else {
			idCategorieBDD = memoireLocaleCategoriesBDD
					.get(NettoyageString.nettoyerString(categorieEnLecture.getNomCategorie()));
		}
		return idCategorieBDD;
	}

	private ArrayList<Integer> traitementMarque(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringMarque = morceauString.split(",");
		ArrayList<Integer> listIDMarquesProduit = new ArrayList<Integer>();

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

				int idMarqueBDD = daoGenerique.insert(marqueEnLecture);
				this.memoireLocaleMarquesBDD.put(marqueEnLecture.getNomMarque(), idMarqueBDD);
				listIDMarquesProduit.add(idMarqueBDD);
				this.compteurMarques++;
			}

		}
		return listIDMarquesProduit;
	}

	private ArrayList<Integer> traitementIngredients(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringIngredient = morceauString.split(",");
		ArrayList<Integer> listIDIngredientsProduit = new ArrayList<Integer>();

		for (String nomIngredient : elemStringIngredient) {

			String cleanIngredient = NettoyageString.NettoyageString.nettoyerString(nomIngredient);

			Ingredient ingredientEnLecture = new Ingredient(cleanIngredient);

			if (memoireLocaleIngredientsBDD.get(NettoyageString.NettoyageString
					.nettoyerString(ingredientEnLecture.getLibelleIngredient())) == null) {

				int idIngredientBDD = daoGenerique.insert(ingredientEnLecture);
				// System.out.println("Test");
				this.memoireLocaleIngredientsBDD.put(ingredientEnLecture.nom_Ingredient, idIngredientBDD);
				listIDIngredientsProduit.add(idIngredientBDD);
				this.compteurIngredients++;
			}
		}
		return listIDIngredientsProduit;
	}

}}
