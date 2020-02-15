package com.jorgepinedo.faivpizza.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jorgepinedo.faivpizza.Models.Orders;

import java.util.List;

@Dao
public interface OrdersDAO {

    @Query("SELECT * from orders")
    List<Orders> getAllOrders();

    @Query("SELECT * from orders WHERE status_id=1 and id= :id")
    Orders getOrder(int id);

    @Query("SELECT * from orders WHERE status_id=1")
    Orders getOrderCurrent();

    @Query("SELECT * from orders Order By id desc limit 1")
    Orders getLastOrder();

    @Insert
    void insert(Orders... orders);

    @Update
    void update(Orders... orders);

    @Query("DELETE FROM orders")
    void deleteAll();
}
