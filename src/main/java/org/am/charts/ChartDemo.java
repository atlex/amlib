package org.am.charts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

/**
 * This is a demo with charts.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.7 $
 */
public class ChartDemo extends JFrame {
  protected BarChart barChart;
  protected PieChart pieChart;
  protected LineChart lineChart;
  protected JTabbedPane tab;

  /**
   * Creates a new ChartDemo object.
   */
  public ChartDemo() {
    setTitle("Chart Demo");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel pane = (JPanel)getContentPane();
    pane.setLayout(new BorderLayout());

    JToolBar toolBar = new JToolBar();
    JButton bClear = new JButton(new ClearAction());
    JButton bFirst = new JButton(new FirstAction());
    JButton bSecond = new JButton(new SecondAction());
    JButton bThird = new JButton(new ThirdAction());
    JButton bFourth = new JButton(new FourthAction());
    JButton bFifth = new JButton(new FifthAction());
    JButton bPng = new JButton(new PNGAction());
    JButton bHorizontal = new JButton(new HorizontalAction());

    toolBar.add(bClear);
    toolBar.add(bFirst);
    toolBar.add(bSecond);
    toolBar.add(bThird);
    toolBar.add(bFourth);
    toolBar.add(bFifth);
    toolBar.add(bHorizontal);
    toolBar.add(bPng);

    tab = new JTabbedPane();
    barChart = new BarChart("BarChart");
    pieChart = new PieChart("PieChart");
    lineChart = new LineChart("LineChart");
    tab.add(barChart, "BarChart");
    tab.add(pieChart, "PieChart");
    tab.add(lineChart, "LineChart");
    pane.add(toolBar, BorderLayout.NORTH);
    pane.add(tab, BorderLayout.CENTER);
  }

  public void setSelectedTab(int index) {
    tab.setSelectedIndex(index);
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

    ChartDemo chartDemo = new ChartDemo();
    chartDemo.setSize(700, 500);
    chartDemo.setLocationRelativeTo(null);
    chartDemo.setSelectedTab(2);
    chartDemo.setVisible(true);
  }

  protected class ClearAction extends AbstractAction {
    /**
     * Defines an <code>Action</code> object with the specified
     * description string and a default icon.
     */
    public ClearAction() {
      super("Clear");
    }
    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
      barChart.setOrientation(BarChart.ORIENATION_VERTICAL);
      barChart.clearChart();
      pieChart.clearChart();
      lineChart.clearChart();
    }
  }
  protected class FirstAction extends AbstractAction {
    /**
     * Defines an <code>Action</code> object with the specified
     * description string and a default icon.
     */
    public FirstAction() {
      super("1");
    }
    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
      Value[] values = new Value[7];
      values[0] = new Value("16.11", 2700.1);
      values[1] = new Value("17.11", 2730.2);
      values[2] = new Value("18.11", 2710.3);
      values[3] = new Value("19.11", 2760.1);
      values[4] = new Value("20.11", 2700.5);
      values[5] = new Value("21.11", 2710);
      values[6] = new Value("22.11", 2800);

      barChart.setTitle("1");
      pieChart.setTitle("1");
      barChart.setValues(values);
      pieChart.setValues(values);

      lineChart.setTitle("1");
//      Point[] line1Points = new Point[5];
//      line1Points[0] = new Point(0, 0);
//      line1Points[1] = new Point(20, 10);
//      line1Points[2] = new Point(40, 20);
//      line1Points[3] = new Point(80, 40);
//      line1Points[4] = new Point(100, 50);
//            Point[] line2Points = new Point[5];
//            line2Points[0] = new Point(0, 8);
//            line2Points[1] = new Point(4, 11);
//            line2Points[2] = new Point(8, 1);
//            line2Points[3] = new Point(11, 4);
//            line2Points[4] = new Point(15, 15);

//      Line line1 = new Line(line1Points, Color.RED);
//            Line line2 = new Line(line2Points, Color.GREEN);

//            Line[] lines = new Line[2];
//      Line[] lines = new Line[1];
//      lines[0] = line1;
//            lines[1] = line2;
//      lineChart.setLines(lines);
      lineChart.setValues(values, 1);
    }
  }
  protected class SecondAction extends AbstractAction {
    /**
     * Defines an <code>Action</code> object with the specified
     * description string and a default icon.
     */
    public SecondAction() {
      super("2");
    }
    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
      Value[] values = new Value[4];
      values[0] = new Value("Monday", 2.5);
      values[1] = new Value("Tuesday",2.5);
      values[2] = new Value("Friday", 2.5);
      values[3] = new Value("Sunday", 2.5);

      barChart.setTitle("2");
      pieChart.setTitle("2");
      barChart.setValues(values);
      pieChart.setValues(values);
    }
  }
  protected class ThirdAction extends AbstractAction {
    /**
     * Defines an <code>Action</code> object with the specified
     * description string and a default icon.
     */
    public ThirdAction() {
      super("3");
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
      Value[] values = new Value[4];
      values[0] = new Value("Monday", 15000);
      values[1] = new Value("Tuesday", 10000);
      values[2] = new Value("Wednesday", 2);
      values[3] = new Value("Thursday", 100);

      barChart.setTitle("3");
      pieChart.setTitle("3");
      barChart.setValues(values);
      pieChart.setValues(values);
    }
  }
  protected class FourthAction extends AbstractAction {
    /**
     * Defines an <code>Action</code> object with the specified
     * description string and a default icon.
     */
    public FourthAction() {
      super("4");
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
      Value[] values = new Value[30];
      double rV;
      for (int i = 0; i < values.length; i++) {
        rV = Math.random();
        values[i] = new Value(Double.toString(rV), rV);
      }
      barChart.setTitle("4");
      pieChart.setTitle("4");
      barChart.setValues(values);
      pieChart.setValues(values);
    }
  }
  protected class FifthAction extends AbstractAction {
    /**
     * Defines an <code>Action</code> object with the specified
     * description string and a default icon.
     */
    public FifthAction() {
      super("5");
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
      Value[] values = new Value[0];
      barChart.setTitle("0");
      pieChart.setTitle("0");
      barChart.setValues(values);
      pieChart.setValues(values);
    }
  }
  protected class PNGAction extends AbstractAction {
    /**
     * Defines an <code>Action</code> object with the specified
     * description string and a default icon.
     */
    public PNGAction() {
      super("PNG");
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
      BaseChart chart = barChart;

      JFileChooser fileChooser = new JFileChooser("d:\\work");
      switch (tab.getSelectedIndex()) {
        case 0: //BarChart
          fileChooser.setSelectedFile(new File("BarChartTest.png"));
          chart = barChart;
          break;
        case 1: //PieChart
          fileChooser.setSelectedFile(new File("PieChartTest.png"));
          chart = pieChart;
          break;
        default:
      }

      if (fileChooser.showSaveDialog(ChartDemo.this) == JFileChooser.APPROVE_OPTION) {
        try {
          chart.saveAsPNG(chart.getWidth(), chart.getHeight(), fileChooser.getSelectedFile().getAbsolutePath());
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }


  protected class HorizontalAction extends AbstractAction {
    /**
     * Defines an <code>Action</code> object with the specified
     * description string and a default icon.
     */
    public HorizontalAction() {
      super("Horizontal");
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
//            Value[] values = new Value[30];
//            double rV;
//            for (int i = 0; i < values.length; i++) {
//                rV = Math.random();
//                values[i] = new Value(Double.toString(rV), rV);
//            }
      Value[] values = new Value[4];
      values[0] = new Value("MondayMondayMonday", 15000);
      values[1] = new Value("TuesdayTuesdayTuesday", 10000);
      values[2] = new Value("WednesdayWednesday", 2);
      values[3] = new Value("ThursdayThursday", 100);

      barChart.setTitle("Horizontal orientation");
      barChart.setOrientation(BarChart.ORIENATION_HORIZONTAL);
      barChart.setUseDifferentColors(true);
      barChart.setLabelsColorLikeBars(true);
      barChart.setValues(values);
    }
  }
}
