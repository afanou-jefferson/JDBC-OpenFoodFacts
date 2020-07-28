package utils;

import java.util.ArrayList;

import org.apache.commons.text.similarity.LevenshteinDistance;

import datas.Entite;
import datas.Marque;

public class CheckStringSimilaire {

	/**
	 * Pour calculer le nb de différence entre 2 strings
	 * 
	 * @param s1
	 * @param s2
	 * @return le nombre de char différents entre s1 et s2
	 */
	public static int getNbCharDifferents(String s1, String s2) {
		LevenshteinDistance nbDiff = new LevenshteinDistance();
		return nbDiff.apply(s1, s2);

	}

	/**
	 * Savoir si 2 string sont à considérer comme identique/similaire
	 * 
	 * @param s1
	 * @param s2
	 * @param seuilMinDifferences
	 * @return True si moins de diff que le seuil renseigné en paramètre
	 */
	public static boolean stringSimilaires(String s1, String s2, int seuilMinDifferences) {
		boolean bool = false;

		if (getNbCharDifferents(s1, s2) <= seuilMinDifferences) {
			bool = true;
		}

		return bool;
	}

	/**
	 * Savoir si la String placé en param1 est inclue dans le liste d'Entite placée
	 * en param2
	 * 
	 * @param paramString
	 * @param list
	 * @param seuilMinDifferences
	 * @return True si String contenu dans la liste
	 */
	public static boolean similaireDejaExistant(String paramString, ArrayList<?> list, int seuilMinDifferences) {
		boolean bool = false;

		for (int i = 0; i < list.size(); i++) {
			Entite tempo = (Entite) list.get(i);
			if (stringSimilaires(paramString, tempo.getEntite(), seuilMinDifferences)) {
				bool = true;
			}
		}

		return bool;
	}

}
