package org.am.net;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.text.MessageFormat;

/**
 * The Downloader. 
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class Downloader {
  protected String proxyHost;
  protected String proxyPort;
  protected String proxyUsername;
  protected String proxyPassword;

  protected ResourceBundle res;
  protected List<DownloaderListener> listeners;

  /**
   * Creates a new Downloader object.
   */
  public Downloader() {
    this(null, null, null, null);
  }

  /**
   * Creates a new Downloader object.
   *
   * @param proxyHost
   * @param proxyPort
   * @param proxyUsername
   * @param proxyPassword
   */
  public Downloader(String proxyHost, String proxyPort, String proxyUsername, String proxyPassword) {
    this.proxyHost = proxyHost;
    this.proxyPort = proxyPort;
    this.proxyUsername = proxyUsername;
    this.proxyPassword = proxyPassword;

    res = ResourceBundle.getBundle("DownloaderBundle");
    listeners = new ArrayList<DownloaderListener>();
  }

  /**
   * Downloads a content.
   *
   * @param url an URL
   * @return    the content
   */
  public String getContent(String url) {
    initConnection();

    InputStream is = null;
    StringBuffer content = null;
    try {
      URL u = new URL(url);
      fireProgress(getStr("ConnectingTo", url));

      is = u.openStream();
      fireProgress(getStr("ConnectedTo", url));

      DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
      fireProgress(getStr("Size", dis.available()));

      content = new StringBuffer();
      String s;
      while ((s = dis.readLine()) != null) {
        content.append(s);
      }
    } catch (MalformedURLException e) {
      fireError(e);
    } catch (IOException e) {
      fireError(e);
    } finally {
      try {
        if (is != null) {
          is.close();
        }
      } catch (IOException e) {
        fireError(e);
      }
    }

    if (content != null) {
      //TODO russian charset support
      return content.toString();
    }
    return null;
  }

  /**
   * Adds listener.
   *
   * @param l listener
   */
  public void addDownloaderListener(DownloaderListener l) {
    listeners.add(l);
  }

  /**
   * Removes listener.
   *
   * @param l listener
   */
  public void removeDownloaderListener(DownloaderListener l) {
    listeners.remove(l);
  }

  /**
   * Inits connection.
   */
  protected void initConnection() {
    if (proxyHost != null && proxyPort != null) {
      if (proxyUsername != null && proxyPassword != null) {
        //TODO init proxy connection
      }
    }
  }

  /**
   *
   * @param s
   */
  protected void fireProgress(String s) {
    for (DownloaderListener l : listeners) {
      l.downloadProgress(s);
    }
  }

  /**
   *
   * @param e
   */
  protected void fireError(Exception e) {
    for (DownloaderListener l : listeners) {
      l.downloadError(e);
    }
  }

  protected String getStr(String key, String arg) {
    return MessageFormat.format(res.getString(key), arg);
  }

  protected String getStr(String key, int arg) {
    return getStr(key, "" + arg);
  }

}
