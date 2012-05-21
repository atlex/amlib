package org.am.utils;

import java.io.*;
import java.net.*;
import java.util.*;

import sun.misc.*;

/**
 * The Ripper.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class Spider {
    public static final String KEY_PROXY_SET = "proxySet";
    public static final String KEY_PROXY_HOST = "http.proxyHost";
    public static final String KEY_PROXY_PORT = "http.proxyPort";
    public static final String KEY_PROXY_USERNAME = "http.proxyUsername";
    public static final String KEY_PROXY_PASSWORD = "http.proxyPassword";

    public static final int DEFAULT_CONNECTION_TIMEOUT = 90;//seconds

    /**
     * Stores the total bytes count.
     */
    protected int downloadedBytes;
    /**
     * Stores the program properties.
     */
    protected Properties properties;

    protected String proxySet;
    protected String proxyHost;
    protected String proxyPort;
    protected String username;
    protected String password;

    /**
     * Creates a new Ripper object.
     */
    public Spider(Properties newProperties) {
        if (newProperties != null) {
            properties = newProperties;
            proxySet = properties.getProperty(KEY_PROXY_SET);
            proxyHost = properties.getProperty(KEY_PROXY_HOST);
            proxyPort = properties.getProperty(KEY_PROXY_PORT);
            username = properties.getProperty(KEY_PROXY_USERNAME);
            password = properties.getProperty(KEY_PROXY_PASSWORD);
        }
    }

    public void download(String url, String outFileName) throws IOException {
        download(url, outFileName, FileUtils.CHARSET_WIN1251);
    }

    public void download(String url, String outFileName, String charset) throws IOException {
        StringBuffer content = getContent(url, charset);
        if (content != null) {
            FileUtils.writeFile(outFileName, content.toString(), charset, false);
        }
    }

    /**
     * Sets the http proxy parameters.
     *
     * @param connection a connection
     */
    protected void setProxy(HttpURLConnection connection) throws NullPointerException {
        if (properties != null) {
            Properties sysProperties = System.getProperties();
            sysProperties.put(KEY_PROXY_SET, proxySet);
            sysProperties.put(KEY_PROXY_HOST, proxyHost);
            sysProperties.put(KEY_PROXY_PORT, proxyPort);
            String tmp = new String(username + ":" + password);
            String encoded = new BASE64Encoder().encode(tmp.getBytes());
            connection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
        }
    }

    /**
     * Downloads the content from the Internet.
     *
     * @param urlString some url
     * @return the content
     * @throws IOException an exception
     */
    public StringBuffer getContent(String urlString, String charset) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        setProxy(connection);

        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int contentSize = bis.available();
        downloadedBytes += contentSize;
        try {
            byte[] buffer = new byte[contentSize];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } finally {
            bis.close();
        }

        connection.disconnect();
        return new StringBuffer(new String(out.toByteArray(), charset));
    }

    /**
     * Gets the total downloaded bytes count.
     *
     * @return the total downloaded bytes count
     */
    public int getDownloadedBytes() {
        return downloadedBytes;
    }
}