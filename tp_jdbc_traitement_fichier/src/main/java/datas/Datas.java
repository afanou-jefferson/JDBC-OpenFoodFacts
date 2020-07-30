package datas;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;

import org.apache.commons.io.FileUtils;

import jdbc.JDBCdaoGenerique;
import jdbc.JDBCdaoProduit;


public class Datas {

	Connection connection;

	ArrayList<Integer> listIDsAdditifs = new ArrayList<Integer>();
	ArrayList<Integer> listIDsIngredients = new ArrayList<Integer>();
	ArrayList<Integer> listIDsMarques= new ArrayList<Integer>();
	ArrayList<Integer> listIDsAllergenes = new ArrayList<Integer>();
	int idCategorieProduit;
	//ArrayList<Produit> listeProduits = new ArrayList<Produit>();

	public Datas(File fichier, Connection connection) throws IllegalArgumentException, IllegalAccessException {

		this.connection = connection;

		JDBCdaoProduit daoProduit = new JDBCdaoProduit(this.connection);
		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);

		try {
			File file = fichier;
			List<String> lignes = FileUtils.readLines(file, "UTF-8");

			for (int i = 1; i < lignes.size(); i++) { // Start 1 pour sauter ligne intitules categories
				String[] morceaux = lignes.get(i).split("\\|", -1);

				int idCategorie = traitementCategorie(morceaux[0]);
				this.listIDsMarques = traitementMarque(morceaux[1]);
				String nomProduit = morceaux[2];
				String gradeNutriProduit = morceaux[3];
				this.listIDsIngredients = traitementIngredients(morceaux[4]);
				this.listIDsAllergenes = traitementAllergenes(morceaux[28]);
				this.listIDsAdditifs = traitementAdditifs(morceaux[29]);

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
				}
				
				Categorie categorieProduit = new Categorie(daoGenerique.getNomFromID(idCategorie, new Categorie()));
				Produit newProduit = new Produit(categorieProduit, listIDsMarques, nomProduit, gradeNutriProduit, listIDsIngredients, listIDsAllergenes, listIDsAdditifs);

				daoProduit.insert(newProduit);

				System.out.println(i);

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
			
			String cleanAdditif = nomAdditif.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,*%]", " ")
					.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").trim();
			
			Additif additifEnLecture = new Additif("");
			
			if (cleanAdditif.equals("")) {
				additifEnLecture = new Additif("ERREUR");
			} else {
				additifEnLecture = new Additif(cleanAdditif);
			}

			String nomAdditifFromBDD = daoGenerique.selectRowLike(additifEnLecture);
			int idAdditifBDD = -1;

			if (nomAdditifFromBDD.equals("NON ENREGISTRE")) {
				idAdditifBDD = daoGenerique.insert(additifEnLecture);

			} else {

				idAdditifBDD = daoGenerique.insert(new Additif(nomAdditifFromBDD));
			}
			listIDAdditifsProduit.add(idAdditifBDD);
		}
			return listIDAdditifsProduit;
	}

	private ArrayList<Integer> traitementAllergenes(String morceauString) {
		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringAllergene = morceauString.split(",");
		ArrayList<Integer> listIDAllergenesProduit = new ArrayList<Integer>();

		for (String nomAllergene : elemStringAllergene) {
			
			String cleanAllergene = nomAllergene.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,*%]", " ")
					.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").trim();
			
			Allergene allergeneEnLecture = new Allergene("");
			
			if (cleanAllergene.equals("")) {
				allergeneEnLecture = new Allergene("ERREUR");
			} else {
				allergeneEnLecture = new Allergene(cleanAllergene);
			}

			String nomAllergeneFromBDD = daoGenerique.selectRowLike(allergeneEnLecture);
			int idAllergeneBDD = -1;

			if (nomAllergeneFromBDD.equals("NON ENREGISTRE")) {
				idAllergeneBDD = daoGenerique.insert(allergeneEnLecture);

			} else {

				idAllergeneBDD = daoGenerique.insert(new Allergene(nomAllergeneFromBDD));
			}
			listIDAllergenesProduit.add(idAllergeneBDD);
		}
			return listIDAllergenesProduit;
	}


	private int traitementCategorie(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);

		String cleanCategorie = morceauString.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,*%]", " ")
				.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").trim();

		Categorie categorieEnLecture = new Categorie("");

		if (cleanCategorie.equals("")) {
			categorieEnLecture = new Categorie("ERREUR");
		} else {
			categorieEnLecture = new Categorie(cleanCategorie);
		}

		String nomCategorieFromBDD = daoGenerique.selectRowLike(categorieEnLecture);
		int idCategorieBDD = -1;

		if (nomCategorieFromBDD.equals("NON ENREGISTRE")) {
			idCategorieBDD = daoGenerique.insert(categorieEnLecture);

		} else {
			idCategorieBDD = daoGenerique.insert(new Categorie(nomCategorieFromBDD));
		}

		return idCategorieBDD;
	}

	private ArrayList<Integer> traitementMarque(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringMarque = morceauString.split(",");
		ArrayList<Integer> listIDMarquesProduit = new ArrayList<Integer>();

		for (String nomMarque : elemStringMarque) {
			
			String cleanMarque = nomMarque.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,*%]", " ")
					.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").trim();
			
			Marque marqueEnLecture = new Marque("");
			
			if (cleanMarque.equals("")) {
				marqueEnLecture = new Marque("ERREUR");
			} else {
				marqueEnLecture = new Marque(cleanMarque);
			}

			String nomMarqueFromBDD = daoGenerique.selectRowLike(marqueEnLecture);
			int idMarqueBDD = -1;

			if (nomMarqueFromBDD.equals("NON ENREGISTRE")) {
				idMarqueBDD = daoGenerique.insert(marqueEnLecture);

			} else {

				idMarqueBDD = daoGenerique.insert(new Marque(nomMarqueFromBDD));
			}
			listIDMarquesProduit.add(idMarqueBDD);
		}
			return listIDMarquesProduit;
	}

	private ArrayList<Integer> traitementIngredients(String morceauString) {

		JDBCdaoGenerique daoGenerique = new JDBCdaoGenerique(this.connection);
		String[] elemStringIngredient = morceauString.split(",");
		ArrayList<Integer> listIDIngredientsProduit = new ArrayList<Integer>();

		for (String nomIngredient : elemStringIngredient) {
			
			String cleanIngredient = nomIngredient.replaceAll("[^\\w]\\s", " ").replaceAll("[\\+\\.\\^,*%]", " ")
					.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", " ").replace("en:", " ").trim();
			
			Ingredient ingredientEnLecture = new Ingredient("");
			
			if (cleanIngredient.equals("")) {
				ingredientEnLecture = new Ingredient("ERREUR");
			} else {
				ingredientEnLecture = new Ingredient(cleanIngredient);
			}

			String nomIngredientFromBDD = daoGenerique.selectRowLike(ingredientEnLecture);
			int idIngredientBDD = -1;

			if (nomIngredientFromBDD.equals("NON ENREGISTRE")) {
				idIngredientBDD = daoGenerique.insert(ingredientEnLecture);

			} else {

				idIngredientBDD = daoGenerique.insert(new Ingredient(nomIngredientFromBDD));
			}
			listIDIngredientsProduit.add(idIngredientBDD);
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

}
