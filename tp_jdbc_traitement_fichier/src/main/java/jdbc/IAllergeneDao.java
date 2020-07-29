package jdbc;

import java.sql.Connection;

import datas.Allergene;

public interface IAllergeneDao {

	//List<Produit> select();
	int insert(Allergene allergene);
	//int update(String ancienNom, String nouveauNom);
	//int delete(Allergene allergene);
	
}
