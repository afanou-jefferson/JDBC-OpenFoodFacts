package jdbc;

import java.sql.Connection;

import datas.Additif;

public interface IAdditifDao {

	//List<Produit> select();
		int insert(Additif additif);
		//int update(String ancienNom, String nouveauNom);
		//int delete(Additif Additif);
}
