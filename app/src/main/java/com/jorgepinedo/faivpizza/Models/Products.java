package com.jorgepinedo.faivpizza.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "products")
public class Products implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="status")
    private String status;

    @ColumnInfo(name="price")
    private float price;

    @ColumnInfo(name="url")
    private String url;

    @ColumnInfo(name="grams")
    private int grams;

    @ColumnInfo(name="category_id")
    private int category_id;

    @ColumnInfo(name="pos_id")
    private int pos_id;

    @ColumnInfo(name="type_id")
    private int type_id;

    @ColumnInfo(name="priority")
    private int priority;


    public Products(String title,float price, String url, int grams, int category_id, int pos_id, int type_id, int priority) {
        this.title = title;
        this.status = "ok";
        this.price = price;
        this.url = url;
        this.grams = grams;
        this.category_id = category_id;
        this.pos_id = pos_id;
        this.type_id = type_id;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPos_id() {
        return pos_id;
    }

    public void setPos_id(int pos_id) {
        this.pos_id = pos_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", url='" + url + '\'' +
                ", grams=" + grams +
                ", category_id=" + category_id +
                ", pos_id=" + pos_id +
                ", type_id=" + type_id +
                ", priority=" + priority +
                '}';
    }
}