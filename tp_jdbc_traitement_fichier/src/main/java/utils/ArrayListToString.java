package utils;

import java.util.ArrayList;
import java.util.stream.Collectors;

import datas.Entite;

public class ArrayListToString {
	
	/**
	 * Methode static visant à retourner le contenu d'une ArrayList placée en paramètre en format String.
	 * @param Arraylist generique
	 * @return String
	 */
	public static String getStringArrayList(ArrayList<?> list) {
		
		String retour = "[";
		
		String listString = list.stream().map(Object::toString).collect(Collectors.joining(", "));
		
		retour = listString + "]";
		
		return retour;	
	}
	
	public static ArrayList<String> arrayEntiteToString(ArrayList<Entite> list){
		
		ArrayList<String> retour = new ArrayList<String>();
		
		for ( Entite ent : list) {
			retour.add(ent.getEntite());
		}
		
		return retour;
		
	}

}
