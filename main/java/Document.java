package main.java;

public class Document {

    private String title; // title of the document
    private String authors; // authors of the document
    private int price; // price of some document
    private int copies; // number of copies of some document
    private Object document_id; // id of the document
    private boolean isReference;
    private String description;
    private int year;
    private String publisher;
    private int edition;
    private boolean isBestSeller;
    private boolean isOutstanding;

    Document (String title, String authors, int price, int copies, boolean isReference) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.copies = copies;
        this.isReference = isReference;
        isOutstanding = false;
    }

    // Constructor for the DataBase purposes
    Document (Object document_id, String title, String authors, int price,
              int copies, boolean isReference, String description, String publisher,
              int edition, int year, boolean isBestSeller, boolean isOutstanding){
        this(title, authors, price, copies, isReference);
        this.document_id = document_id;
        this.description = description;
        this.publisher = publisher;
        this.edition = edition;
        this.year = year;
        this.isBestSeller = isBestSeller;
        this.isOutstanding = isOutstanding;
    }

    /** Returns the title of the document. */
    public String getTitle () {
        return title;
    }

    /** Sets the title of the document. */
    public void setTitle (String title) {
        this.title = title;
    }

    /** Returns whether the document is reference or not. */
    public boolean isReference() {
        return isReference;
    }

    /** Sets the 'reference state' of the document. */
    public void setReference(boolean reference) {
        isReference = reference;
    }

    /** Returns the copies of the document. */
    public int getCopies() {
        return copies;
    }

    /** Sets the number of copies of the document. */
    public void setCopies(int copies) {
        this.copies = copies;
    }

    /** Returns the price of the document. */
    public int getPrice() {
        return price;
    }

    /** Getter for authors of the document.*/
    public String getAuthors(){
        return authors;
    }

    /** Sets the authors of the document. */
    public void setAuthors(String authors){
        this.authors = authors;
    }

    /** Sets the price of the document. */
    public void setPrice(int price) {
        this.price = price;
    }

    /** Returns the description of the document. */
    public String getDescription() {
        return description;
    }

    /** Returns the year of publishing. */
    public int getYear() {
        return year;
    }

    /** Sets the year of publishing. */
    public void setYear(int year) { this.year = year; }

    /** Sets the id of the document (used for the database) */
    public void setDocument_id(Object document_id) {
        this.document_id = document_id;
    }

    /** Returns the id of the document (used for the database) */
    public Object getDocument_id(){
        return document_id;
    }

    /** Returns the publisher of the document. */
    public String getPublisher() {
        return publisher;
    }

    /** Sets the publisher of the document. */
    public void setPublisher(String publisher) { this.publisher = publisher; }

    /** Returns the edition of the document. */
    public int getEdition() {
        return edition;
    }

    /** Sets the edition of the document. */
    public void setEdition(int edition) { this.edition = edition; }

    /** Returns whether the document is bestseller or not. */
    public boolean isBestSeller() { return isBestSeller; }

    /** Sets the 'bestseller state' of the document. */
    public void setBestSeller(boolean isBestSeller) { this.isBestSeller = isBestSeller; }

    /** Returns whether the document has outstanding request. */
    public boolean isOutstanding() {
        return isOutstanding;
    }

    /** Places or removes the outstanding request for the document. */
    public void setOutstanding(boolean outstanding) {
        isOutstanding = outstanding;
    }
}
