package com.example.ag_notes.Model;

import java.util.Date;

public class Notes {

    private Integer id;
    private String title;
    private String description;
    private Date date;
    private Integer image;


    public Notes() {

    }

    public Notes(Integer id, String title, String desc, Date date, Integer img) {
        this.id = id;
        this.title = title;
        this.description = desc;
        this.date = date;
        this.image = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
