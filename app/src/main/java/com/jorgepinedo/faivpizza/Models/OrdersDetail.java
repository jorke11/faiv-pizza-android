package com.jorgepinedo.faivpizza.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class OrdersDetail implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="order_id")
    private int order_id;

    @ColumnInfo(name="product_id")
    private int product_id;

    @ColumnInfo(name="quantity")
    private int quantity;

    @ColumnInfo(name="parent_id")
    private int parent_id;

    @ColumnInfo(name="status_id")
    private int status_id;

    public OrdersDetail(int order_id, int product_id, int parent_id) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = 1;
        this.parent_id = parent_id;
        this.status_id = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }


    @Override
    public String toString() {
        return "OrdersDetail{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                ", parent_id=" + parent_id +
                ", status_id=" + status_id +
                '}';
    }
}