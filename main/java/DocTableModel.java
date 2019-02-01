package main.java;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is used only for building the library document table. */
public class DocTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Document> documents;

    public DocTableModel(List<Document> documents) {
        this.documents = documents;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Title";
            case 1:
                return "Type";
            case 2:
                return "Copies";
            case 3:
                return "Price";
            case 4:
                return "Reference";
            case 5:
                return "Bestseller";
        }
        return "";
    }

    public int getRowCount() {
        return documents.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Document doc = documents.get(rowIndex);
        String type = "Document";
        // Check if the document is a book
        boolean isBS = false;
        if (doc instanceof Book){
            type = "Book";
            if (doc.isBestSeller()) isBS = true;
        }
        if (doc instanceof Book) type = "A book";
        if (doc instanceof AudioVideo) type = "An AV material";
        if (doc instanceof JournalArticle) type = "A journal article";
        switch (columnIndex) {
            case 0:
                return doc.getTitle();
            case 1:
                return type;
            case 2:
                return doc.getCopies();
            case 3:
                return doc.getPrice();
            case 4:
                return doc.isReference();
            case 5:
                return isBS;
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

}
