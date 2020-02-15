package com.jorgepinedo.faivpizza.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Orders implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="status_id")
    private int status_id;

    @ColumnInfo(name="service")
    private float service;

    @ColumnInfo(name="order_post_id")
    private int order_post_id;

    @ColumnInfo(name="type_id")
    private int type_id;

    public Orders() {
        this.status_id = 1;
        this.service = 0;
        this.order_post_id = 0;
        this.type_id= 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public float getService() {
        return service;
    }

    public void setService(float service) {
        this.service = service;
    }

    public int getOrder_post_id() {
        return order_post_id;
    }

    public void setOrder_post_id(int order_post_id) {
        this.order_post_id = order_post_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", status_id=" + status_id +
                ", service=" + service +
                ", order_post_id=" + order_post_id +
                ", type_id=" + type_id +
                '}';
    }
}