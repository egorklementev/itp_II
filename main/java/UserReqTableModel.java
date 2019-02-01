package main.java;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** This class is used only for building the user documents table. */
public class UserReqTableModel extends AbstractTableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<User> users;

    public UserReqTableModel(List<User> users) {
        this.users = users;
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
                return "Username";
            case 1:
                return "Priority";
        }
        return "";
    }

    public int getRowCount() {
        return users.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getUsername();
            case 1:
                return user.getPriority();
        }
        return "";
    }

    /** Special method that is used to real-time update of the table. */
    void replace (List<User> users) {
        this.users.clear();
        fireTableRowsDeleted(0, getRowCount());
        for (int i = 0; i < users.size(); ++i)
            this.users.add(users.get(i));
        fireTableRowsInserted(0, users.size());
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
