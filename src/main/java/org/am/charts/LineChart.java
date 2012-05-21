// *****************************************************************
//
// $RCSfile: LineChart.java,v $: The XYChart.
//
// $Revision: 1.1 $
// *****************************************************************
//
//  Datum       Author              Kommentar
// -----------------------------------------------------------------
//  19.05.2006  Alexander Maximenya Created
// *****************************************************************
package org.am.charts;

import org.am.utils.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The Line chart. Draws lines by valueCircles.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class LineChart extends DefaultChart {
  public static final Dimension POINT_SIZE = new Dimension(9, 9);
  private static final String PIN = "/pin.png";

  //Stores a mouseMotionPoint for mouse motions
  private Point mouseMotionPoint;

  private double firstValue = Double.MIN_VALUE;
  private double secondValue = Double.MIN_VALUE;
  private Point firstPoint;
  private Point secondPoint;

  private Image pinImage;


  /**
   * Creates a new XYChart object.
   *
   * @param title  a chart's title
   */
  public LineChart(String title) {
    super(title);

    pinImage = new IconLoader().getImage(PIN);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    if (showChartYLabels) {
      calcYLabels();
      calcLeftChartMargin(g2);
    }

    drawChartArea(g2, leftChartMargin);
    //X labels
    drawXLabels(g2);
    //Y labels and horizontal grid
    drawYLabels(g2);

    drawLines(g2);

    drawValueSigns(g2);

    drawValueHint(g2);
  }

  /**
   * Draws lines and circles.
   *
   * @param g Graphics2D
   */
  protected void drawLines(Graphics2D g) {
    if (!isValuesFilled()) {
      return;
    }
    g.setColor(Color.RED);

    double pointsStep = (getWidth() - (leftChartMargin)* 2) / getValues().length;

    int x1;
    int y1;
    int x2 = 0;
    int y2 = 0;

    for (int i = 0; i < getValues().length; i++) {
      //first mouseMotionPoint and value circle
      x1 = round(leftChartMargin + pointsStep * i);
      y1 = round(getPointTop(getValue(i).getValue()));
      g.fillOval(x1 - POINT_SIZE.width/2, y1 - POINT_SIZE.height/2, POINT_SIZE.width, POINT_SIZE.height);
      addValueCircle(i, x1, y1);

      //Second mouseMotionPoint
      if (i < getValues().length - 1) {
        x2 = round(leftChartMargin + pointsStep * (i + 1));
        y2 = round(getPointTop(getValue(i + 1).getValue()));
      }
      g.drawLine(x1, y1, x2, y2);

      //Point label
      if (showPointsLabels && getValue(i).getValue() > 0) {
        int yPointLabel = round(getPointTop(getValues()[i].getValue())) - 10;
        g.drawString(format(getValue(i).getValue()), x1, yPointLabel);
      }
    }
  }

  protected void drawValueHint(Graphics2D g) {
    Value v = getValueByPoint(mouseMotionPoint);
    if (v != null) {
      final int STRING_MARGIN = 5;
      final int SHADOW_MARGIN = 3;

      int x, y;
      String s = String.valueOf(v.getValue());
      int width = round(getStringWidth(s, g)) + STRING_MARGIN * 2;
      int height = 15;//@todo calculate height

      //shadow
      g.setColor(Color.GRAY);
      x = round(mouseMotionPoint.getX()) + 10;
      y = round(mouseMotionPoint.getY()) - 10;
      g.fillRect(x, y, width, height);

      //frame
      g.setColor(Color.RED);
      x = x - SHADOW_MARGIN;
      y = y - SHADOW_MARGIN;
      g.fillRect(x, y, width, height);

      //string
      g.setColor(Color.WHITE);
      x = x + STRING_MARGIN;
      y = y + height - STRING_MARGIN;
      g.drawString(s, x, y);
    }
  }

  protected Value getValueByPoint(Point p) {
    if (valueCircles != null && p != null) {
      for (int i = 0; i < valueCircles.length; i++) {
        Point vc = valueCircles[i];
        if (vc != null) {
          if ( (p.getX() > vc.getX() - POINT_SIZE.width) && (p.getX() < vc.getX() + POINT_SIZE.width) ) {
            if ((p.getY() > vc.getY() - POINT_SIZE.height) && (p.getY() < vc.getY() + POINT_SIZE.height)) {
              return getValues()[i];
            }
          }
        }
      }
    }
    return null;
  }

  protected void mouseMotion(MouseEvent e) {
    if (isValuesFilled()) {
      if (!e.getPoint().equals(mouseMotionPoint)) {
        mouseMotionPoint = e.getPoint();
        repaint();
      }
    }
  }

  protected void drawValueSigns(Graphics2D g) {
    g.setColor(Color.BLUE);

    final int SIZE = 30;
    int x, y;


    if (firstPoint != null) {
      x = round(firstPoint.getX() - SIZE/2);
      y = round(firstPoint.getY() - SIZE/2);
//      if (pinImage != null) {
        g.drawImage(pinImage, x, y, this);
//      } else {
//        g.fillOval(x, y, SIZE, SIZE);
//      }
    }
    if (secondPoint != null) {
      x = round(secondPoint.getX() - SIZE/2);
      y = round(secondPoint.getY() - SIZE/2);
//      if (pinImage != null) {
        g.drawImage(pinImage, x, y, this);
//      } else {
//        g.fillOval(x, y, SIZE, SIZE);
//      }

      //connection line between 2 points
      g.drawLine((int)firstPoint.getX(), (int)firstPoint.getY(), (int)secondPoint.getX(), (int)secondPoint.getY());
    }

  }

  protected void mouseLeftButtonDoubleClick(MouseEvent e) {
    Value v = getValueByPoint(e.getPoint());
    if (v != null) {
      if (firstValue == Double.MIN_VALUE) {
        firstValue = v.getValue();
        firstPoint = e.getPoint();
      } else if (secondValue == Double.MIN_VALUE) {
        secondValue = v.getValue();
        secondPoint = e.getPoint();
      }
      repaint();

      if (firstValue != Double.MIN_VALUE && secondValue != Double.MIN_VALUE) {
        double diff = (secondValue - firstValue) * 100 / firstValue;
        JOptionPane.showMessageDialog(this, "<html><center>v1=" + firstValue + ", v2=" + secondValue + "<br><b>diff=" + format(diff) + " %</b></center></html>");
        firstValue = Double.MIN_VALUE;
        secondValue = Double.MIN_VALUE;
        firstPoint = null;
        secondPoint = null;
      }
    }
  }
}
