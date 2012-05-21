package org.am.dialogs;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * The base dialog for all dialogs.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.3 $
 */
public abstract class BaseDialog extends JDialog implements DialogConstants {
    /**
     * The button OK.
     */
    protected JButton bOk;
    /**
     * The button Cancel.
     */
    protected JButton bCancel;
    /**
     * Stores the dialog close option.
     */
    protected int closeOption = JOptionPane.CANCEL_OPTION;
    /**
     * The main dialog pane.
     * All children add their components to this pane.
     */
    protected JPanel pane;

    /**
     * Creates a new BaseDialog object.
     */
    public BaseDialog() {
        this(null);
    }

    /**
     * Creates a new BaseDialog object.
     *
     * @param owner the parent
     */
    public BaseDialog(Frame owner) {
        this(owner, EMPTY, true);
    }

    /**
     * Creates a new BaseDialog object.
     *
     * @param owner              the parent
     * @param title              the title
     * @param modal              modal flag
     */
    public BaseDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); don't use it in JDK 1.6 and above
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new GridBagLayout());

        OkAction okAction = new OkAction(KEY_OK);
        CancelAction cancelAction = new CancelAction(KEY_CANCEL);

        //Default actions
        ActionMap actions = contentPane.getActionMap();
        InputMap inputs = contentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        actions.put(KEY_OK, okAction);
        actions.put(KEY_CANCEL, cancelAction);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KEY_OK);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), KEY_CANCEL);

        pane = new JPanel(new GridBagLayout());

        bOk = new JButton(okAction);
        bOk.setCursor(CURSOR_HAND);
        bCancel = new JButton(cancelAction);
        bCancel.setCursor(CURSOR_HAND);
        bOk.setPreferredSize(bCancel.getPreferredSize());

        JSeparator bootomSeparator = new JSeparator();
        JPanel pBottomButtons = new JPanel();
        pBottomButtons.add(bOk);
        pBottomButtons.add(bCancel);

        contentPane.add(pane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        contentPane.add(bootomSeparator, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0));
        contentPane.add(pBottomButtons, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        initGUI();
        pack();
        setLocationRelativeTo(owner);
    }
    /**
     * Inits the GUI.
     */
    protected abstract void initGUI();

    /**
     * Checks a dialog data.
     *
     * @return an error message or null if the dialog doesn't have errors
     */
    protected abstract String checkData();

    /**
     * Sets the dialog data.
     *
     * @param data the dialog data
     */
    public abstract void setData(Object data);
    /**
     * Gets the dialog data.
     *
     * @return the dialog data
     */
    public abstract Object getData();
    /**
     * The button "OK" was pressed.
     */
    protected void okPressed() {
        String errors = checkData();
        if (errors == null) {
            closeOption = JOptionPane.OK_OPTION;
            hideDialog();
        } else {
             JOptionPane.showMessageDialog(this, errors);
        }
    }
    /**
     * The button "Cancel" was pressed.
     */
    protected void cancelPressed() {
        closeOption = JOptionPane.CANCEL_OPTION;
        hideDialog();
    }

    /**
     * Hides the dialog.
     */
    protected void hideDialog() {
        setVisible(false);
        dispose();
    }
    /**
     * Gets the dialog close option.
     *
     * @return he dialog close option
     * @see JOptionPane#OK_OPTION
     * @see JOptionPane#CANCEL_OPTION
     */
    public int getCloseOption() {
        return closeOption;
    }

    public void setCancelVisible(boolean visible) {
        bCancel.setVisible(visible);
    }

    /**
     * Implements an action Cancel.
     */
    protected class OkAction extends AbstractAction {
        /**
         * Defines an <code>Action</code> object with the specified
         * description string and a default icon.
         */
        public OkAction(String name) {
            super(name);
        }

        /**
         * Invoked when an action occurs.
         *
         * @param event  event
         */
        public void actionPerformed(ActionEvent event) {
            okPressed();
        }
    }

    /**
     * Implements an action Cancel.
     */
    protected class CancelAction extends AbstractAction {
        /**
         * Defines an <code>Action</code> object with the specified
         * description string and a default icon.
         */
        public CancelAction(String name) {
            super(name);
        }

        /**
         * Invoked when an action occurs.
         *
         * @param event  event
         */
        public void actionPerformed(ActionEvent event) {
            cancelPressed();
        }
    }
}