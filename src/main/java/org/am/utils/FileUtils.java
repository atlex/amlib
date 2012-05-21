/**
 * Date         Comments
 * ---------------------------------------------
 * 2005-10-06 writeFile was fixed.
 * 2005-09-21 isFileExists was added.
 * 2005-09-19 writeFile and readFile were fixed.
 * 2005-09-12 deleteFile is added.
 * 2005-09-07 StringBuffer is replaced by StingBuilder.
 * 2005-03-18 getExtension is added.
 * 2003-12-23 Changed readFile and writeFile.
 * 2003-11-21 Added getFileName, changed changeFileExt.
 */
package org.am.utils;

import java.io.*;

/**
 * The file utilities class.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class FileUtils {
    /**
     * Charset constant.
     */
    public static final String CHARSET_WIN1251 = "Cp1251";
    /**
     * Charset constant.
     */
    public static final String CHARSET_KOI8 = "KOI8-R";
    /**
     * Charset constant.
     */
    public static final String CHARSET_CP866 = "Cp866";
    /**
     * Charset constant.
     */
    public static final String CHARSET_UTF8 = "utf-8";

    /**
     * Writes the string to the file.
     *
     * @param fileName the name of the file
     * @param string   the string to write
     * @param append   if append is true then the string appends to the end of the file
     * @throws IOException
     */
    public static void writeFile(String fileName, String string, boolean append) throws IOException {
        writeFile(fileName, string, CHARSET_WIN1251, append);
    }

    /**
     * Writes the string to the file.
     *
     * @param fileName    the name of the file
     * @param string      the string to write
     * @param charsetName the name of the charset
     * @param append      if append is true then the string appends to the end of the file
     * @throws IOException
     */
    public static void writeFile(String fileName, String string, String charsetName, boolean append) throws IOException {
        File file = new File(fileName);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName, append);
            fos.write(string.getBytes(charsetName));
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * Reads the file.
     *
     * @param fileName the name of the file
     * @return the content of the file
     * @throws IOException
     */
    public static StringBuffer readFile(String fileName) throws IOException {
        return readFile(fileName, CHARSET_WIN1251);
    }

    /**
     * Reads the file.
     *
     * @param fileName    the name of the file
     * @param charsetName the name of the charset
     * @return the content of the file
     * @throws IOException
     */
    public static StringBuffer readFile(String fileName, String charsetName) throws IOException {
        StringBuffer result = null;
        FileInputStream fis = null;
        byte[] array;

        try {
            fis = new FileInputStream(fileName);
            array = new byte[fis.available()];
            fis.read(array);
            result = new StringBuffer(new String(array, charsetName));
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        return result;
    }

    public static byte[] readFileBytes(String fileName) throws IOException {
        InputStream is = null;
        byte[] bytes;
        try {
            is = new FileInputStream(fileName);
            bytes = new byte[is.available()];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length &&
                    (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                if (is != null) {
                    is.close();
                }
                throw new IOException("Could not completely read file \"" + fileName + "\".");
            }
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return bytes;
    }

//    /**
//     * Reads the file.
//     *
//     * @param fileName     the name of the file
//     * @param charsetName  the name of the charset
//     * @return             the content of the file
//     * @throws IOException
//     */
//    public static String readFile(String fileName, String charsetName) throws IOException {
//        String result = null;
//        FileInputStream fis = new FileInputStream(fileName);
//
//        byte[] array = new byte[fis.available()];
//        fis.read(array);
//        result = new String(array, charsetName);
//        fis.close();
//
//        return result;
//    }

    /**
     * Changes the extansion of the file.
     *
     * @param oldFileName the old name of the file
     * @param newExt      the new extension of the file
     * @return the new name of the file
     */
    public static String changeFileExt(String oldFileName, String newExt) {
        return oldFileName.substring(0, oldFileName.lastIndexOf(".") + 1) + newExt;
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Gets the file's name without full path.
     *
     * @param fullPath the full path to file
     * @return the file's name without full path
     */
    public static String getFileName(String fullPath) {
        return getFileName(fullPath, true);
    }

    /**
     * Gets the file's name without full path.
     *
     * @param fullPath the full path to file
     * @return the file's name without full path
     */
    public static String getFileName(String fullPath, boolean withExtension) {
        String fileName;
        int index;

        index = fullPath.lastIndexOf(File.separatorChar) + 1;
        if (index == -1) {
            fileName = fullPath;
        } else {
            fileName = fullPath.substring(index);
        }

        //extension
        if (!withExtension) {
            index = fileName.lastIndexOf(".");
            if (index != -1) {
                fileName = fileName.substring(0, index);
            }
        }

        return fileName;
    }

    /**
     * Gets file directory.
     *
     * @param fullPath full file path
     * @return file directory
     */
    public static String getFileDir(String fullPath) {
        if (isDirectory(fullPath)) {
            return fullPath;
        }

        String fileDir;
        int index;

        index = fullPath.lastIndexOf(File.separatorChar) + 1;
        if (index == -1) {
            fileDir = fullPath;
        } else {
            fileDir = fullPath.substring(0, index);
        }

        return fileDir;
    }

    /**
     * Deletes some file.
     *
     * @param filePath some file
     * @return true if deleted, false otherwise
     * @throws SecurityException an exception
     */
    public static boolean deleteFile(String filePath) throws SecurityException {
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * Tests whether the file or directory denoted by this abstract pathname exists.
     *
     * @param filePath some file
     * @return true if and only if the file or directory denoted by this abstract pathname exists; false otherwise
     */
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Tests whether the file denoted by this abstract pathname is a directory.
     *
     * @param filePath a file path
     * @return true if and only if the file denoted by this abstract pathname exists and is a directory; false otherwise
     */
    public static boolean isDirectory(String filePath) {
        return new File(filePath).isDirectory();
    }

    /**
     * Corrects a file path. Changes a wrong file separator.
     * Changes 'd:/work/projects' to 'd:\work\projects' on Windows.
     *
     * @param filePath a wrong file path
     * @return a right file path
     */
    public static String correctFilePath(String filePath) {
        char win = '\\';
        char lin = '/';
        char rightSep;
        char wrongSep;

        if (File.separatorChar == win) {
            rightSep = win;
            wrongSep = lin;
        } else {
            rightSep = lin;
            wrongSep = win;
        }

        return filePath.replace(wrongSep, rightSep);
    }

    public static File[] getFiles(String path, FileFilter fileFilter) {
        File file = new File(path);
        if (file.isFile()) {
            return new File[]{file};
        } else {
            return file.listFiles(fileFilter);
        }
    }
     
    /**
     * This class represents Java file filter.
     */
    public static class JaveFileFilter extends javax.swing.filechooser.FileFilter {
        /**
         * Whether the given file is accepted by this filter.
         */
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            return FileUtils.getExtension(f.getName()).equals("java");
        }

        /**
         * The description of this filter.
         *
         * @see javax.swing.filechooser.FileView#getName
         */
        public String getDescription() {
            return "Java files (*.java)";
        }
    }

    /**
     * This class represents HTML file filter.
     */
    public static class HtmlFileFilter extends javax.swing.filechooser.FileFilter {
        /**
         * Whether the given file is accepted by this filter.
         */
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            return FileUtils.getExtension(f.getName()).equals("htm") || FileUtils.getExtension(f.getName()).equals("html");
        }

        /**
         * The description of this filter.
         *
         * @see javax.swing.filechooser.FileView#getName
         */
        public String getDescription() {
            return "HTML files (*.htm, *.html)";
        }
    }

    /**
     * This class represents HTML file filter.
     */
    public static class CsvFileFilter extends javax.swing.filechooser.FileFilter {
        /**
         * Whether the given file is accepted by this filter.
         */
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            return FileUtils.getExtension(f.getName()).equals("csv");
        }

        /**
         * The description of this filter.
         *
         * @see javax.swing.filechooser.FileView#getName
         */
        public String getDescription() {
            return "CSV files (*.csv)";
        }
    }

}
