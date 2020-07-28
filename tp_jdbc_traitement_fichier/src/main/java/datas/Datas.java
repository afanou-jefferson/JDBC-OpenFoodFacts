package datas;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

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

			// Decoupage de chaque ligne pour obtenir les valeurs de chaque attributs
			for (int i = 1; i < lignes.size(); i++) { // Start 1 pour sauter ligne intitules categories
				String[] morceaux = lignes.get(i).split("\\|", -1); // Exception trouvée on stackOverflow pour "|"
				System.out.println("Lignes n°" + i);

				// Gestion Catégories
				String categorieProduit = morceaux[0];
				String cleanCategorie = categorieProduit.toLowerCase().replaceAll("[^\\w]\\s", "")
						.replaceAll("[\\+\\.\\^,*%]", "").replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ")
						.replace("fr:", "").replace("en:", "").trim();
				Categorie newCat = new Categorie(cleanCategorie);

				boolean categorieListed = false;
				for (Categorie cat : this.listeCategorie) {
					if (cat.getLibelleCategorie().equals(newCat.getLibelleCategorie())) {
						categorieListed = true;
					}
				}
				if (!categorieListed) {
					this.listeCategorie.add(newCat);
				}

				// Gestion Marque
				String marqueProduit = morceaux[1];
				String[] elemStringMarque = marqueProduit.split(",");
				ArrayList<Marque> currentProductMarques = new ArrayList<Marque>();
				for (String nomMarque : elemStringMarque) {
					String cleanNomMarque = nomMarque.toLowerCase().replaceAll("[^\\w]\\s", "")
							.replaceAll("[\\+\\.\\^,*%]", "").replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ")
							.replace("fr:", "").replace("en:", "").trim();
					Marque newMarque = new Marque(cleanNomMarque);
					currentProductMarques.add(newMarque);
					boolean marqueListed = false;
					for (Marque marque : this.listeMarque) {
						if (marque.getNomMarque().equals(newMarque.getNomMarque())) {
							marqueListed = true;
						}
					}
					if (!marqueListed) {
						this.listeMarque.add(newMarque);
					}
				}

				String nomProduit = morceaux[2];

				String gradeNutriProduit = morceaux[3];

				// Gestion Ingredients
				String listeIngredientsProduit = morceaux[4]; // Ingredient commence par [ et séparés par une virgule
				String[] elemStringIngredient = listeIngredientsProduit.split(",");
				ArrayList<Ingredient> currentProductIngredients = new ArrayList<Ingredient>();
				for (String nomIng : elemStringIngredient) {
					String cleanIngredient = nomIng.toLowerCase().replaceAll("[^\\w]\\s", "")
							.replaceAll("[\\+\\.\\^,*%]", "").replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ")
							.replace("fr:", "").replace("en:", "").trim();
					Ingredient newIngredient = new Ingredient(cleanIngredient);
					currentProductIngredients.add(newIngredient);

					boolean ingredientListed = false;
					for (Ingredient ing : listeIngredient) {
						if (ing.getLibelleIngredient().equals(newIngredient.getLibelleIngredient())) {
							ingredientListed = true;
						}
					}

					if (!ingredientListed) {
						this.listeIngredient.add(newIngredient);
					}
				}

				// Gestion données Nutri
				donneesNutritionnelles nutri = new donneesNutritionnelles();
				int compteurField = 5;
				for (Field field : nutri.getClass().getDeclaredFields()) {
					/*
					 * if ( morceaux[compteurField].length() > 0 && morceaux[compteurField].length()
					 * < 10 && morceaux[compteurField].trim().length() > 0) {
					 */
					if (Datas.isANumber(morceaux[compteurField])) {
						field.set(nutri, Double.parseDouble(morceaux[compteurField]));
					} else {
						field.set(nutri, -1); // Val par défaut quand info non présente
					}
					compteurField++;
					;
				}

				String listeAllergenes = morceaux[28];
				String[] elemStringAllergenes = listeAllergenes.split(",");
				ArrayList<Allergene> currentProductAllergenes = new ArrayList<Allergene>();
				for (String nomAll : elemStringAllergenes) {
					String cleanAllergene = nomAll.toLowerCase().replaceAll("[^\\w]\\s", "")
							.replaceAll("[\\+\\.\\^,*%]", "").replaceAll("[0-9]", "").replaceAll("[\\_\\-]", " ")
							.replace("fr:", "").replace("en:", "").trim();
					Allergene newAllergene = new Allergene(cleanAllergene);
					currentProductAllergenes.add(newAllergene);

					boolean allergeneListed = false;
					for (Allergene all : listeAllergene) {
						if (all.getLibelleAllergene().equals(newAllergene.getLibelleAllergene())) {
							allergeneListed = true;
						}
					}

					if (!allergeneListed) {
						this.listeAllergene.add(newAllergene);
					}
				}

				String listeAdditifs = morceaux[29];
				String[] elemStringAdditifss = listeAdditifs.split(",");
				ArrayList<Additif> currentProductAdditifs = new ArrayList();
				for (String nomAdd : elemStringAdditifss) {
					String cleanAdditif = nomAdd.toLowerCase().replaceAll("[^\\w]\\s", "")
							.replaceAll("[\\+\\.\\^,*%]", "").replaceAll("[\\_\\-]", " ").replace("fr:", "")
							.replace("en:", "").trim();
					Additif newAdditifs = new Additif(cleanAdditif);
					currentProductAdditifs.add(newAdditifs);

					boolean AdditifsListed = false;
					for (Additif add : listeAdditif) {
						if (add.getLibelleAdditif().equals(newAdditifs.getLibelleAdditif())) {
							AdditifsListed = true;
						}
					}

					if (!AdditifsListed) {
						this.listeAdditif.add(newAdditifs);
					}
				}

				Produit currentProduit = new Produit(newCat, currentProductMarques, nomProduit, gradeNutriProduit,
						nutri, currentProductIngredients, currentProductAllergenes, currentProductAdditifs);
				listeProduit.add(currentProduit);

				System.out.println(currentProduit.toString());

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

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
}
