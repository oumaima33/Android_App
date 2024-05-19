package com.example.taskmanag;

public class Note {

    String id;
    String title;
    String desc;
    String date;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String category;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public Note(String id, String title, String desc , String date,String category){
        this.id=id;
        this.title=title;
        this.desc=desc;
        this.date=date;
    }
}
