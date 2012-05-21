package org.am.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Juan Heyns
 *         <p/>
 *         Created on 25-Mar-2004
 *         Refactored 21-Jun-2004
 */
public class JDatePicker extends JPanel {

    /**
     * Tests the JDatePicker
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame testFrame = new JFrame();
        testFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                System.exit(0);
            }
        });
        testFrame.setSize(600, 300);
        JPanel jPanel = new JPanel();
        jPanel.add(new JDatePicker());
        testFrame.getContentPane().add(jPanel);
        testFrame.setVisible(true);
    }

    private Popup popup;
    private JTextField tfDate;
    private JButton bShowDatePanel;
    private JDatePanel datePanel;

    /**
     * Constructs a JDatePicker
     */
    public JDatePicker() {
        this(new Date(), DateFormat.LONG);
    }

    public JDatePicker(Date date, int dateFormat) {
        setLayout(new GridBagLayout());
        EventHandler eventHandler = new EventHandler();

        this.addHierarchyBoundsListener(eventHandler);

        datePanel = new JDatePanel();
        datePanel.addChangeListener(eventHandler);
        datePanel.addActionListener(eventHandler);
        datePanel.setStartDate(date);
        datePanel.setDateFormat(dateFormat);

        tfDate = new JTextField(datePanel.toString());
        tfDate.setEditable(false);
        tfDate.setBackground(UIManager.getColor("TextField.background"));
        tfDate.setHorizontalAlignment(JTextField.CENTER);

        bShowDatePanel = new JButton("...");
        bShowDatePanel.addActionListener(eventHandler);
        bShowDatePanel.setMargin(new Insets(0, 0, 0, 0));
        bShowDatePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        int height = tfDate.getPreferredSize().height;
        int width = height;
        bShowDatePanel.setPreferredSize(new Dimension(width, height + 1));

        add(tfDate, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        add(bShowDatePanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(Box.createGlue(), new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                        GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * Prints out the date in the format of the datePanel.toString()
     */
    public String toString() {
        return datePanel.toString();
    }

    /**
     * Sets the date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        datePanel.setStartDate(date);
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public Date getDate() {
        return datePanel.getDate();
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
        datePanel.setDateFormat(format);
    }

    /**
     * Called internally to popup the dates.
     */
    private void showPopup() {
        if (popup == null) {
            PopupFactory fac = new PopupFactory();
            Point xy = getLocationOnScreen();
            datePanel.setVisible(true);
            popup = fac.getPopup(this, datePanel, (int)xy.getX(), (int)(xy.getY() + this.getHeight() - 1));
            popup.show();
        }
    }

    /**
     * Called internally to hide the popup dates.
     */
    private void hidePopup() {
        if (popup != null) {
            tfDate.setText(datePanel.toString());
            popup.hide();
            popup = null;
        }
    }

    /**
     * Sets whether or not this component is enabled.
     *
     * @param enabled true if this component should be enabled, false otherwise
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            tfDate.setEnabled(true);
            tfDate.setBackground(UIManager.getColor("TextField.background"));
        } else {
            tfDate.setEnabled(false);
        }
        bShowDatePanel.setEnabled(enabled);
    }

    /**
     * Registers the text to display in a tool tip.
     * The text displays when the cursor lingers over the component.
     *
     * @param toolTip the string to display; if the text is null, the tool tip is turned off for this component
     */
    public void setToolTipText(String toolTip) {
        tfDate.setToolTipText(toolTip);
        bShowDatePanel.setToolTipText(toolTip);
    }

    /**
     * This internal class hides the public event methods from the outside
     */
    private class EventHandler implements ActionListener, HierarchyBoundsListener, ChangeListener {

        public void ancestorMoved(HierarchyEvent e) {
            hidePopup();
        }

        public void ancestorResized(HierarchyEvent e) {
            hidePopup();
        }

        public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource().equals(bShowDatePanel)) {
                if (popup == null) {
                    showPopup();
                } else {
                    hidePopup();
                }
            } else if (arg0.getSource().equals(datePanel)) {
                hidePopup();
            }
        }

        public void stateChanged(ChangeEvent arg) {
            if (arg.getSource().equals(datePanel)) {
                if (tfDate != null) {
                    tfDate.setText(datePanel.toString());
                }
            }
        }
    }

}
