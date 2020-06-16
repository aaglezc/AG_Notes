package com.example.ag_notes.Model;

public class Nota {

    private Integer id;
    private String title;
    private String description;
    private String date;
    private byte[] image;
    private String category;
    private String audio;


    public Nota() {

    }

    public Nota(Integer id, String title, String desc, String date, byte[] img, String category, String audio) {
        this.id = id;
        this.title = title;
        this.description = desc;
        this.date = date;
        this.image = img;
        this.category = category;
        this.audio = audio;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
