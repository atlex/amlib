package org.am.dialogs;

import java.awt.*;

/**
 * The DialogConstants.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.3 $
 */
public interface DialogConstants {
    String KEY_OK = "OK";
    String KEY_CANCEL = "Cancel";
    String EMPTY = "";
    String SPACE = " ";
    String COMMAS = "\"";

    //PasswordDialog
    String TITLE_PASSWORD = "Please enter the password";
    String PASSWORD_FOR = "Password for:";

    //AboutDialog
    String NAME = "Name";
    String VERSION = "Version 1.0";
    String EMAIL = "E-Mail:";
    String HOMEPAGE = "Home page:";
    String DONATE = "Donate:";
    String COPYRIGHTS = "Copyright (c) 2006";

    String PROMPT = "Prompt";
    String CONNECT_TO = "Connect to";
    String USERNAME = "User name:";
    String PASSSWORD = "Password:";

    Cursor CURSOR_HAND = new Cursor(Cursor.HAND_CURSOR);
}
