package main.java;

import java.sql.Date;

class Notification {

    private int type;
    private String doc;
    private Date date;
    private int fine;

    Notification (int type) {
        this.type = type;
        this.doc = "null";
        this.date = Main.date;
        this.fine = 0;
    }

    Notification (int type, String doc) {
        this.type = type;
        this.doc = doc;
        this.date = Main.date;
        this.fine = 0;
    }

    Notification (int type, String doc, int fine) {
        this.type = type;
        this.doc = doc;
        this.date = Main.date;
        this.fine = fine;
    }

    Notification (int type, String doc, Date date) {
        this.type = type;
        this.doc = doc;
        this.date = date;
        this.fine = 0;
    }

    Notification (int type, String doc, Date date, int fine) {
        this.type = type;
        this.doc = doc;
        this.date = date;
        this.fine = fine;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
}
