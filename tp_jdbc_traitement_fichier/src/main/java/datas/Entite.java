package datas;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Interface pour implémenter une fonction getEntite() facilitant le code et accélérant les traitements
 * @author Exost
 *
 */
public abstract class Entite {
	
	private int idEnBDD;
	private String nomClasse = getNomModel();
	
	
	public String getNomClasse() {
		return nomClasse;
	}


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
	
	public void setNomClasse(String nomClasse) {
		this.nomClasse = nomClasse;
	}

	public int getIdEnBDD() {
		return idEnBDD;
	}

	public void setIdEnBDD(int idEnBDD) {
		this.idEnBDD = idEnBDD;
	}
}
