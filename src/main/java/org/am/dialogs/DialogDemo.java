package org.am.dialogs;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * The DialogDemo.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.6 $
 */
public class DialogDemo extends JFrame {

    public DialogDemo() {
        setTitle("Dialog Demo");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel pane = (JPanel)getContentPane();
        pane.setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        JButton bPassword = new JButton(new PasswordAction());
        JButton bAbout = new JButton(new AboutAction());
        JButton bUsernamePassword = new JButton(new UsernamePasswordAction());

        toolBar.add(bPassword);
        toolBar.add(bAbout);
        toolBar.add(bUsernamePassword);

        pane.add(toolBar, BorderLayout.NORTH);
        pack();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        DialogDemo demo = new DialogDemo();
//        demo.setSize(200, 200);
        demo.setLocationRelativeTo(null);
        demo.setVisible(true);
    }

    protected class PasswordAction extends AbstractAction {
        /**
         * Defines an <code>Action</code> object with the specified
         * description string and a default icon.
         */
        public PasswordAction() {
            super("PasswordDialog");
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            PasswordDialog dialog = new PasswordDialog(DialogDemo.this);
            dialog.setVisible(true);
            if (dialog.getCloseOption() == JOptionPane.OK_OPTION) {
                System.out.println("OK=" + dialog.getData());
            } else {
                System.out.println("Cancel=" + dialog.getData());
            }
        }
    }

    protected class AboutAction extends AbstractAction {
        /**
         * Defines an <code>Action</code> object with the specified
         * description string and a default icon.
         */
        public AboutAction() {
            super("AboutDialog");
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            AboutDialog dialog = new AboutDialog(DialogDemo.this);
            dialog.setEmail("a.maximenya@gmail.com");
            dialog.setHomepage("http://vcsreport.sf.net");
//            dialog.setDonate("http://store.kagi.com/?6FCUM_LIVE&lang=en");
//            dialog.setDonate(null);
//          dialog.setPreferredSize(new Dimension(640, 480));

            dialog.setVisible(true);
            if (dialog.getCloseOption() == JOptionPane.OK_OPTION) {
                System.out.println("OK=" + dialog.getData());
            } else {
                System.out.println("Cancel");
            }
        }
    }

    protected class UsernamePasswordAction extends AbstractAction {
        /**
         * Defines an <code>Action</code> object with the specified
         * description string and a default icon.
         */
        public UsernamePasswordAction() {
            super("UsernamePassword");
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            String[] data = new String[2];
            data[0] = "u1";
            data[1] = "p1";

            UsernamePasswordDialog dialog = new UsernamePasswordDialog(DialogDemo.this);
            dialog.setData(data);
            dialog.setVisible(true);
            if (dialog.getCloseOption() == JOptionPane.OK_OPTION) {
                data = (String[])dialog.getData();
                System.out.println("OK=username=" + data[0]);
                System.out.println("OK=password=" + data[1]);
            } else {
                System.out.println("Cancel");
            }
        }
    }
}
