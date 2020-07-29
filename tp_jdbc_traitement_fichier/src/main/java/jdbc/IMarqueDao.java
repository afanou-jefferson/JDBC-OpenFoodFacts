package jdbc;

import java.sql.Connection;

import datas.Marque;

public interface IMarqueDao {

	//List<Produit> select();
		int insert(Marque marque);
		//int update(String ancienNom, String nouveauNom);
		//int delete(Marque Marque);
}
