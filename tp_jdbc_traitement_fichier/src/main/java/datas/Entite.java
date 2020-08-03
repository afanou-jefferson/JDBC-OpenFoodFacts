package datas;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Interface pour implémenter une fonction getEntite() facilitant le code et accélérant les traitements
 * @author Exost
 *
 */
public abstract class Entite {
	
	String nomClasse = getNomModel();
	
	public String getNomModel() {
		String nomModel = this.getClass().getSimpleName();	
		return nomModel;
	}
	
	public int getNbAttributsModel() {
		return this.getClass().getFields().length;
	}
	
	public ArrayList<String> getNomAttributsModel() {
		ArrayList<String> tabNomAttributs = new ArrayList<String>();
		for ( Field field : this.getClass().getFields() ) {
			tabNomAttributs.add(field.getName());
		}
		return tabNomAttributs;
	}
	
	public abstract ArrayList<String> getValeurAttributsModel();
	
	public abstract String getNomUnique();
	

}
