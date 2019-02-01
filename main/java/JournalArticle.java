package main.java;

class JournalArticle extends Document {

    /** Initialisation of the journal article.
     *  @param title, authors, journal, issue, publisher, issue_editors, pub_date, price, copies. */
    JournalArticle(String title, String authors, int price, int copies, boolean isReference){
        super(title, authors, price, copies, isReference);
    }

    /** Constructor for the database purposes. */
    JournalArticle (Object document_id, String title, String authors, int price,
                int copies, boolean isReference, String description, String publisher,
                int edition, int year, boolean isBestSeller, boolean isOutstanding) {
        super(document_id, title, authors, price, copies, isReference, description, publisher, edition, year, isBestSeller, isOutstanding);
    }

}
