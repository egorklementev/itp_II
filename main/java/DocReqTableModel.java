package main.java;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is used only for building the user documents table. */
public class DocReqTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<Document> documents;

    DocReqTableModel() {
        documents = new ArrayList<>();
        documents.addAll(Main.reqDocs);
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Title";
            case 1:
                return "Requests number";
        }
        return "";
    }

    public int getRowCount() {
        return documents.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Document document = documents.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return document.getTitle();
            case 1:
                return Main.priorityQueues.get(rowIndex).size();
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
