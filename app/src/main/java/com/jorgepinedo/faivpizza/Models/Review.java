package com.jorgepinedo.faivpizza.Models;

import java.io.Serializable;


public class Review implements Serializable {

    private int id;

    private String title;

    private int pos_id;

    private int price;

    private int quantity;

    private float subtotal;

    private int status_id;

    public Review(int id, String title, int pos_id, int price, int quantity, float subtotal, int status_id) {
        this.id = id;
        this.title = title;
        this.pos_id = pos_id;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.status_id = status_id;
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

    public int getPos_id() {
        return pos_id;
    }

    public void setPos_id(int pos_id) {
        this.pos_id = pos_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pos_id=" + pos_id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", status_id=" + status_id +
                '}';
    }
}