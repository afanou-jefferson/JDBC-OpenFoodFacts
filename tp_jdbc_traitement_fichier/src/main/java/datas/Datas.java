package datas;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import utils.ArrayListToString;
import utils.CheckStringSimilaire;

public class Datas {

	ArrayList<Additif> listeAdditif = new ArrayList<Additif>();
	ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
	ArrayList<Ingredient> listeIngredient = new ArrayList<Ingredient>();
	ArrayList<Marque> listeMarque = new ArrayList<Marque>();
	ArrayList<Produit> listeProduit = new ArrayList<Produit>();
	ArrayList<Allergene> listeAllergene = new ArrayList<Allergene>();

	public Datas(File fichier) throws IllegalArgumentException, IllegalAccessException {

		try {
			// Lecture du fichier
			File file = fichier;
			List<String> lignes = FileUtils.readLines(file, "UTF-8");

			for (int i = 1; i < lignes.size(); i++) { // Start 1 pour sauter ligne intitules categories
				String[] morceaux = lignes.get(i).split("\\|", -1);

				Categorie newCat = traitementCategorie(morceaux[0]);
				ArrayList<Marque> currentProductMarques = traitementMarque(morceaux[1]);
				String nomProduit = morceaux[2];
				String gradeNutriProduit = morceaux[3];
				ArrayList<Ingredient> currentProductIngredients = traitementIngredients(morceaux[4]);
				ArrayList<Allergene> currentProductAllergenes = traitementAllergenes(morceaux[28]);
				ArrayList<Additif> currentProductAdditifs = traitementAdditifs(morceaux[29]);

				// Gestion donnees Nutritionnelles des indices [5] à [27]
				donneesNutritionnelles nutri = new donneesNutritionnelles();
				int compteurField = 5;
				for (Field field : nutri.getClass().getDeclaredFields()) {
					if (Datas.isANumber(morceaux[compteurField])) {
						field.set(nutri, Double.parseDouble(morceaux[compteurField]));
					} else {
						field.set(nutri, -1); // Val par défaut quand info non présente
					}
					compteurField++;
				}

				Produit currentProduit = new Produit(newCat, currentProductMarques, nomProduit, gradeNutriProduit,
						nutri, currentProductIngredients, currentProductAllergenes, currentProductAdditifs);

				// On part du principe qu'il n'y a que Produits uniques dans le fichier pour le
				// moment
				listeProduit.add(currentProduit);
				// System.out.println(currentProduit.toString());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private ArrayList<Additif> traitementAdditifs(String morceauString) {

		String[] elemStringAdditifss = morceauString.split(",");
		ArrayList<Additif> currentProductAdditifs = new ArrayList();

		for (String nomAdd : elemStringAdditifss) {
			String cleanAdditif = nomAdd.replaceAll("[^\\w]\\s", "").replaceAll("[\\+\\.\\^,*%]", "")
					.replaceAll("[\\_\\-]", " ").replace("fr:", "").replace("en:", "").trim();

			if (!CheckStringSimilaire.similaireDejaExistant(cleanAdditif, currentProductAdditifs, 5)) {
				Additif newAdditifs = new Additif(cleanAdditif);
				currentProductAdditifs.add(newAdditifs);

				if (CheckStringSimilaire.similaireDejaExistant(cleanAdditif, this.listeAdditif, 5)) {
					this.listeAdditif.add(newAdditifs);
				}
			}
		}
		return currentProductAdditifs;
	}

	private ArrayList<Allergene> traitementAllergenes(String morceauString) {
		String[] elemStringAllergenes = morceauString.split(",");
		ArrayList<Allergene> currentProductAllergenes = new ArrayList<Allergene>();

		for (String nomAll : elemStringAllergenes) {
			String cleanAllergene = nomAll.replaceAll("[^\\w]\\s", "").replaceAll("[\\+\\.\\^,*%]", "")
					.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", "").replace("en:", "").trim();

			if (CheckStringSimilaire.similaireDejaExistant(cleanAllergene, currentProductAllergenes, 5)) {
				Allergene newAllergene = new Allergene(cleanAllergene);
				currentProductAllergenes.add(newAllergene);

				if (CheckStringSimilaire.similaireDejaExistant(cleanAllergene, listeAllergene, 5)) {
					this.listeAllergene.add(newAllergene);
				}
			}
		}
		return currentProductAllergenes;
	}

	private Categorie traitementCategorie(String morceauString) {
		String cleanCategorie = morceauString.replaceAll("[^\\w]\\s", "").replaceAll("[\\+\\.\\^,*%]", "")
				.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", "").replace("en:", "").trim();
		Categorie newCat = new Categorie(cleanCategorie);

		boolean categorieListed = false;

		if (CheckStringSimilaire.similaireDejaExistant(cleanCategorie, this.listeCategorie, 5)) {
			categorieListed = true;
		}
		if (!categorieListed) {
			this.listeCategorie.add(newCat);
		}
		return newCat;
	}

	private ArrayList<Marque> traitementMarque(String morceauString) {
		String[] elemStringMarque = morceauString.split(",");
		ArrayList<Marque> currentProductMarques = new ArrayList<Marque>(); // List en Entite pour la recherche
																			// mais on place des marques ( impl
																			// Entite ) dedans
		for (String nomMarque : elemStringMarque) {
			String cleanNomMarque = nomMarque.replaceAll("[^\\w]\\s", "").replaceAll("[\\+\\.\\^,*%]", "")
					.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", "").replace("en:", "").trim();

			if (!CheckStringSimilaire.similaireDejaExistant(cleanNomMarque, currentProductMarques, 5)) {
				Marque newMarque = new Marque(cleanNomMarque);
				currentProductMarques.add(newMarque);

				// Need au moins 5 differents pour qu'il soit considéré comme "nouveau"
				boolean marqueListedDatas = CheckStringSimilaire.similaireDejaExistant(cleanNomMarque, this.listeMarque,
						5);

				if (!marqueListedDatas) {
					this.listeMarque.add(newMarque);
				}
			}
		}
		return currentProductMarques;
	}

	private ArrayList<Ingredient> traitementIngredients(String morceauString) {
		String[] elemStringIngredient = morceauString.split(",");
		ArrayList<Ingredient> currentProductIngredients = new ArrayList<Ingredient>();

		for (String nomIng : elemStringIngredient) {

			String cleanIngredient = nomIng.replaceAll("[^\\w]\\s", "").replaceAll("[\\+\\.\\^,*%]", "")
					.replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ").replace("fr:", "").replace("en:", "").trim();

			if (CheckStringSimilaire.similaireDejaExistant(cleanIngredient, currentProductIngredients, 5)) {
				Ingredient newIngredient = new Ingredient(cleanIngredient);
				currentProductIngredients.add(newIngredient);

				if (CheckStringSimilaire.similaireDejaExistant(cleanIngredient, listeIngredient, 5)) {
					this.listeIngredient.add(newIngredient);
				}
			}
		}
		return currentProductIngredients;
	}

	public static boolean isANumber(String chaine) {
		try {
			Integer.parseInt(chaine);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public void printListeAllergenes() {
		for (Allergene all : this.listeAllergene) {
			System.out.println(all.getLibelleAllergene());
		}
	}

	public void printListeAdditifs() {
		for (Additif add : this.listeAdditif) {
			System.out.println(add.getLibelleAdditif());
		}
	}

	public void printListeIngredients() {
		for (Ingredient ing : this.listeIngredient) {
			System.out.println(ing.getLibelleIngredient());
		}
	}

	public void printListeCategories() {
		for (Categorie cat : this.listeCategorie) {
			System.out.println(cat.getLibelleCategorie());
		}
	}

	public void printListeMarques() {
		for (Marque marque : this.listeMarque) {
			System.out.println(marque.getNomMarque());
		}
	}

	public ArrayList<Additif> getListeAdditif() {
		return listeAdditif;
	}

	public ArrayList<Categorie> getListeCategorie() {
		return listeCategorie;
	}

	public ArrayList<Ingredient> getListeIngredient() {
		return listeIngredient;
	}

	public ArrayList<Marque> getListeMarque() {
		return listeMarque;
	}

	public ArrayList<Produit> getListeProduit() {
		return listeProduit;
	}

	public ArrayList<Allergene> getListeAllergene() {
		return listeAllergene;
	}

}
