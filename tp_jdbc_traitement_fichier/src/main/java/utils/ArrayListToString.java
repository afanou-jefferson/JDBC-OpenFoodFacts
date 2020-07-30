package utils;

import java.util.ArrayList;
import java.util.stream.Collectors;

import datas.InfoProduit;

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
	
	public static ArrayList<String> arrayEntiteToString(ArrayList<InfoProduit> list){
		
		ArrayList<String> retour = new ArrayList<String>();
		
		for ( InfoProduit info : list) {
			retour.add(info.getValeurIdentifiant());
		}
		
		return retour;
		
	}

}
