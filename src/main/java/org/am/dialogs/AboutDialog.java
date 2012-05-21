package org.am.dialogs;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.am.utils.*;

/**
 * The About dialog.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.5 $
 */
public class AboutDialog extends BaseDialog {
  protected static final String TIP_EMAIL = "Click to open the default E-Mail client";
  protected static final String TIP_HTTP = "Click to open this Internet address";

  /**
   * Stores an icon.
   */
  protected JLabel lIcon;
  /**
   * Stores an icon.
   */
  protected JLabel lProjectName;
  /**
   * Stores a version.
   */
  protected JLabel lVersion;
  /**
   * Stores an email.
   */
  protected JTextField tfEmail;
  /**
   * Stores a home page.
   */
  protected JTextField tfHomepage;
  /**
   * Donate label
   */
  protected JLabel lDonate;
  /**
   * Stores a Donate page.
   */
  protected JTextField tfDonate;
  /**
   * Stores the copyrights.
   */
  protected JLabel lCopyRights;

  /**
   * Creates a non-modal dialog without a title with the
   * specified <code>Frame</code> as its owner.  If <code>owner</code>
   * is <code>null</code>, a shared, hidden frame will be set as the
   * owner of the dialog.
   * <p/>
   * This constructor sets the component's locale property to the value
   * returned by <code>JComponent.getDefaultLocale</code>.
   *
   * @param owner the <code>Frame</code> from which the dialog is displayed
   * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
   *                                    returns true.
   * @see java.awt.GraphicsEnvironment#isHeadless
   * @see javax.swing.JComponent#getDefaultLocale
   */
  public AboutDialog(Frame owner) {
    super(owner, EMPTY, true);
  }

  /**
   * Inits the GUI.
   */
  protected void initGUI() {
    setResizable(false);
    setCancelVisible(false);

    MouseHandler mouseHandler = new MouseHandler();

    lIcon = new JLabel();
    lProjectName = new JLabel(NAME);
    lProjectName.setHorizontalAlignment(SwingConstants.CENTER);
    lProjectName.setFont(lProjectName.getFont().deriveFont((float)25.0));
    lVersion = new JLabel(VERSION);

    JLabel lEmail = new JLabel(EMAIL);
    tfEmail = new JTextField();
    tfEmail.setEditable(false);
    tfEmail.setCursor(CURSOR_HAND);
    tfEmail.setForeground(Color.BLUE);
    tfEmail.setToolTipText(TIP_EMAIL);
    tfEmail.addMouseListener(mouseHandler);

    JLabel lHomepage = new JLabel(HOMEPAGE);
    tfHomepage = new JTextField();
    tfHomepage.setEditable(false);
    tfHomepage.setCursor(CURSOR_HAND);
    tfHomepage.setForeground(Color.BLUE);
    tfHomepage.setToolTipText(TIP_HTTP);
    tfHomepage.addMouseListener(mouseHandler);

    lDonate = new JLabel(DONATE);
    tfDonate = new JTextField();
    tfDonate.setEditable(false);
    tfDonate.setCursor(CURSOR_HAND);
    tfDonate.setForeground(Color.BLUE);
    tfDonate.setToolTipText(TIP_HTTP);
    tfDonate.addMouseListener(mouseHandler);

    lCopyRights = new JLabel(COPYRIGHTS);

    pane.add(lIcon, new GridBagConstraints(0, 0, 1, 6, 0.0, 0.0,
        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 10), 0, 0));
    pane.add(lProjectName, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(15, 120, 5, 120), 0, 0));
    pane.add(lVersion, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    pane.add(lEmail, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
        GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pane.add(tfEmail, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 15), 0, 0));

    pane.add(lHomepage, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
        GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pane.add(tfHomepage, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 15), 0, 0));

    pane.add(lDonate, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
        GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pane.add(tfDonate, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 15), 0, 0));

    pane.add(lCopyRights, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 30, 15), 0, 0));

//    pane.setPreferredSize(new Dimension(640, 480));

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
  }

  /**
   * Gets the dialog data.
   *
   * @return the dialog data
   */
  public Object getData() {
    return null;
  }

  /**
   * Sets a project version.
   *
   * @param version a project version
   */
  public void setVersion(String version) {
    lVersion.setText(version);
    pack();
  }

  /**
   * Seta a project icon.
   *
   * @param icon a project icon
   */
  public void setIcon(Icon icon) {
    lIcon.setIcon(icon);
    pack();
  }

  /**
   * Sets a project name.
   *
   * @param name a project name
   */
  public void setProjectName(String name) {
    lProjectName.setText(name);
    pack();
  }

  /**
   * Sets an email.
   *
   * @param email an eamil
   */
  public void setEmail(String email) {
    tfEmail.setText(email);
    pack();
  }

  /**
   * Sets a Donate URL.
   *
   * @param donateUrl a Donate URL
   */
  public void setDonate(String donateUrl) {
    if (donateUrl != null) {
      lDonate.setVisible(true);
      tfDonate.setVisible(true);
      tfDonate.setText(donateUrl);
    } else {
      lDonate.setVisible(false);
      tfDonate.setVisible(false);
    }
    pack();
  }

  /**
   * Sets a home page.
   *
   * @param homepage a home page
   */
  public void setHomepage(String homepage) {
    tfHomepage.setText(homepage);
    pack();
  }

  /**
   * Sets a project copyrights.
   *
   * @param copyRights a project copyrights
   */
  public void setCopyRights(String copyRights) {
    lCopyRights.setText(copyRights);
    pack();
  }

  protected void runBrowser(String url) {
    BrowserLauncher browserLauncher = new BrowserLauncher(url);
    try {
      browserLauncher.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This class handles mouse evnets.
   */
  protected class MouseHandler extends MouseAdapter {
    /**
     * Invoked when the mouse has been clicked on a component.
     */
    public void mouseClicked(MouseEvent e) {
      Object src = e.getSource();

      if (src.equals(tfEmail)) {
        String url = tfEmail.getText();
        if (url.indexOf("mailto://") == -1) {
          url = "mailto:" + url;
        }
        runBrowser(url);
      } else if (src.equals(tfHomepage)) {
        String url = tfHomepage.getText();
        if (url.indexOf("http://") == -1) {
          url = "http://" + url;
        }
        runBrowser(url);
      } else if (src.equals(tfDonate)) {
        String url = tfDonate.getText();
        if (url.indexOf("http://") == -1) {
          url = "http://" + url;
        }
        runBrowser(url);
      }
    }
  }
}