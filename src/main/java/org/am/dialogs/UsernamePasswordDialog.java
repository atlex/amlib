package org.am.dialogs;

import java.awt.*;

import javax.swing.*;

/**
 * The UsernamePasswordDialog.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.4 $
 */
public class UsernamePasswordDialog extends BaseDialog {
    /**
     * Stores the top text.
     */
    protected JLabel lTopText;
    /**
     * Helps to enter the username.
     */
    protected JComboBox cbUsername;
    /**
     * Helps to enter the password.
     */
    protected JPasswordField tfPassword;

    /**
     * Creates a new PasswordDialog object.
     */
    public UsernamePasswordDialog(Frame owner) {
        super(owner, PROMPT, true);
    }

    /**
     * Inits the GUI.
     */
    protected void initGUI() {
        setResizable(false);

        lTopText = new JLabel(CONNECT_TO);
        JLabel lUsername = new JLabel(USERNAME);
        cbUsername = new JComboBox();
        cbUsername.setEditable(true);

        JLabel lPassword = new JLabel(PASSSWORD);
        tfPassword = new JPasswordField();

        int margin = 15;

        pane.add(lTopText, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, margin, 5, margin), 0, 0));
        pane.add(lUsername, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, margin, 5, 5), 0, 0));
        pane.add(cbUsername, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, margin), 0, 0));
        pane.add(lPassword, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, margin, margin, 5), 0, 0));
        pane.add(tfPassword, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, margin, margin), 0, 0));
        pane.add(Box.createGlue(), new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0,
                        GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
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
     * Sets th dialog data.
     *
     * @param data the dialog data
     */
    public void setData(Object data) {
        String[] usernamePassword = (String[])data;
        cbUsername.setSelectedItem(usernamePassword[0]);
        tfPassword.setText(usernamePassword[1]);
    }

    /**
     * Gets th dialog data.
     *
     * @return th dialog data
     */
    public Object getData() {
        String[] usernamePassword = new String[2];
        usernamePassword[0] = (String)cbUsername.getSelectedItem();
        usernamePassword[1] = new String(tfPassword.getPassword());
        return usernamePassword;
    }

    public void setTopText(String text) {
        lTopText.setText(CONNECT_TO + SPACE + COMMAS + text + COMMAS);
        pack();
    }
}
