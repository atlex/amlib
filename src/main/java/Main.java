import java.text.*;
import java.util.*;
import java.io.IOException;

import javax.swing.*;

import org.am.dialogs.*;
import org.am.utils.*;
import org.am.net.Downloader;

/**
 * The Main.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class Main {

  public static void main(String[] args) {
//        Log.setLevel(Log.DEBUG);
//        Log.showTime(false);
//        Log.info("Test");

//        try {
//            FileUtils.writeFile("dir\\file.txt", "test", true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//          tableSortTest();

//        Log.info(FileUtils.getFileName("D:\\work\\TaskList.txt", false));

//        dayOfWeekTest();
//          fileSeparatorTest();
//        aboutDlgTest();
//        startDateEndDateTest();
//    dffTimesTest();
//    downloaderTest();
    daysIntervalTest();
  }

  protected static void downloaderTest() {
    String url = "http://www.nbrb.by/Services/XmlExRates.aspx?ondate=11/19/2009";
//    String url = "http://fffffffff";
    Downloader d = new Downloader();

    System.out.println(d.getContent(url));
  }

  protected static void aboutDlgTest() {
    AboutDialog aboutDialog = new AboutDialog(null);
    aboutDialog.setTitle("Application About");
    aboutDialog.setIcon(new IconLoader().getIcon("/cvsAbout.png"));
    aboutDialog.setProjectName("Application");
    aboutDialog.setVersion("Version 1.0");
    aboutDialog.setEmail("a.maximenya@gmail.com");
    aboutDialog.setHomepage("www.cvsreport.com");
    aboutDialog.setCopyRights("<html><center>Copyright (c) 2005 Alexander Maximenya<br>" +
        "All rights reserved</center></html>");
    aboutDialog.setLocationRelativeTo(null);
    aboutDialog.setVisible(true);
  }

  protected static void fileSeparatorTest() {
//        String filePath = "D:\\work\\bajtext";
//        String filePath = "D:/work/bajtext";
    String filePath = "/usr/local/path";
    System.out.println(filePath);
    System.out.println(FileUtils.correctFilePath(filePath));
  }

  protected static void startDateEndDateTest() {
    Log.info(DateUtils.getCurrentWeekStartDate(DateUtils.PATTERN_DATE));
    Log.info(DateUtils.getCurrentWeekEndDate(DateUtils.PATTERN_DATE));
  }

  protected static void dayOfWeekTest() {
    String strDate;
    String day;
    try {
      strDate = "2005-11-07 19:33:20";
      day = DateUtils.getDayOfWeek(strDate, DateUtils.PATTERN_DATE_TIME);
      System.out.println(day);

      strDate = "2005-11-08 19:33:20";
      day = DateUtils.getDayOfWeek(strDate, DateUtils.PATTERN_DATE_TIME);
      System.out.println(day);

      strDate = "2005-11-09 19:33:20";
      day = DateUtils.getDayOfWeek(strDate, DateUtils.PATTERN_DATE_TIME);
      System.out.println(day);

      strDate = "2005-11-10 19:33:20";
      day = DateUtils.getDayOfWeek(strDate, DateUtils.PATTERN_DATE_TIME);
      System.out.println(day);

      strDate = "2005-11-11 19:33:20";
      day = DateUtils.getDayOfWeek(strDate, DateUtils.PATTERN_DATE_TIME);
      System.out.println(day);

      strDate = "2005-11-12 19:33:20";
      day = DateUtils.getDayOfWeek(strDate, DateUtils.PATTERN_DATE_TIME);
      System.out.println(day);

      strDate = "2005-11-13 19:33:20";
      day = DateUtils.getDayOfWeek(strDate, DateUtils.PATTERN_DATE_TIME);
      System.out.println(day);

      strDate = "2005-11-14 19:33:20";
      day = DateUtils.getDayOfWeek(strDate, DateUtils.PATTERN_DATE_TIME);
      System.out.println(day);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  protected static void tableSortTest() {
    final String FIRST = "1111";
    final String SECOND = "2222";
    final int ROW_COUNT = 50000;
    final Object[] ROW = new Object[]{FIRST, SECOND};
    final Object[] ROW2 = new Object[]{SECOND, FIRST};

    JTable table = TableUtils.createDefaultTable(new String[]{FIRST, SECOND});
    TableUtils.SorterableHeader header = TableUtils.getSorterableHeader(table.getColumnModel());
    TableUtils.ClickableTableHeaderUI ui = (TableUtils.ClickableTableHeaderUI)header.getUI();
    table.setTableHeader(header);

    for (int i = 0; i < ROW_COUNT; i++) {
      TableUtils.addRow(table, ROW2);
    }
    for (int i = 0; i < ROW_COUNT; i++) {
      TableUtils.addRow(table, ROW);
    }

    Log.info("Sort is started");
    Date start = new Date();
    ui.sort(0);
    Date end = new Date();
    Log.info("Sort is stoped");
  }

  protected static void dffTimesTest() {
    Date start = new Date();
    Date end = new Date();

    System.out.println(DateUtils.getDifference(start, end));
  }

  protected static void daysIntervalTest() {
    try {
      //OK
      //Date start = DateUtils.getDate("2009-12-01", DateUtils.PATTERN_DATE);
      //Date end = DateUtils.getDate("2009-12-11", DateUtils.PATTERN_DATE);
      //OK
      //Date start = DateUtils.getDate("2009-11-01", DateUtils.PATTERN_DATE);
      //Date end = DateUtils.getDate("2009-12-11", DateUtils.PATTERN_DATE);
      //Error
      Date start = DateUtils.getDate("2009-12-01", DateUtils.PATTERN_DATE);
      Date end = DateUtils.getDate("2010-01-12", DateUtils.PATTERN_DATE);

      Date[] interval = DateUtils.getDaysInterval(start, end);
      for (Date date : interval) {
        System.out.println(date);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
