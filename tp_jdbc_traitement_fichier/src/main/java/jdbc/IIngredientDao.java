package jdbc;

import java.sql.Connection;

import datas.Ingredient;

public interface IIngredientDao {

	//List<Produit> select();
		int insert(Ingredient ingredient);
		//int update(String ancienNom, String nouveauNom);
		//int delete(Ingredient Ingredient);
}
