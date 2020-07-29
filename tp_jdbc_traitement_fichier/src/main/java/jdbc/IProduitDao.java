package jdbc;

import java.sql.Connection;
import java.util.List;

import datas.Produit;

public interface IProduitDao {
	
	//List<Produit> select();
	void insert(Produit produit);
	//int update(String ancienNom, String nouveauNom);
	//int delete(Article article);

}
