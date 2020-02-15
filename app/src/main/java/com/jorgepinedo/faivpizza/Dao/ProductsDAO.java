package com.jorgepinedo.faivpizza.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jorgepinedo.faivpizza.Models.Products;

import java.util.List;

@Dao
public interface ProductsDAO {

    @Query("SELECT * from products")
    List<Products> getAllProducts();

    @Query("SELECT * from products where category_id IN(:category_id)")
    List<Products> getAllProductsCategory(int[] category_id);

    @Query("SELECT * from products WHERE id= :id")
    Products getProductById(int id);

    @Query("SELECT * from products WHERE url= :url")
    Products getProductByUrl(String url);

    @Query("SELECT * from products WHERE pos_id= :pos_id and category_id<>7")
    Products getProductByPosId(int pos_id);

    @Query("SELECT * from products WHERE pos_id= :pos_id and category_id in (:category_id)")
    Products getProductByPos(int pos_id,int[] category_id);

    @Insert
    void insert(Products... products);

    @Update
    void update(Products... products);

    @Query("DELETE FROM products")
    void delete();
}
