package main.java;

import java.sql.Date;

class Booking {

    private Date date;
    private int duration;
    private Document doc;
    private boolean reqByLib;
    private boolean reqByUser;
    private boolean hasReceived;
    private boolean hasRenewed;

    /** Represents a single offer of any document
     *  @param doc document to be booked
     *  @param duration certain time for what document will stay booked */
    Booking (Document doc, int duration, boolean hasReceived) {
        this.duration = duration;
        this.doc = doc;
        this.hasReceived = hasReceived;
        date = Main.date;
        reqByLib = false;
        reqByUser = false;
        hasRenewed = false;
    }

    /** Represents a single offer of any document
     *  @param doc document to be booked
     *  @param duration certain time for what document will stay booked
     *  @param date the new date will replace the old date */
    Booking (Document doc, int duration, Date date) {
        this.doc = doc;
        this.duration = duration;
        this.date = date;
        reqByLib = false;
        reqByUser = false;
        hasReceived = false;
        hasRenewed = false;
    }

    /** Returns whether the order is overdue or not. */
    boolean isOverdue() { return getTimeLeft() < 0; }

    /** Returns whether the order was requested to return by librarian or not. */
    boolean hasRequestedByLib(){
        return reqByLib;
    }

    /** Returns whether the order was requested to return by user or not. */
    boolean hasRequestedByUser(){
        return reqByUser;
    }

    /** Request from the librarian to the user to return the document back. */
    void libRequest(){
        reqByLib = true;
    }

    /** Request from the user to the librarian to return the document back. */
    void userRequest(){
        reqByUser = true;
    }

    /** Returns the days that left before the user has to return the document.
     *  If the value is negative it means that the order is overdue. */
    int getTimeLeft() {
        if (hasReceived) {
            if (date.getYear() < Main.date.getYear()) return -365;
            int days[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int dayNow = Main.date.getDate();
            for (int i = 0; i < Main.date.getMonth(); ++i) dayNow += days[i];
            int bookDay = date.getDate();
            for (int i = 0; i < date.getMonth(); ++i) bookDay += days[i];
            return duration - (dayNow - bookDay);
        } else {
            return duration;
        }
    }

    /** Returns whether the document was renewed or not. */
    boolean hasRenewed () {
        return hasRenewed;
    }

    /** Sets renewed true. */
    void setRenewed() { hasRenewed = true; }

    /** Returns how much days from the beginning date can user hold the document. */
    int getDuration() { return duration; }

    /** Returns the date when the document was booked. */
    Date getDate() { return date; }

    /** Sets new value for the date. */
    void setDate(Date date) { this.date = date; }

    /** Returns the instance of the document. */
    Document getDoc () {
        return doc;
    }

    /** Returns whether the user has received the document or not. */
    boolean hasReceived() {
        return hasReceived;
    }

    /** Sets the 'receiving status' of the document. */
    void setReceived() {
        this.hasReceived = true;
        date = Main.date;
    }

    /** Overload for the database */
    void setReceived(boolean received) { hasReceived = received; }
}
