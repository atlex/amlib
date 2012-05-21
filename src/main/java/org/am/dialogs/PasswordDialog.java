package org.am.dialogs;

import java.awt.*;

import javax.swing.*;

/**
 * This class represents the password dialog.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.3 $
 */
public class PasswordDialog extends BaseDialog {
    /**
     * Stores the top text.
     */
    protected JLabel lText;
    /**
     * Helps to enter the password.
     */
    protected JPasswordField tfPassword;

    /**
     * Creates a new PasswordDialog object.
     */
    public PasswordDialog(Frame owner) {
        super(owner, TITLE_PASSWORD, true);
    }

    /**
     * Inits the GUI.
     */
    protected void initGUI() {
        setResizable(false);

        lText = new JLabel(PASSWORD_FOR);
        tfPassword = new JPasswordField();

        pane.add(lText, new GridBagConstraints(0, 0, 3, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        pane.add(tfPassword, new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    }

    /**
     * Checks a dialog data.
     *
     * @return an error message or null if the dialog doesn't have errors
     */
    public String checkData() {
        return null;
    }

    /**
     * Sets the dialog data.
     *
     * @param data the dialog data
     */
    public void setData(Object data) {
        tfPassword.setText((String)data);
    }

    /**
     * Gets the dialog data.
     *
     * @return the dialog data
     */
    public Object getData() {
        if (closeOption == JOptionPane.CANCEL_OPTION) {
            return null;
        }
        if (tfPassword.getPassword().length > 0) {
            return new String(tfPassword.getPassword());
        }
        return null;
    }

    /**
     * Sets the top text.
     *
     * @param text the top text
     */
    public void setText(String text) {
        lText.setText(text);
        pack();
    }

    /**
     * Gets the top text.
     *
     * @return the top text
     */
    public String getText() {
        return lText.getText();
    }
}