// *****************************************************************
//
// $RCSfile: BrowserLauncher.java,v $: The BrowserLauncher.
//
// $Revision: 1.2 $
// *****************************************************************
//
//  Datum       Author              Kommentar
// -----------------------------------------------------------------
//  28.03.2006  Alexander Maximenya Created
// *****************************************************************
package org.am.utils;

import java.io.*;

/**
 * The BrowserLauncher.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class BrowserLauncher {
    public static final int UNKNOW_OS = -1;
    public static final int WINDOWS_XP = 0;
    public static final int WINDOWS_98 = 1;
    public static final int MACOS = 2;
    public static final int UNIX = 3;
    public static final int LINUX = 4;

    public static final String S_UNKNOW_OS = "Unknown OS";
    public static final String S_WINDOWS_XP = "Windows XP";
    public static final String S_WINDOWS_98 = "Windows 98";
    public static final String S_MACOS = "Mac";
    public static final String S_UNIX = "Unix";
    public static final String S_LINUX = "Linux";

    /**
     * Stores the url.
     */
    protected String url;

    /**
     * Creates a new BrowserLauncher object.
     *
     * @param newUrl the url. Starts with protocol name (http://, ftp://)
     */
    public BrowserLauncher(String newUrl) {
        this.url = newUrl;
    }

    /**
     * Runs a browser.
     *
     * @throws IOException an exception
     */
    public void run() throws IOException {
        int os = getOS();

        switch (os) {
            case WINDOWS_XP:
                runWindowsXP();
                break;
            case WINDOWS_98:
                runWindows98();
                break;
            case MACOS:
                runMacos();
                break;
            case UNIX:
                runUnix();
                break;
            case LINUX:
                runLinux();
                break;
            default:
                throw new IOException(S_UNKNOW_OS);
        }
    }

    /**
     * Runs a browser on Windows XP.
     *
     * @throws IOException an exception
     */
    protected void runWindowsXP() throws IOException {
        String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
        Process process = Runtime.getRuntime().exec(cmd);
        try {
            process.waitFor();
            process.exitValue();
        } catch (InterruptedException ie) {
            throw new IOException("InterruptedException while launching browser: " + ie.getMessage());
        }
    }

    /**
     * Runs a browser on Windows 98.
     */
    protected void runWindows98() {
        System.out.println(S_WINDOWS_98);
    }

    /**
     * Runs a browser on Mac OS.
     */
    protected void runMacos() {
        System.out.println(S_MACOS);
    }

    /**
     * Runs a browser on Unix.
     */
    protected void runUnix() {
        System.out.println(S_UNIX);
    }

    /**
     * Runs a browser on Linux.
     */
    protected void runLinux() {
        System.out.println(S_LINUX);
    }

    /**
     * Gets an OS.
     *
     * @return an OS
     */
    protected int getOS() {
        String os = System.getProperty("os.name");
        System.out.println(os);

        if (os.equals(S_WINDOWS_XP)) {
            return WINDOWS_XP;
        }
        if (os.equals(S_WINDOWS_98)) {
            return WINDOWS_98;
        }
        if (os.equals(S_MACOS)) {
            return MACOS;
        }
        if (os.equals(S_UNIX)) {
            return UNIX;
        }

        return 0;
    }

    /**
     * The Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        BrowserLauncher browserLauncher = new BrowserLauncher("http://www.google.com");
        try {
            browserLauncher.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
