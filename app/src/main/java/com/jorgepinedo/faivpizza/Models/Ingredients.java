package com.jorgepinedo.faivpizza.Models;

import java.io.Serializable;


public class Ingredients implements Serializable {

    private int id;
    private String title;
    private String url;
    private int category_id;
    private int priority;

    public Ingredients(String title, String url, int category_id, int priority) {
        this.title = title;
        this.url = url;
        this.category_id = category_id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", category_id=" + category_id +
                ", priority=" + priority +
                '}';
    }
}