package structure_datas;

import java.util.ArrayList;

public class Allergene extends Table{
	
	public int id_Allergene;
	public String nom_Allergene;

	public Allergene(int id_Allergene, String nom_Allergene) {
		super();
		this.id_Allergene = id_Allergene;
		this.nom_Allergene = nom_Allergene;
	}
	
	@Override
	public String getNomUnique() {
		// TODO Auto-generated method stub
		return this.nom_Allergene;
	}
	
	@Override
	public ArrayList<String> getValeurAttributsTable() {
		ArrayList<String> listeValeurs = new ArrayList<String>();
		
		listeValeurs.add(Integer.toString(this.id_Allergene));
		listeValeurs.add(this.nom_Allergene);
		
		return listeValeurs;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id_Allergene;
	}
	
	
}
