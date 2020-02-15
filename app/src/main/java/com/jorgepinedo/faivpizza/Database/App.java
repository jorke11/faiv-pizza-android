package com.jorgepinedo.faivpizza.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jorgepinedo.faivpizza.Dao.CategoriesDAO;
import com.jorgepinedo.faivpizza.Dao.OrdersDAO;
import com.jorgepinedo.faivpizza.Dao.OrdersDetailDAO;
import com.jorgepinedo.faivpizza.Dao.ProductsDAO;
import com.jorgepinedo.faivpizza.Models.Categories;
import com.jorgepinedo.faivpizza.Models.Orders;
import com.jorgepinedo.faivpizza.Models.OrdersDetail;
import com.jorgepinedo.faivpizza.Models.Products;

@Database(entities = {
        Categories.class,
        Products.class,
        Orders.class,
        OrdersDetail.class
},version = 1)
public abstract class App extends RoomDatabase {
    public abstract CategoriesDAO categoriesDAO();
    public abstract ProductsDAO productsDAO();
    public abstract OrdersDAO ordersDAO();
    public abstract OrdersDetailDAO ordersDetailDAO();
}