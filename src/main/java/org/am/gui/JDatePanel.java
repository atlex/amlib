/**
 * Date         Comments
 * ---------------------------------------------
 * 2005-10-05 Refactoring.
 */
package org.am.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * @author Juan Heyns
 *         Created on 26-Mar-2004
 *         Refactored on 21-Jun-2004
 *         Refactored on 8-Jul-2004
 */
public class JDatePanel extends JPanel {
    /**
     * The foreground of the selected current day.
     */
    protected static final Color F_SELECTED_CURRENT_DAY = Color.WHITE;
    /**
     * The foreground of the current day.
     */
    protected static final Color F_CURRENT_DAY = Color.BLUE;
    /**
     * The foreground of the wekeend day.
     */
    protected static final Color F_WEEKEND_DAY = Color.RED;
    /**
     * The foreground of the names of the days.
     */
    protected static final Color F_NAME_OF_DAY = Color.WHITE;
    /**
     * The background of the names of the days.
     */
    protected static final Color B_NAME_OF_DAY = Color.GRAY;

    protected static final String TODAY = "Today: ";
    protected static final String[] NAMES_OF_DAYS = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    /**
     * The cursor of the labels.
     */
    protected static final Cursor LABEL_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    /**
     * The default locale for JDatePanel.
     * To get localized version of JDatePanel use Locale.getDefault();
     */
    protected static final Locale DEFAULT_LOCALE = Locale.US;

    /**
     * Stores the date format.
     */
    protected int dateFormat = DateFormat.LONG;
    protected int firstDayOfWeek = Calendar.MONDAY;

    protected ArrayList actionListeners;

    protected GregorianCalendarModel calendarModel;

    protected javax.swing.JPanel centerPanel;
    protected Vector changeListeners;

    protected JTable dayTable;

    protected CalendarTableCellRenderer dayTableCellRenderer;
    protected JTableHeader dayTableHeader;
    protected EventHandler eventHandler;
    protected JLabel monthLabel;
    protected JPopupMenu monthPopupMenu;
    protected JMenuItem[] monthPopupMenuItems;
    protected JButton nextMonthButton;
    protected JPanel northCenterPanel;

    protected JPanel northPanel;
    protected JButton previousMonthButton;
    protected JPanel southPanel;
    protected JLabel todayLabel;
    protected JSpinner yearSpinner;

    /**
     * This inner class renders the table of the days
     */
    private class CalendarTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel label = (JLabel)super.getTableCellRendererComponent(arg0, arg1, isSelected, hasFocus, row, col);
            label.setHorizontalAlignment(JLabel.CENTER);

            if (row == -1) {
                //The names of the days
                if (arg1.equals(NAMES_OF_DAYS[0]) || arg1.equals(NAMES_OF_DAYS[6])) {
                    label.setForeground(F_WEEKEND_DAY);
                } else {
                    label.setForeground(F_NAME_OF_DAY);
                }
                label.setBackground(B_NAME_OF_DAY);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }

            GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
            Integer day = (Integer)arg1;
            Calendar today = new GregorianCalendar();
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            //Setting Foreground
            if (cal.get(Calendar.DATE) == day.intValue()) {
                if (today.get(Calendar.DATE) == day.intValue() && today.get(Calendar.MONTH) == calendarModel.getCalendar(Calendar.MONTH) && today.get(Calendar.YEAR) == calendarModel.getCalendar(Calendar.YEAR)) {
                    label.setForeground(F_SELECTED_CURRENT_DAY);
                    label.setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    label.setForeground(Color.WHITE);
                }
            } else if (day.intValue() < 1 || day.intValue() > lastDay) {
                //The days of the other month
                label.setForeground(Color.LIGHT_GRAY);
                if (day.intValue() > lastDay) {
                    label.setText(Integer.toString(day.intValue() - lastDay));
                } else {
                    Calendar lastMonth = new GregorianCalendar();
                    lastMonth.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1);
                    int lastDayLastMonth = lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
                    label.setText(Integer.toString(lastDayLastMonth + day.intValue()));
                }
            } else {
                if (today.get(Calendar.DATE) == day.intValue() && today.get(Calendar.MONTH) == calendarModel.getCalendar(Calendar.MONTH) && today.get(Calendar.YEAR) == calendarModel.getCalendar(Calendar.YEAR)) {
                    label.setForeground(F_CURRENT_DAY);
                    label.setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    if (isWeekend(day.intValue())) {
                        label.setForeground(F_WEEKEND_DAY);
                    } else {
                        label.setForeground(Color.BLACK);
                    }
                }
            }

            //Setting background
            if (cal.get(Calendar.DATE) == day.intValue()) {
                label.setBackground(new Color(10, 36, 106));
            } else {
                label.setBackground(Color.WHITE);
            }

            return label;
        }
    }

    /**
     * This inner class hides the public event handling methods from the outside
     */
    private class EventHandler implements ActionListener, MouseListener, ChangeListener {

        public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource().equals(nextMonthButton)) {
                GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
                int day = cal.get(Calendar.DATE);
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1);
                if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                } else {
                    cal.set(Calendar.DATE, day);
                }
                calendarModel.setCalendar(cal.getTime());
            } else if (arg0.getSource().equals(previousMonthButton)) {
                GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
                int day = cal.get(Calendar.DATE);
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1);
                if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                } else {
                    cal.set(Calendar.DATE, day);
                }
                calendarModel.setCalendar(cal.getTime());
            } else {
                for (int month = 0; month < monthPopupMenuItems.length; month++) {
                    if (arg0.getSource().equals(monthPopupMenuItems[month])) {
                        GregorianCalendar cal = (GregorianCalendar)calendarModel.getCalendarClone();
                        int day = cal.get(Calendar.DATE);
                        cal.set(cal.get(Calendar.YEAR), month, 1);
                        if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        } else {
                            cal.set(Calendar.DATE, day);
                        }
                        calendarModel.setCalendar(cal.getTime());
                    }
                }
            }
        }

        public void mouseClicked(MouseEvent arg0) {
            if (arg0.getSource().equals(monthLabel)) {
                getMonthPopupMenu().setLightWeightPopupEnabled(false);
                monthPopupMenu.show((Component)arg0.getSource(), arg0.getX(), arg0.getY());
            } else if (arg0.getSource().equals(todayLabel)) {
                calendarModel.setCalendar(new Date());
//                fireActionPerformed(); //it hides JDatePanel when the todayLabel was clicked.
            } else if (arg0.getSource().equals(dayTable)) {
                int row = dayTable.getSelectedRow();
                int col = dayTable.getSelectedColumn();
                if (row >= 0 && row <= 5) {
                    Integer date = (Integer)calendarModel.getValueAt(row, col);
                    calendarModel.setCalendar(Calendar.DAY_OF_MONTH, date.intValue());
                    fireActionPerformed();
                }
            }
        }

        public void mouseEntered(MouseEvent arg0) {
        }

        public void mouseExited(MouseEvent arg0) {
        }

        public void mousePressed(MouseEvent arg0) {
        }

        public void mouseReleased(MouseEvent arg0) {
        }

        public void stateChanged(ChangeEvent arg0) {
            if (arg0.getSource().equals(calendarModel)) {
                Iterator i = getChangeListeners().iterator();
                while (i.hasNext()) {
                    ChangeListener cl = (ChangeListener)i.next();
                    cl.stateChanged(new ChangeEvent(JDatePanel.this));
                }
            }
        }
    }

    /**
     * This model represents the selected date, it is used by the table and year spinner
     */
    private class GregorianCalendarModel implements TableModel, SpinnerModel {
        private GregorianCalendar calendar;
        private ArrayList changeListeners;     // spinner
        private ArrayList monthLabels;         // label
        private ArrayList tableModelListeners; // table

        public GregorianCalendarModel() {
            calendar = (GregorianCalendar)GregorianCalendar.getInstance(DEFAULT_LOCALE);
            calendar.setFirstDayOfWeek(firstDayOfWeek);
            changeListeners = new ArrayList();
            tableModelListeners = new ArrayList();
            monthLabels = new ArrayList();
        }

        public void addChangeListener(ChangeListener arg0) {
            changeListeners.add(arg0);
        }

        public void addMonthLabel(JLabel label) {
            monthLabels.add(label);
            fireMonthTextFieldsUpdate();
        }

        public void addTableModelListener(TableModelListener arg0) {
            tableModelListeners.add(arg0);
        }

        public void addTodayLabel(JLabel label) {
            DateFormat df1 = DateFormat.getDateInstance(dateFormat, DEFAULT_LOCALE);
            label.setText(TODAY + df1.format(new Date()));
        }

        private void fireCalendarChanged() {
            //notify the spinner view of a change
            Iterator i = changeListeners.iterator();
            while (i.hasNext()) {
                ChangeListener cl = (ChangeListener)i.next();
                cl.stateChanged(new ChangeEvent(this));
            }
        }

        public void fireCalendarInvalidated() {
            fireCalendarChanged();
            fireTableModelEvent();
            fireMonthTextFieldsUpdate();
        }

        private void fireMonthTextFieldsUpdate() {
            //change monthLabel text
            Iterator i = monthLabels.iterator();
            JLabel label;
            DateFormatSymbols df;
            while (i.hasNext()) {
                label = (JLabel)i.next();
                df = new DateFormatSymbols(DEFAULT_LOCALE);
                label.setText(df.getMonths()[calendar.get(Calendar.MONTH)]);
            }
        }

        private void fireTableModelEvent() {
            //notify the table view of a change
            Iterator i = tableModelListeners.iterator();
            TableModelListener tl;
            while (i.hasNext()) {
                tl = (TableModelListener)i.next();
                tl.tableChanged(new TableModelEvent(this));
            }
        }

        public int getCalendar(int field) {
            return calendar.get(field);
        }

        public Calendar getCalendarClone() {
            return (Calendar)calendar.clone();
        }

        public Class getColumnClass(int arg0) {
            return Integer.class;
        }

        public int getColumnCount() {
            return 7;
        }

        public String getColumnName(int colIndex) {
            String name;
            int index = colIndex + firstDayOfWeek;
            if (index > Calendar.SATURDAY) {
                index = Calendar.SUNDAY;
            }
            switch (index) {
                case Calendar.SUNDAY:
                    name = NAMES_OF_DAYS[0];
                    break;
                case Calendar.MONDAY:
                    name = NAMES_OF_DAYS[1];
                    break;
                case Calendar.TUESDAY:
                    name = NAMES_OF_DAYS[2];
                    break;
                case Calendar.WEDNESDAY:
                    name = NAMES_OF_DAYS[3];
                    break;
                case Calendar.THURSDAY:
                    name = NAMES_OF_DAYS[4];
                    break;
                case Calendar.FRIDAY:
                    name = NAMES_OF_DAYS[5];
                    break;
                case Calendar.SATURDAY:
                    name = NAMES_OF_DAYS[6];
                    break;
                default:
                    name = "";
            }
            return name;
        }

        public Object getNextValue() {
            return Integer.toString(getCalendar(Calendar.YEAR) + 1);
        }

        public Object getPreviousValue() {
            return Integer.toString(getCalendar(Calendar.YEAR) - 1);
        }

        public int getRowCount() {
            return 6;
        }

        public Object getValue() {
            return Integer.toString(getCalendar(Calendar.YEAR));
        }

        public Object getValueAt(int row, int col) {
            Calendar firstDayOfMonth = (Calendar)calendar.clone();
            firstDayOfMonth.set(Calendar.DATE, 1);
            int DOW = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
            int value = col - DOW + row * 7 + (firstDayOfWeek + 1);
            return new Integer(value);
        }

        public boolean isCellEditable(int arg0, int arg1) {
            return false;
        }

        public void removeChangeListener(ChangeListener arg0) {
            changeListeners.remove(arg0);
        }

        public void removeMonthLabel(JLabel label) {
            monthLabels.remove(label);
        }

        public void removeTableModelListener(TableModelListener arg0) {
            tableModelListeners.remove(arg0);
        }

        public void setCalendar(Date date) {
            calendar.setTime(date);
            fireCalendarInvalidated();
        }

        public void setCalendar(int field, int value) {
            calendar.set(field, value);
            fireCalendarInvalidated();
        }

        public void setCalendar(int year, int month, int date) {
            calendar.set(year, month, date);
            fireCalendarInvalidated();
        }

        public void setValue(Object arg0) {
            int year = Integer.parseInt((String)arg0);
            setCalendar(Calendar.YEAR, year);
        }

        public void setValueAt(Object arg0, int arg1, int arg2) {
        }
    }

    /**
     * This tests the JDatePanel
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame testFrame = new JFrame();
        testFrame.getContentPane().add(new JDatePanel());
        testFrame.setBounds(300, 200, 300, 200);
        testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        testFrame.setVisible(true);
    }

    /**
     * This is the default constructor
     */
    public JDatePanel() {
        super();
        initialize();
    }

    /**
     * Sets the font size.
     *
     * @param size the font size
     */
    public void setFontSize(float size) {
        Font font = getFont().deriveFont(size);
        getMonthLabel().setFont(font);
        getTodayLabel().setFont(font);
        getYearSpinner().setFont(font);
        dayTable.setFont(font);
        dayTable.setRowHeight((int)(size * 2));
    }

    /**
     * The actionListener is notified when a user clicks on a date
     *
     * @param arg
     */
    public void addActionListener(ActionListener arg) {
        getActionListeners().add(arg);
    }

    /**
     * Change listeners will be notified when the selected date is changed
     *
     * @param arg
     */
    public void addChangeListener(ChangeListener arg) {
        getChangeListeners().add(arg);
    }

    /**
     * called internally when actionListeners should be notified
     */
    private void fireActionPerformed() {
        Iterator i = getActionListeners().iterator();
        while (i.hasNext()) {
            ActionListener al = (ActionListener)i.next();
            al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "date selected"));
        }
    }

    private ArrayList getActionListeners() {
        if (actionListeners == null) {
            actionListeners = new ArrayList();
        }
        return actionListeners;
    }

    /**
     * Returns a clone of the Calender object holding the date internally
     *
     * @return Calendar
     */
    public Calendar getCalendarClone() {
        return getCalenderModel().getCalendarClone();
    }

    private GregorianCalendarModel getCalenderModel() {
        if (calendarModel == null) {
            calendarModel = new GregorianCalendarModel();
            calendarModel.addChangeListener(eventHandler);
        }
        return calendarModel;
    }

    /**
     * This method initializes centerPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new javax.swing.JPanel();
            centerPanel.setLayout(new java.awt.BorderLayout());
            centerPanel.setOpaque(false);
            centerPanel.add(getDayTableHeader(), BorderLayout.NORTH);
            centerPanel.add(getDayTable(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private Vector getChangeListeners() {
        if (changeListeners == null) {
            changeListeners = new Vector();
        }
        return changeListeners;
    }

    /**
     * This method initializes dayTable
     *
     * @return javax.swing.JTable
     */
    private JTable getDayTable() {
        if (dayTable == null) {
            dayTable = new JTable();
            dayTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
            dayTable.setPreferredSize(new java.awt.Dimension(100, 80));
            dayTable.setModel(getCalenderModel());
            dayTable.setShowGrid(true);
            dayTable.setGridColor(Color.WHITE);
            dayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            dayTable.setCellSelectionEnabled(true);
            dayTable.setRowSelectionAllowed(true);
            dayTable.setFocusable(false);
            dayTable.addMouseListener(getEventHandler());
            TableColumn column = null;
            for (int i = 0; i < 7; i++) {
                column = dayTable.getColumnModel().getColumn(i);
                column.setPreferredWidth(15);
                column.setCellRenderer(getDayTableCellRenderer());
            }
        }
        return dayTable;
    }

    private CalendarTableCellRenderer getDayTableCellRenderer() {
        if (dayTableCellRenderer == null) {
            dayTableCellRenderer = new CalendarTableCellRenderer();
        }
        return dayTableCellRenderer;
    }

    private JTableHeader getDayTableHeader() {
        if (dayTableHeader == null) {
            dayTableHeader = getDayTable().getTableHeader();
            dayTableHeader.setResizingAllowed(false);
            dayTableHeader.setReorderingAllowed(false);
            dayTableHeader.setDefaultRenderer(getDayTableCellRenderer());
        }
        return dayTableHeader;
    }

    private EventHandler getEventHandler() {
        if (eventHandler == null) {
            eventHandler = new EventHandler();
        }
        return eventHandler;
    }

    /**
     * This method initializes monthLabel
     *
     * @return javax.swing.JLabel
     */
    private JLabel getMonthLabel() {
        if (monthLabel == null) {
            monthLabel = new JLabel();
            monthLabel.setForeground(SystemColor.activeCaptionText);
            monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
            monthLabel.setText("uninitialised");
            monthLabel.addMouseListener(getEventHandler());
            getCalenderModel().addMonthLabel(monthLabel);
        }
        return monthLabel;
    }

    /**
     * This method initializes monthPopupMenu
     *
     * @return javax.swing.JPopupMenu
     */
    private JPopupMenu getMonthPopupMenu() {
        if (monthPopupMenu == null) {
            monthPopupMenu = new JPopupMenu();
            JMenuItem[] menuItems = getMonthPopupMenuItems();
            for (int i = 0; i < menuItems.length; i++) {
                monthPopupMenu.add(menuItems[i]);
            }
        }
        return monthPopupMenu;
    }

    private JMenuItem[] getMonthPopupMenuItems() {
        if (monthPopupMenuItems == null) {
            DateFormatSymbols df = new DateFormatSymbols(DEFAULT_LOCALE);
            String[] months = df.getMonths();
            monthPopupMenuItems = new JMenuItem[months.length - 1];
            for (int i = 0; i < months.length - 1; i++) {
                JMenuItem mi = new JMenuItem(months[i]);
                mi.addActionListener(eventHandler);
                monthPopupMenuItems[i] = mi;
            }
        }
        return monthPopupMenuItems;
    }

    /**
     * This method initializes nextMonthButton
     *
     * @return javax.swing.JButton
     */
    private javax.swing.JButton getNextMonthButton() {
        if (nextMonthButton == null) {
            nextMonthButton = new javax.swing.JButton(new JNextIcon(4, 7));
            nextMonthButton.setText("");
            nextMonthButton.setPreferredSize(new java.awt.Dimension(20, 15));
            nextMonthButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            nextMonthButton.setFocusable(false);
            nextMonthButton.addActionListener(getEventHandler());
        }
        return nextMonthButton;
    }

    /**
     * This method initializes northCenterPanel
     *
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getNorthCenterPanel() {
        if (northCenterPanel == null) {
            northCenterPanel = new javax.swing.JPanel();
            northCenterPanel.setLayout(new java.awt.BorderLayout());
            northCenterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
            northCenterPanel.setOpaque(false);
            northCenterPanel.add(getMonthLabel(), java.awt.BorderLayout.CENTER);
            northCenterPanel.add(getYearSpinner(), java.awt.BorderLayout.EAST);
        }
        return northCenterPanel;
    }

    /**
     * This method initializes northPanel
     *
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getNorthPanel() {
        if (northPanel == null) {
            northPanel = new javax.swing.JPanel();
            northPanel.setLayout(new java.awt.BorderLayout());
            northPanel.setName("");
            northPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
            northPanel.setBackground(java.awt.SystemColor.activeCaption);
            northPanel.add(getPreviousMonthButton(), java.awt.BorderLayout.WEST);
            northPanel.add(getNextMonthButton(), java.awt.BorderLayout.EAST);
            northPanel.add(getNorthCenterPanel(), java.awt.BorderLayout.CENTER);
        }
        return northPanel;
    }

    /**
     * This method initializes previousMonthButton
     *
     * @return javax.swing.JButton
     */
    private javax.swing.JButton getPreviousMonthButton() {
        if (previousMonthButton == null) {
            previousMonthButton = new javax.swing.JButton(new JPreviousIcon(4, 7));
            previousMonthButton.setText("");
            previousMonthButton.setPreferredSize(new java.awt.Dimension(20, 15));
            previousMonthButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            previousMonthButton.setFocusable(false);
            previousMonthButton.addActionListener(getEventHandler());
        }
        return previousMonthButton;
    }

    /**
     * This method initializes southPanel
     *
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getSouthPanel() {
        if (southPanel == null) {
            southPanel = new javax.swing.JPanel();
            southPanel.setLayout(new java.awt.BorderLayout());
            //southPanel.setOpaque(false);
            southPanel.setBackground(Color.WHITE);
            southPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
            southPanel.add(getTodayLabel(), java.awt.BorderLayout.CENTER);
        }
        return southPanel;
    }

    /**
     * This method initializes todayLabel
     *
     * @return javax.swing.JLabel
     */
    private JLabel getTodayLabel() {
        if (todayLabel == null) {
            todayLabel = new javax.swing.JLabel();
            todayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            todayLabel.setText("uninitialised");
            todayLabel.addMouseListener(getEventHandler());
            getCalenderModel().addTodayLabel(todayLabel);
        }
        return todayLabel;
    }

    /**
     * This method initializes yearSpinner
     *
     * @return javax.swing.JSpinner
     */
    private javax.swing.JSpinner getYearSpinner() {
        if (yearSpinner == null) {
            yearSpinner = new javax.swing.JSpinner();
            yearSpinner.setModel(getCalenderModel());
        }
        return yearSpinner;
    }

    /**
     * This method initializes this
     */
    public void initialize() {
        this.setLayout(new java.awt.BorderLayout());
        this.setSize(200, 160);
        this.setPreferredSize(new java.awt.Dimension(200, 160));
        this.setBackground(java.awt.SystemColor.activeCaptionText);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
        this.setOpaque(false);
        this.add(getNorthPanel(), java.awt.BorderLayout.NORTH);
        this.add(getSouthPanel(), java.awt.BorderLayout.SOUTH);
        this.add(getCenterPanel(), java.awt.BorderLayout.CENTER);
    }

    /**
     * removes the actionListener
     *
     * @param arg
     */
    public void removeActionListener(ActionListener arg) {
        getActionListeners().remove(arg);
    }

    /**
     * removes the specified change listener
     *
     * @param arg
     */
    public void removeChangeListener(ChangeListener arg) {
        getChangeListeners().remove(arg);
    }

    /**
     * Prints out the selectedDate in long format
     */
    public String toString() {
        DateFormat df = DateFormat.getDateInstance(dateFormat, DEFAULT_LOCALE);
        return df.format(getDate());
    }

    /**
     * Sets the start date.
     *
     * @param date
     */
    public void setStartDate(Date date) {
        getCalenderModel().setCalendar(date);
    }

    /**
     * Sets the date format.
     *
     * @param format the date format
     * @see java.text.DateFormat#MEDIUM
     * @see java.text.DateFormat#LONG
     * @see java.text.DateFormat#SHORT
     */
    public void setDateFormat(int format) {
        dateFormat = format;
    }

    /**
     * Gets true if a given day is SATURDAY or SUNDAY.
     *
     * @param day a some day of month
     * @return true if a given day is SATURDAY or SUNDAY, false otherwise
     */
    protected boolean isWeekend(int day) {
        Calendar cal = calendarModel.getCalendarClone();
        cal.set(Calendar.DAY_OF_MONTH, day);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY);
    }

    /**
     * Sets a first day of a week.
     *
     * @param firstDay a first day of a week
     * @see Calendar#MONDAY
     */
    public void setFirstDayOfWeek(int firstDay) {
        firstDayOfWeek = firstDay;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public Date getDate() {
        return getCalendarClone().getTime();
    }

    protected class JNextIcon implements Icon {

        protected int width;
        protected int height;

        protected int[] xPoints = new int[3];
        protected int[] yPoints = new int[3];

        public JNextIcon(int width, int height) {
            setDimension(width, height);
        }

        public int getIconWidth() {
            return width;
        }

        public int getIconHeight() {
            return height;
        }

        public void setDimension(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            xPoints[0] = x + width;
            yPoints[0] = y + (height / 2);

            xPoints[1] = x;
            yPoints[1] = y - 1;

            xPoints[2] = x;
            yPoints[2] = y + height;

            g.setColor(Color.BLACK);
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }

    protected class JPreviousIcon implements Icon {
        protected int width;
        protected int height;

        protected int[] xPoints = new int[3];
        protected int[] yPoints = new int[3];

        public JPreviousIcon(int width, int height) {
            setDimension(width, height);
        }

        public int getIconWidth() {
            return width;
        }

        public int getIconHeight() {
            return height;
        }

        public void setDimension(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            xPoints[0] = x;
            yPoints[0] = y + (height / 2);

            xPoints[1] = x + width;
            yPoints[1] = y - 1;

            xPoints[2] = x + width;
            yPoints[2] = y + height;

            g.setColor(Color.BLACK);
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }


}
