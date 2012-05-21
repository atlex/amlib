/**
 * Date         Comments
 * ---------------------------------------------
 * 2005-10-03 MouseInputHandler is changed.
 * 2005-09-13 Some private classes are public now.
 */
package org.am.utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.Comparator;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.*;

/**
 * The table utilities.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.10 $
 */
public class TableUtils {
    /**
     * The constant.
     */
    public static final String PASSWORD_STARS = "************";
    /**
     * The constant.
     */
    public static final String PASSWORD_STAR = "*";
    /**
     * The constant.
     */
    public static final String DELIMETER = " - ";
    /**
     * The constant.
     */
    public static final String EMPTY = "";
    
    public static final int SORT_ASCEND = 1;
    public static final int SORT_DESCEND = 0;

    /**
     * Creates the default table.
     *
     * @param columnNames the names of the columns.
     * @return the table with defualt settings
     */
    public static JTable createDefaultTable(String[] columnNames) {
        return new JTable(createTableModel(columnNames));
    }

    /**
     * Creates the table.
     *
     * @param tableModel the table's model
     * @return the table
     */
    public static JTable createTable(TableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setDefaultRenderer(Object.class, new TableCellUnfocusableRenderer());
        table.setDefaultEditor(Object.class, null);

        return table;
    }

    /**
     * Creates the table.
     *
     * @param columnNames the names of the columns.
     * @return the table
     */
    public static JTable createTable(String[] columnNames) {
        JTable table = createDefaultTable(columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setDefaultRenderer(Object.class, new TableCellUnfocusableRenderer());
        table.setDefaultEditor(Object.class, null);

        return table;
    }

    /**
     * Creates the table.
     *
     * @return the table
     */
    public static JTable createTable() {
        return createTable(new String[0]);
    }

    /**
     * Creates a DefaultTableModel.
     *
     * @param columnNames the column names
     * @return            a DefaultTableModel
     */
    public static DefaultTableModel createTableModel(String[] columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel();

        for (int i = 0; i < columnNames.length; i++) {
            tableModel.addColumn(columnNames[i]);
        }
        return tableModel;
    }

    /**
     * Clears the table.
     *
     * @param table the table
     */
    public static void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.getDataVector().clear();
        model.fireTableDataChanged();
    }

    /**
     * Converts the table model to the array of rows.
     * Array of rows is the object array of object arrays (Object[][]).
     *
     * @param tableModel the table model
     * @return the array of rows
     */
    public static Object[][] tableModelToArray(TableModel tableModel) {
        DefaultTableModel model = (DefaultTableModel)tableModel;
        Vector rowsVec = model.getDataVector();
        int rowsCount = rowsVec.size();
        Object[][] rows = new Object[rowsCount][];
        Vector rowVec;

        for (int i = 0; i < rowsCount; i++) {
            rowVec = (Vector)rowsVec.elementAt(i);
            rows[i] = rowVec.toArray();
        }
        return rows;
    }

    /**
     * Gets the TableCellUnfocusableRenderer.
     *
     * @return the TableCellUnfocusableRenderer
     */
    public static TableCellUnfocusableRenderer getTableCellUnfocusableRenderer() {
        return new TableCellUnfocusableRenderer();
    }

    /**
     * Gets the TableCellPasswordRenderer.
     *
     * @return the TableCellPasswordRenderer
     */
    public static TableCellPasswordRenderer getTableCellPasswordRenderer() {
        return new TableCellPasswordRenderer();
    }

    /**
     * Gets the cell renderrer with checkbox.
     *
     * @return the cell renderrer with checkbox
     */
    public static BooleanCellRenderer getBooleanCellRenderer() {
        return new BooleanCellRenderer();
    }

    /**
     * Gets a new BooleanCellEditor.
     *
     * @return a new BooleanCellEditor
     */
    public static BooleanCellEditor getBooleanCellEditor() {
        return new BooleanCellEditor();
    }

    /**
     * Gets a new ComboBoxCellEditor.
     *
     * @return a new ComboBoxCellEditor
     */
    public static ComboBoxCellEditor getComboBoxCellEditor() {
        return new ComboBoxCellEditor();
    }

    public static SorterableHeader getSorterableHeader(TableColumnModel columnModel) {
        SorterableHeader sorterableHeader = new SorterableHeader(columnModel);
        sorterableHeader.setUI(new ClickableTableHeaderUI());

        return sorterableHeader;
    }

    /**
     * Adds th row into the table.
     * Clears the bottom controls. Puts the input int the first control.
     */
    public static void addNewRow(JTable table, Container controlsContainer) throws Exception {
        Object[] row = new Object[controlsContainer.getComponentCount()];
        Component comp;
        String item = null;
        Object objItem;
        JTextField textField;
        JComboBox comboBox;

        for (int i = 0; i < controlsContainer.getComponentCount(); i++) {
            comp = controlsContainer.getComponent(i);

            if (comp instanceof JTextField) {
                textField = (JTextField)comp;
                row[i] = textField.getText();
                textField.setText(EMPTY);
            } else if (comp instanceof JComboBox) {
                comboBox = (JComboBox)comp;
                objItem = comboBox.getSelectedItem();
                if (objItem instanceof StringBuffer) {
                    item = objItem.toString();
                } else if (objItem instanceof String) {
                    item = (String) objItem;
                }
                if (item != null) {
                    if (item.indexOf(DELIMETER) > -1) {
                        row = new Object[2];
                        row[0] = item.substring(0, item.indexOf(DELIMETER));
                        row[1] = item.substring(item.indexOf(DELIMETER) + DELIMETER.length());
                    } else {
                        row[i] = item;
                    }
                    comboBox.removeItem(item);
                } else {
                    return;
                }
            }
        }

        addRow(table, row);
        controlsContainer.getComponent(0).requestFocus();
    }
    /**
     * Adds a row to the end of the table.
     *
     * @param table the table
     * @param row   a row
     */
    public static void addRow(JTable table, Object[] row) {
        getModel(table).addRow(row);
    }

    /**
     * Updates the selected row from controls.
     *
     * @param table             a table
     * @param controlsContainer a container of controls
     * @throws Exception an exception
     */
    public static void updateSelectedRow(JTable table, Container controlsContainer) throws Exception {
        int selRow = table.getSelectedRow();
        DefaultTableModel tableModel = ((DefaultTableModel)table.getModel());

        if (selRow > -1) {
            int compCount = controlsContainer.getComponentCount();
            int column = 0;
            Object value = null;
            Component comp;
            String item;
            StringBuffer newItem;
            JComboBox comboBox;

            for (int i = 0; i < compCount; i++) {
                comp = controlsContainer.getComponent(i);
                if (!(comp instanceof JLabel)) {
                    if ((comp instanceof JTextField)) {
                        value = ((JTextField)comp).getText();
                    } else if (comp instanceof JToggleButton) {
                        value = new Boolean(((JToggleButton)comp).isSelected());
                    } else if (comp instanceof JComboBox) {
                        comboBox = (JComboBox)comp;
                        item = (String)comboBox.getSelectedItem();
                        if (item.indexOf(DELIMETER) > -1) {
                            //Moves the second value from the table to the combobox.
                            newItem = new StringBuffer();
                            newItem.append(tableModel.getValueAt(selRow, 0));
                            newItem.append(DELIMETER);
                            newItem.append(tableModel.getValueAt(selRow, 1));
                            comboBox.addItem(newItem.toString());
                            //Moves the first value from the combobox to the table.
                            comboBox.removeItem(item);
                            tableModel.setValueAt(item.substring(0, item.indexOf(DELIMETER)), selRow, 0);
                            tableModel.setValueAt(item.substring(item.indexOf(DELIMETER) + DELIMETER.length()), selRow, 1);
                            return;
                        } else {
                            value = item;
                        }
                    }

                    tableModel.setValueAt(value, selRow, column);
                    column++;
                }
            }
        }
    }

    /**
     * Puts the row into edit components.
     *
     * @param table             a table
     * @param controlsContainer a container of controls
     * @throws Exception an exception
     */
    public static void setRowIntoControls(JTable table, Container controlsContainer) throws Exception {
        int selRow = table.getSelectedRow();
        int compCount = controlsContainer.getComponentCount();
        int column = 0;
        Object value;
        Component comp;

        for (int i = 0; i < compCount; i++) {
            if (selRow > -1) {
                value = table.getValueAt(selRow, column);
                comp = controlsContainer.getComponent(i);
                if (comp instanceof JTextField) {
                    ((JTextField)comp).setText(value.toString());
                } else if (value instanceof JComboBox) {
                    ((JComboBox)comp).setSelectedItem(value);
                }
            }
            column++;
        }
    }

    /**
     * Deletes the selected row. Selects a first row.
     *
     * @param table a table
     */
    public static void deleteSelectedRow(JTable table) {
        int selRow = table.getSelectedRow();
        if (selRow > -1) {
            getModel(table).removeRow(selRow);
            if (table.getRowCount() > 0) {
                table.getSelectionModel().setSelectionInterval(0, 0);
            }
        }
    }

    /**
     * Deletes the selected row.
     * Adds the removed row to the combobox.
     *
     * @param table a table
     * @throws Exception an exception
     */
    public static void deleteSelectedRow(JTable table, Container controlsContainer) throws Exception {
        int selRow = table.getSelectedRow();

        if (selRow > -1) {
            DefaultTableModel tableModel = getModel(table);
            int compCount = controlsContainer.getComponentCount();
            int column = 0;
            Component comp;
            StringBuffer item;

            for (int i = 0; i < compCount; i++) {
                comp = controlsContainer.getComponent(i);
                if (comp instanceof JComboBox) {
                    item = new StringBuffer();
                    item.append(tableModel.getValueAt(selRow, column));
                    item.append(DELIMETER);
                    item.append(tableModel.getValueAt(selRow, column + 1));
                    ((JComboBox)comp).addItem(item.toString());
                }
                column++;
            }
            deleteSelectedRow(table);
        }
    }
    /**
     * Gets rows of a table and converts rows to the combobox's items.
     *
     * @param table a table
     * @return combobox's items
     */
    public static Object[] getItems(JTable table) {
        DefaultTableModel tableModel = TableUtils.getModel(table);
        Vector itemsVec = tableModel.getDataVector();
        Object[] items = new Object[itemsVec.size()];

        Vector itemVec;
        StringBuffer itemStr;
        for (int i = 0; i < itemsVec.size(); i++) {
//            itemStr = "";
            itemStr = new StringBuffer();
            itemVec = (Vector)itemsVec.elementAt(i);
            for (int j = 0; j < itemVec.size(); j++) {
                itemStr.append(itemVec.elementAt(j));
                itemStr.append(DELIMETER);
//                itemStr += itemVec.elementAt(j) + DELIMETER;
                items[i] = itemStr.substring(0, itemStr.lastIndexOf(DELIMETER));
            }
        }
        return items;
    }

    /**
     * Gets the default table model.
     *
     * @param table a table
     * @return a default table model
     */
    public static DefaultTableModel getModel(JTable table) {
        return (DefaultTableModel)table.getModel();
    }

    /**
     * Gets the password stars.
     *
     * @param count a count of stars.
     * @return the password stars
     */
    protected static String getStars(int count) {
        StringBuffer stars = new StringBuffer(count);
        for (int i = 0; i < count; i++) {
            stars.append(PASSWORD_STAR);
//            stars += PASSWORD_STAR;
        }

        return stars.toString();
    }

    /**
     * Sets the rows into the table.
     * Clears old values.
     *
     * @param table
     * @param rows
     */
    public static void setRows(JTable table, Object[][] rows) {
        DefaultTableModel tableModel = TableUtils.getModel(table);
        TableUtils.clearTable(table);
        if (rows != null) {
            for (int i = 0; i < rows.length; i++) {
                tableModel.addRow(rows[i]);
            }
        }
    }

    /**
     * Gets rows of a table.
     *
     * @param table a table
     * @return rows
     */
    public static Object[][] getRows(JTable table) {
        return tableModelToArray(TableUtils.getModel(table));
    }

    /**
     * Gets a table value.
     *
     * @param table      a table
     * @param mousePoint some mouse point on a table
     * @return           a table value
     */
    public static Object getValue(JTable table, Point mousePoint) {
        return table.getValueAt(table.rowAtPoint(mousePoint), table.columnAtPoint(mousePoint));
    }

    /**
     * Gets a row text.
     *
     * @param table       a table
     * @param mousePoint  some mouse point on a table
     * @param columnDelim column delimiter
     * @return            a row text
     */
    public static String getRowText(JTable table, Point mousePoint, String columnDelim) {
        int rowNumber = table.rowAtPoint(mousePoint);
        int colCount = table.getColumnCount();
        StringBuffer rowText = new StringBuffer();
        for (int i = 0; i < colCount; i++) {
            rowText.append(table.getValueAt(rowNumber, i));
            rowText.append(columnDelim);
        }
        return rowText.toString();
    }

    /**
     * Gets text of selected rows.
     *
     * @param table       a table
     * @param columnDelim column delimiter
     * @return            text of selected rows
     */
    public static String getSelectedRowsText(JTable table, String columnDelim) {
        int[] rowNumbers = table.getSelectedRows();
        int rowNumber;
        StringBuffer rowText = new StringBuffer();
        int colCount = table.getColumnCount();
        for (int i = 0; i < rowNumbers.length; i++) {
            rowNumber = rowNumbers[i];
            for (int j = 0; j < colCount; j++) {
                rowText.append(table.getValueAt(rowNumber, j));
                rowText.append(columnDelim);
            }
        }
        return rowText.toString();
    }

    /**
     * Gets text of selected rows.
     *
     * @param table       a table
     * @param columnIndex column index
     * @return            text of selected rows
     */
    public static String getSelectedRowsText(JTable table, int columnIndex) {
        int[] rowNumbers = table.getSelectedRows();
        int rowNumber;
        StringBuffer rowText = new StringBuffer();
        int colCount = table.getColumnCount();
        for (int i = 0; i < rowNumbers.length; i++) {
            rowNumber = rowNumbers[i];
            rowText.append(table.getValueAt(rowNumber, columnIndex));
            rowText.append('\n');
        }
        return rowText.toString();
    }

    /**
     * The TableCellUnfocusableRenderer.
     */
    private static class TableCellUnfocusableRenderer extends DefaultTableCellRenderer {
        /**
         * Returns the default table cell renderer.
         *
         * @param table      the <code>JTable</code>
         * @param value      the value to assign to the cell at <code>[row, column]</code>
         * @param isSelected true if cell is selected
         * @param hasFocus   true if cell has focus
         * @param row        the row of the cell to render
         * @param column     the column of the cell to render
         * @return the default table cell renderer
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
            return this;
        }
    }

    /**
     * The TableCellPasswordRenderer.
     */
    private static class TableCellPasswordRenderer extends DefaultTableCellRenderer {
        /**
         * Returns the default table cell renderer.
         *
         * @param table      the <code>JTable</code>
         * @param value      the value to assign to the cell at <code>[row, column]</code>
         * @param isSelected true if cell is selected
         * @param hasFocus   true if cell has focus
         * @param row        the row of the cell to render
         * @param column     the column of the cell to render
         * @return the default table cell renderer
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, false, row, column);

            if (value instanceof String) {
                value = getStars(((String)value).length());
            }
            if (value == null || value.equals("")) {
                value = PASSWORD_STARS;
            }
            this.setValue(value);

            return this;
        }
    }

    /**
     *
     */
    private static class BooleanCellRenderer extends DefaultTableCellRenderer {
        private static JCheckBox checkBox;

        public BooleanCellRenderer() {
            super();
            checkBox = new JCheckBox();
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        }
        /**
         * Returns the default table cell renderer.
         *
         * @param table      the <code>JTable</code>
         * @param value      the value to assign to the cell at <code>[row, column]</code>
         * @param isSelected true if cell is selected
         * @param hasFocus   true if cell has focus
         * @param row        the row of the cell to render
         * @param column     the column of the cell to render
         * @return the default table cell renderer
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (isSelected) {
                checkBox.setBackground(table.getSelectionBackground());
            } else {
                checkBox.setBackground(table.getBackground());
            }
            checkBox.setSelected(((Boolean)value).booleanValue());

            return checkBox;
        }
    }

    /**
     * This class represents a BooleanCellEditor.
     */
    private static class BooleanCellEditor extends DefaultCellEditor {
        /**
         * Constructor for the BooleanEditor object.
         */
        public BooleanCellEditor() {
            super(new JCheckBox());
            JCheckBox checkBox = (JCheckBox)getComponent();
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    /**
     * This class represents a ComboBoxCellEditor.
     */
    private static class ComboBoxCellEditor extends DefaultCellEditor {
        /**
         * Constructor for the ComboBoxCellEditor object.
         */
        public ComboBoxCellEditor() {
            super(new JComboBox());
        }
    }

    /**
     * Header with sort attributes created February 20, 2003
     *
     * @author Stas Lapitsky
     * @version 1.0
     */
    public static class SorterableHeader extends JTableHeader {
        /**
         * The field of class.
         */
        protected int[] sortings;

        /**
         * Constructor for the SorterableHeader object
         *
         * @param cm Description of the Parameter
         */
        public SorterableHeader(TableColumnModel cm) {
            super(cm);
            sortings = new int[cm.getColumnCount()];
            for (int i = 0; i < sortings.length; i++) {
                sortings[i] = 0;
            }
        }

        /**
         * Sets the columnModel attribute of the SorterableHeader object
         *
         * @param columnModel The new column model value
         */
        public void setColumnModel(TableColumnModel columnModel) {
            super.setColumnModel(columnModel);
            sortings = new int[columnModel.getColumnCount()];
            for (int i = 0; i < sortings.length; i++) {
                sortings[i] = 0;
            }
        }

        /**
         * Sets the sortRegim attribute of the SorterableHeader object
         *
         * @param columnIndex a column index
         * @param regim       a sort regim
         * @see TableUtils#SORT_ASCEND
         * @see TableUtils#SORT_DESCEND
         */
        public void setSortRegim(int columnIndex, int regim) {
            if (sortings.length > 0) {
                sortings[columnIndex] = regim;
            }
        }

        /**
         * Gets the sort regim attribute of the SorterableHeader object
         *
         * @param columnIndex a column index
         * @return a sort regim
         */
        public int getSortRegim(int columnIndex) {
            int sortMode = 0;
            if (sortings.length > 0) {
                sortMode = sortings[columnIndex];
            }

            return sortMode;
        }
    }

    /**
     * Class for Sorting functionality on header mouse click
     *
     * @author Stas Lapitsky created February 20, 2003
     * @version 1.0
     */
    public static class ClickableTableHeaderUI extends BasicTableHeaderUI {

        /**
         * Sort type: Ascending/Descending
         */
        protected boolean sortType;

        /**
         * Table on which sort is applyed
         */
        protected JTable table;

        /**
         * Data of the table
         */
        protected Object[][] rows;

        /**
         * Constructor for the ClickableTableHeaderUI object
         */
        public ClickableTableHeaderUI() {
            super();
        }

        /**
         * Gets the header attribute of the ClickableTableHeaderUI object
         *
         * @return The header value
         */
        protected JTableHeader getHeader() {
            return header;
        }

        /**
         * Imulates click on column header to sort table
         *
         * @param columnIndex - column index on which hase been clicked
         */
        public void sort(int columnIndex) {
            table = getHeader().getTable();
            columnIndex = table.convertColumnIndexToModel(columnIndex);
            if (columnIndex != -1) {
                rows = TableUtils.getRows(table);
                sortType = ((SorterableHeader)getHeader()).getSortRegim(columnIndex) == 0;
                sortTable(rows, columnIndex, sortType);

                setRows(table, rows);

                if (sortType) {
                    ((SorterableHeader)getHeader()).setSortRegim(columnIndex, 1);
                } else {
                    ((SorterableHeader)getHeader()).setSortRegim(columnIndex, 0);
                }
                ((DefaultTableModel)table.getModel()).fireTableDataChanged();
            }
        }

        /**
         * Description of the Method
         *
         * @return Description of the Return Value
         */
        protected MouseInputListener createMouseInputListener() {
            return new MouseInputHandler();
        }

        /**
         * Sorts the table rows.
         *
         * @param rows        the table rows
         * @param columnIndex column index
         * @param asc         sort method
         */
        protected void sortTable(Object[][] rows, int columnIndex, boolean asc) {
            RowsComparator rowsComparator = new RowsComparator(columnIndex, asc);
            Arrays.sort(rows, rowsComparator);
        }

        /**
         * Class to sort table on mouse click on header
         *
         * @author Dan Galitsky created February 20, 2003
         * @version 1.0
         */
        private class MouseInputHandler extends BasicTableHeaderUI.MouseInputHandler {
            /**
             * The field of class.
             */
            TableColumnModel columnModel;

            /**
             * Description of the Method
             *
             * @param e Description of the Parameter
             */
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }

                Point p = e.getPoint();
                // First find which header cell was hit
                columnModel = header.getColumnModel();
                int index = header.columnAtPoint(p);
                if (index != -1) {
                    Rectangle r = header.getHeaderRect(index);
                    // The last 3 pixels + 3 pixels of next column are for resizing
                    r.grow(-3, 0);
                    if (r.contains(p)) {
                        sort(columnModel.getColumnIndexAtX(e.getPoint().x));
                    }
                }
            }
        }
    }

    /**
     * This class compares the table rows.
     * The table rows should be Object[][].
     */
    public static class RowsComparator implements Comparator {
        /**
         * Stores the column index.
         */
        protected int columnIndex;
        /**
         * Stores the sort method.
         */
        protected boolean asc;

        /**
         * Creates a new RowsComparator object.
         *
         * @param newColumnIndex column index
         * @param newAsc         sort method
         */
        public RowsComparator(int newColumnIndex, boolean newAsc) {
            columnIndex = newColumnIndex;
            asc = newAsc;
        }

        /**
         * Compares two rows.
         *
         * @param o1 row 1
         * @param o2 row 2
         * @return 0, 1, -1
         */
        public int compare(Object o1, Object o2) {
            int result;

            Object[] row1 = (Object[]) o1;
            Object[] row2 = (Object[]) o2;

            Object value1 = row1[columnIndex];
            Object value2 = row2[columnIndex];

            result = value1.toString().compareTo(value2.toString());
            if (!asc) {
                result *= -1;
            }
            return result;
        }
    }
}
