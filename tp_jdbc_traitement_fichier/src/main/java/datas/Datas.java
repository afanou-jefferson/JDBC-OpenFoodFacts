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

public class Datas {

	Connection connection;
	int idCategorieProduit;

	ArrayList<Integer> listIDsAdditifs = new ArrayList<Integer>();
	ArrayList<Integer> listIDsIngredients = new ArrayList<Integer>();
	ArrayList<Integer> listIDsMarques = new ArrayList<Integer>();
	ArrayList<Integer> listIDsAllergenes = new ArrayList<Integer>();

	HashMap<String, Integer> memoireLocaleAdditifsBDD;
	HashMap<String, Integer> memoireLocaleIngredientsBDD;
	HashMap<String, Integer> memoireLocaleMarquesBDD;
	HashMap<String, Integer> memoireLocaleAllergenesBDD;
	HashMap<String, Integer> memoireLocaleCategoriesBDD;
	HashMap<String, Integer> memoireLocaleProduitsBDD;
	
	public int compteurCategorie = 1;
	public int compteurAdditifs = 1;
	public int compteurIngredients = 1;
	public int compteurMarques = 1;
	public int compteurAllergenes = 1;
	public int compteurProduits = 1;


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
			
			//System.out.println("Test in" + this.memoireLocaleIngredientsBDD.toString());

			// Start 1 pour sauter ligne intitules categories
			for (int i = 1; i < lignes.size(); i++) {
				String[] morceaux = lignes.get(i).split("\\|", -1);

				String nomProduit = nettoyerString(morceaux[2]);
				String gradeNutriProduit = morceaux[3];
				int idCategorie = traitementCategorie(morceaux[0]);
				this.listIDsMarques = traitementMarque(morceaux[1]);
				this.listIDsIngredients = traitementIngredients(morceaux[4]);
				this.listIDsAllergenes = traitementAllergenes(morceaux[28]);
				this.listIDsAdditifs = traitementAdditifs(morceaux[29]);

				/*
				// Gestion donnees Nutritionnelles des indices [5] à [27]
				DonneesNutritionnelles nutri = new DonneesNutritionnelles();
				int compteurField = 5;
				for (Field field : nutri.getClass().getDeclaredFields()) {
					if (Datas.isANumber(morceaux[compteurField])) {
						field.set(nutri, Double.parseDouble(morceaux[compteurField]));
					} else {
						field.set(nutri, -1); // Val par défaut quand info non présente
					}
					compteurField++;
				} */

				Categorie categorieProduit = new Categorie(daoGenerique.getNomFromID(idCategorie, new Categorie()));
				Produit newProduit = new Produit(categorieProduit, listIDsMarques, nomProduit, gradeNutriProduit,
						listIDsIngredients, listIDsAllergenes, listIDsAdditifs);

				
				if ( memoireLocaleProduitsBDD.get(nettoyerString(nomProduit.toLowerCase())) == null ) {
					System.out.println("Produit buggé :" + nomProduit);
					int idNewProduit = daoProduit.insert(newProduit);
					memoireLocaleProduitsBDD.put(nomProduit.toLowerCase(), idNewProduit);
					compteurProduits++;
				}


				//System.out.println(i);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private ArrayList<Integer> traitementAdditifs(String morceauString) {
		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringAdditif = morceauString.split(",");
		ArrayList<Integer> listIDAdditifsProduit = new ArrayList<Integer>();

		for (String nomAdditif : elemStringAdditif) {

			// Nettoyage spécial
			String cleanAdditif = nomAdditif.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,*%]", " ")
					.replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").trim();

			Additif additifEnLecture = new Additif("");

			if (cleanAdditif.equals("")) {
				additifEnLecture = new Additif("ERREUR");
			} else {
				additifEnLecture = new Additif(cleanAdditif);
			}

			if (memoireLocaleAdditifsBDD.get(nettoyerString(additifEnLecture.getLibelleAdditif())) == null) {

				int idAdditifBDD = daoGenerique.insert(additifEnLecture);
				this.memoireLocaleAdditifsBDD.put(additifEnLecture.nom_Additif, idAdditifBDD);

				listIDAdditifsProduit.add(idAdditifBDD);
				this.compteurAdditifs++;
			}
		}
		return listIDAdditifsProduit;
	}

	private ArrayList<Integer> traitementAllergenes(String morceauString) {
		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringAllergene = morceauString.split(",");
		ArrayList<Integer> listIDAllergenesProduit = new ArrayList<Integer>();

		for (String nomAllergene : elemStringAllergene) {

			String cleanAllergene = nettoyerString(nomAllergene);

			Allergene allergeneEnLecture = new Allergene("");

			if (cleanAllergene.equals("")) {
				allergeneEnLecture = new Allergene("ERREUR");
			} else {
				allergeneEnLecture = new Allergene(cleanAllergene);
			}

			if (memoireLocaleAllergenesBDD.get(nettoyerString(allergeneEnLecture.getLibelleAllergene())) == null) {

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

		String cleanCategorie = nettoyerString(morceauString);

		Categorie categorieEnLecture = new Categorie("");

		if (cleanCategorie.equals("")) {
			categorieEnLecture = new Categorie("ERREUR");
		} else {
			categorieEnLecture = new Categorie(cleanCategorie);
		}

		int idCategorieBDD = 0;

		if (memoireLocaleCategoriesBDD.get(nettoyerString(categorieEnLecture.getNomCategorie())) == null) {
			idCategorieBDD = daoGenerique.insert(categorieEnLecture);
			this.memoireLocaleAdditifsBDD.put(categorieEnLecture.getNomCategorie(), idCategorieBDD);
			this.compteurCategorie++;
		} else {
			idCategorieBDD = memoireLocaleCategoriesBDD.get(nettoyerString(categorieEnLecture.getNomCategorie()));
		}
		return idCategorieBDD;
	}

	private ArrayList<Integer> traitementMarque(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringMarque = morceauString.split(",");
		ArrayList<Integer> listIDMarquesProduit = new ArrayList<Integer>();

		for (String nomMarque : elemStringMarque) {

			String cleanMarque = nettoyerString(nomMarque);

			Marque marqueEnLecture = new Marque("");

			if (cleanMarque.equals("")) {
				marqueEnLecture = new Marque("ERREUR");
			} else {
				marqueEnLecture = new Marque(cleanMarque);
			}

			// String nomMarqueFromBDD =
			// daoGenerique.selectRowLike(marqueEnLecture).getNom_Row();

			if (memoireLocaleMarquesBDD.get(nettoyerString(marqueEnLecture.getNomMarque().toLowerCase())) == null) {

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

			String cleanIngredient = nettoyerString(nomIngredient);

			/*Ingredient ingredientEnLecture = new Ingredient("");

			if (cleanIngredient.equals("")) {
				ingredientEnLecture = new Ingredient("ERREUR");
			} else {
				ingredientEnLecture = new Ingredient(cleanIngredient);
			}*/
		
			Ingredient ingredientEnLecture = new Ingredient(cleanIngredient);

			// String nomIngredientFromBDD =
			// daoGenerique.selectRowLike(ingredientEnLecture).getNom_Row();

			if (memoireLocaleIngredientsBDD.get(nettoyerString(ingredientEnLecture.getLibelleIngredient())) == null) {
				
				//System.out.println("Ingrédient buggé : " + ingredientEnLecture.getLibelleIngredient());

				int idIngredientBDD = daoGenerique.insert(ingredientEnLecture);
				//System.out.println("Test");
				this.memoireLocaleIngredientsBDD.put(ingredientEnLecture.nom_Ingredient, idIngredientBDD);
				listIDIngredientsProduit.add(idIngredientBDD);
				this.compteurIngredients++;
			}
		}
		return listIDIngredientsProduit;
	}

	public static boolean isANumber(String chaine) {
		try {
			Integer.parseInt(chaine);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	
	public static String removeAccents(String value)
	{
		HashMap<Character, Character> MAP_NORM = null;
	    if (MAP_NORM == null || MAP_NORM.size() == 0)
	    {
	        MAP_NORM = new HashMap<Character, Character>();
	        MAP_NORM.put('À', 'A');
	        MAP_NORM.put('Á', 'A');
	        MAP_NORM.put('Â', 'A');
	        MAP_NORM.put('Ã', 'A');
	        MAP_NORM.put('Ä', 'A');
	        MAP_NORM.put('È', 'E');
	        MAP_NORM.put('É', 'E');
	        MAP_NORM.put('Ê', 'E');
	        MAP_NORM.put('Ë', 'E');
	        MAP_NORM.put('Í', 'I');
	        MAP_NORM.put('Ì', 'I');
	        MAP_NORM.put('Î', 'I');
	        MAP_NORM.put('Ï', 'I');
	        MAP_NORM.put('Ù', 'U');
	        MAP_NORM.put('Ú', 'U');
	        MAP_NORM.put('Û', 'U');
	        MAP_NORM.put('Ü', 'U');
	        MAP_NORM.put('Ò', 'O');
	        MAP_NORM.put('Ó', 'O');
	        MAP_NORM.put('Ô', 'O');
	        MAP_NORM.put('Õ', 'O');
	        MAP_NORM.put('Ö', 'O');
	        MAP_NORM.put('Ñ', 'N');
	        MAP_NORM.put('Ç', 'C');
	        MAP_NORM.put('ª', 'A');
	        MAP_NORM.put('º', 'O');
	        MAP_NORM.put('§', 'S');
	        MAP_NORM.put('³', '3');
	        MAP_NORM.put('²', '2');
	        MAP_NORM.put('¹', '1');
	        MAP_NORM.put('à', 'a');
	        MAP_NORM.put('á', 'a');
	        MAP_NORM.put('â', 'a');
	        MAP_NORM.put('ã', 'a');
	        MAP_NORM.put('ä', 'a');
	        MAP_NORM.put('è', 'e');
	        MAP_NORM.put('é', 'e');
	        MAP_NORM.put('ê', 'e');
	        MAP_NORM.put('ë', 'e');
	        MAP_NORM.put('í', 'i');
	        MAP_NORM.put('ì', 'i');
	        MAP_NORM.put('î', 'i');
	        MAP_NORM.put('ï', 'i');
	        MAP_NORM.put('ù', 'u');
	        MAP_NORM.put('ú', 'u');
	        MAP_NORM.put('û', 'u');
	        MAP_NORM.put('ü', 'u');
	        MAP_NORM.put('ò', 'o');
	        MAP_NORM.put('ó', 'o');
	        MAP_NORM.put('ô', 'o');
	        MAP_NORM.put('õ', 'o');
	        MAP_NORM.put('ö', 'o');
	        MAP_NORM.put('ñ', 'n');
	        MAP_NORM.put('ç', 'c');
	    }

	    if (value == null) {
	        return "";
	    }

	    StringBuilder sb = new StringBuilder(value);

	    for(int i = 0; i < value.length(); i++) {
	        Character c = MAP_NORM.get(sb.charAt(i));
	        if(c != null) {
	            sb.setCharAt(i, c.charValue());
	        }
	    }

	    return sb.toString();

	}

	public static String nettoyerString(String rawString) {
		
		String cleanString = Normalizer.normalize(rawString, Normalizer.Form.NFD);
		
		cleanString = rawString.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,*%]", " ")
				.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").toLowerCase().trim();
		cleanString = removeAccents(cleanString);
		return cleanString;
	}
}
