package org.am.net;

/**
 * The DownloaderListener. 
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public interface DownloaderListener {
  /**
   * Indicates progress.
   *
   * @param s a progress string
   */
  void downloadProgress(String s);
  /**
   * Shows a download error.
   *
   * @param e an error
   */
  void downloadError(Exception e);
}
