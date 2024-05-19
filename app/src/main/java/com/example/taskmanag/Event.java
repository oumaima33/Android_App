package com.example.taskmanag;

public class Event {

    String id, title ,desc ,date ;

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

    public Event(String id, String title, String desc , String date){
        this.id=id;
        this.title=title;
        this.desc=desc;
        this.date=date;
    }
}
