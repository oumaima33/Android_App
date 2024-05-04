package com.example.taskmanag;

public class Model {

    String id, title ,desc ,deadline ,doc,img;
    public Model(String id,String title,String desc ,String deadline, String doc ,String img){
        this.id=id;
        this.title=title;
        this.desc=desc;
        this.deadline=deadline;
        this.doc=doc;
        this.img=img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
