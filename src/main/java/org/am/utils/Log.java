package org.am.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Logger.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class Log {
    public static final byte SILENT = -1;
    public static final byte INFO = 0;
    public static final byte DEBUG = 1;

    protected static final String INFO_STR = " INFO ";
    protected static final String ERROR_STR = " ERROR ";
    protected static final String DEBUG_STR = " DEBUG ";
    protected static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN_DATE_TIME);
    protected static final String START_BRACE = "[";
    protected static final String END_BRACE = "]";

    protected static final String LINE_DELIM = System.getProperty("line.separator");

    protected static byte level = INFO;
    protected static String logFile;
    protected static boolean showTime = true;

    /**
     * Sets the debug level.
     *
     * @param newLevel the debug level
     */
    public static final void setLevel(byte newLevel) {
        level = newLevel;
    }

    /**
     * Sets the simple mode.
     * The simple mode is mode without the start info.
     *
     * @param newShowTime the simple mode
     */
    public static final void showTime(boolean newShowTime) {
        showTime = newShowTime;
    }

    /**
     * Sets the log file.
     *
     * @param newLogFile the log file
     */
    public static final void setFile(String newLogFile) {
        logFile = newLogFile;
    }

    /**
     * Put info message without system info to the log.
     *
     * @param text some message text
     */
    public static void message(String text) {
        message(text, true);
    }
    /**
     * Put info message without system info to the log.
     *
     * @param text some message text
     */
    /**
     *  Put info message without system info to the log.
     *
     * @param text      some message text
     * @param lineBreak if true then add line break
     */
    public static void message(String text, boolean lineBreak) {
        info(text, lineBreak, true);
    }

    /**
     * Put info message to the log.
     *
     * @param text some info text
     */
    public static void info(String text) {
        info(text, true, false);
    }

    /**
     *  Put info message to the log.
     *
     * @param text      some info text
     * @param lineBreak if true then add a line break
     */
    public static void info(String text, boolean lineBreak) {
        info(text, lineBreak, false);
    }

    /**
     * Put info message to the log.
     *
     * @param text      some info text
     * @param lineBreak if true then add line break
     */
    /**
     *  Put info message to the log.
     *
     * @param text        some info text
     * @param lineBreak   if true then add a line break
     * @param justMessage if true then doesn't add a system info
     */
    public static void info(String text, boolean lineBreak, boolean justMessage) {
        if (level == SILENT) {
            return;
        }

        String str;
        if (!justMessage) {
            if (showTime) {
                str = getDate() + INFO_STR + text;
            }  else {
                str = text;
            }
        } else {
            str = text;
        }

        if (lineBreak)  {
            str = str + LINE_DELIM;
        }

        if (logFile != null) {
            try {
                writeToLogFile(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print(str);
        }
    }

    /**
     * Put the error message to the log.
     *
     * @param ex some exception
     */
    public static void error(Exception ex, boolean printStackTrace) {
        if (level == SILENT) {
            return;
        }

        String err;
        if (showTime) {
            err = ex.toString();
        }  else {
            err = getDate() + ERROR_STR + ex.toString();;
        }
        if (logFile != null) {
            try {
                writeToLogFile(err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(err);
        }
        if (printStackTrace) {
            ex.printStackTrace();
        }
    }

    /**
     * Put the error message to the log.
     *
     * @param ex some exception
     */
    public static void error(Exception ex) {
        error(ex, false);
    }

    /**
     * Put the error message to the log.
     *
     * @param message some message
     */
    public static void error(String message) {
        error(new Exception(message), false);
    }

    /**
     * Put the debug message to the log.
     *
     * @param text some debug text
     */
    public static void debug(String text) {
        if (level == SILENT) {
            return;
        }

        String debug;
        if (showTime) {
            debug = text;
        }  else {
            debug = getDate() + DEBUG_STR + text;
        }
        if (level == DEBUG) {
            if (logFile != null) {
                try {
                    writeToLogFile(debug);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(debug);
            }
        }
    }

    /**
     * Deletes the log file.
     *
     * @return true if and only if the file or directory is successfully deleted; false otherwise
     */
    public static boolean deleteLogFile() {
        if (logFile != null) {
            File file = new File(logFile);
            if (file.exists()) {
                return file.delete();
            }
        }
        return false;
    }

    /**
     * Writes some text to the log file.
     *
     * @param text         some text
     * @throws IOException an exception
     */
    protected static void writeToLogFile(String text) throws IOException {
        if (logFile != null) {
            File file = new File(logFile);
            if (!file.exists() && file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(logFile, true);
                fos.write(text.getBytes());
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

    /**
     * Gets the current date string.
     *
     * @return  the current date string
     */
    protected static String getDate() {
//        return START_BRACE + df.format(new Date()) + END_BRACE;
        return DATE_FORMAT.format(new Date());
    }
}
