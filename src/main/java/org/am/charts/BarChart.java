/**
 *
 */
package org.am.charts;

import java.awt.*;

/**
 * This class represents a bar chart.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.17 $
 */
public class BarChart extends DefaultChart {
  public static final Color BARS_COLOR = Color.RED;
  public static final double BAR_WIDTH = 5.0;
  public static final Color TOP_BAR_LABEL_COLOR = Color.WHITE;
  public static final double TOP_BAR_LABEL_TOP_MARGIN = 10.0;

  public static final double FIRST_BAR_LEFT_MARGIN = 5.0;

  public static final int ORIENATION_HORIZONTAL = 0;
  public static final int ORIENATION_VERTICAL = 1;

  protected Color barsColor = BARS_COLOR;
  protected double barWidth = BAR_WIDTH;

  protected boolean useDifferentColors = false;
  protected boolean isLabelsColorLikeBars = false;

  protected int orientation = ORIENATION_VERTICAL;

  /**
   * Creates a new BarChart Object.
   *
   * @param title a title
   */
  public BarChart(String title) {
    super(title);
  }

  /**
   * @see javax.swing.JComponent
   */
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    if (showChartYLabels) {
      switch (orientation) {
        case ORIENATION_HORIZONTAL:
          calcLeftChartMarginHorizontal(g2);
          break;
        case ORIENATION_VERTICAL:
          calcYLabels();
          calcLeftChartMargin(g2);
          break;
        default:
      }
    }

    drawChartArea(g2, leftChartMargin);

    switch (orientation) {
      case ORIENATION_HORIZONTAL:
        //Horizontal bars
        drawHorizontalBars(g2);
        break;
      case ORIENATION_VERTICAL:
        //X labels.
//        drawXLabels(g2);
        //Y labels and grid
        drawYLabels(g2);
        //Vertical bars and X labels
        drawBars(g2);
        break;
      default:
    }
  }

  /**
   * Draws bars and bar labels.
   *
   * @param g Graphics2D
   */
  protected void drawBars(Graphics2D g) {
    if (!isValuesFilled()) {
      return;
    }

    double barStep = (getWidth() - (leftChartMargin + FIRST_BAR_LEFT_MARGIN)* 2) / getValues().length;
    double xTopLabel;
    double xBottomLabel;
    int x;
    int y;
    int w;
    int h;
    Value value;
    double realBarWidth;
    for (int i = 0; i < getValues().length; i++) {
      //bar
      if (useDifferentColors) {
        g.setColor(getColor(i));
      } else {
        g.setColor(barsColor);
      }
      value = getValues()[i];
      xTopLabel = leftChartMargin + barStep * i + FIRST_BAR_LEFT_MARGIN;
      xBottomLabel = xTopLabel;
      realBarWidth = (barWidth + barStep) / 2;
      x = round(xTopLabel);
      y = round(getPointTop(value.getValue()));
      w = round(realBarWidth);
      h = round(getBarHeight(value.getValue()));
      g.fillRect(x, y, w, h);

      //bar border
      if (value.getValue() > 0) {
        g.setColor(gridColor);
        g.drawRect(x, y, w, h);
      }

      //top bar label
      if (showPointsLabels && value.getValue() > 0) {
        xTopLabel = xTopLabel + getCenterX(realBarWidth, value.getValue(), g);
        x = round(xTopLabel);
        y = round(getPointTop(value.getValue()) + TOP_BAR_LABEL_TOP_MARGIN);
        g.setColor(TOP_BAR_LABEL_COLOR);
        g.drawString(format(value.getValue()), x, y);
      }

      //bottom bar label
      if (isLabelsColorLikeBars) {
        g.setColor(getColor(i));
      } else {
        g.setColor(labelsColor);
      }
      xBottomLabel = xBottomLabel + getCenterX(realBarWidth, value.getLabel(), g);
      x = round(xBottomLabel);
      y = round(getHeight() - getCenterPoint(chartMargin, getStringHeight(value.getLabel(), g)));
      g.drawString(value.getLabel(), x, y);
    }
  }

  protected void drawHorizontalBars(Graphics2D g) {
    if (!isValuesFilled()) {
      return;
    }

    double barStep;
    int x;
    int y;
    int w;
    int h;
    Value value;
    double realBarWidth;

    barStep = (getHeight() - chartMargin * 2) / getValues().length;
    for (int i = 0; i < getValues().length; i++) {
      value = getValues()[i];

      //bar
      realBarWidth = (barWidth + barStep) / 2;
      x = round(leftChartMargin + 1);
      y = round(barStep * i + chartMargin + 1);
      w = round(getBarRightPoint(value.getValue()));
      h = round(realBarWidth);
      if (useDifferentColors) {
        g.setColor(getColor(i));
      } else {
        g.setColor(barsColor);
      }
      g.fillRect(x, y, w, h);

      //bar border
      if (value.getValue() > 0) {
        g.setColor(gridColor);
        g.drawRect(x, y, w, h);
      }

      //left label
      x = round(LABELS_LEFT_MARGIN);
      y = round(y + getCenterY(realBarWidth, value.getLabel(), g));
      if (isLabelsColorLikeBars) {
        g.setColor(getColor(i));
      } else {
        g.setColor(labelsColor);
      }
//            g.drawString(value.getLabel(), x, y);
      drawShadowString(g, value.getLabel(), x, y);

      //bar label
      x = round(getBarRightPoint(value.getValue()));
      if (x < leftChartMargin) {
        x = round(leftChartMargin + 1);
      }

      g.setColor(TOP_BAR_LABEL_COLOR);
      drawShadowString(g, EMPTY + value.getValue(), x, y);
    }
  }

//  protected double getTopYForValue(int value) {
//    if (value == 0) {
//      return getBottomY();
//    }
////    double factor = (minValue + maxValue)/(double)value;
//    double factor = (maxValue)/(double)value;
//    return  chartMargin + (getBottomY() - getTopY()) / factor;
//  }

  protected double getBarLenght(double value) {
    if (value == 0) {
      return 0;
    }
//    double factor = (minValue + maxValue)/value;
    double factor = (maxValue)/value;
    return  (getWidth() - leftChartMargin * 2) / factor;
  }

  /**
   * Gets bar top y coordinate.
   *
   * @param value an input value
   * @return      bar top y coordinate
   */
  protected double getBarRightPoint(double value) {
    if (value == 0) {
      return chartMargin;
    }
    return getBarLenght(value);
  }

  protected void calcLeftChartMarginHorizontal(Graphics2D g) {
    if (!isValuesFilled()) {
      return;
    }

    double widest = 0;
    double width;
    for (int i = 0; i < getValues().length; i++) {
      width = getStringWidth(getValues()[i].getLabel(), g);
      if (width > widest) {
        widest = width;
      }
    }
    leftChartMargin = widest + LABELS_LEFT_MARGIN * 2;
    if (leftChartMargin < chartMargin) {
      leftChartMargin = chartMargin;
    }
  }

  public void setBarWidth(int newBarWidth) {
    barWidth = newBarWidth;
  }

  public void setBarsColor(Color barsColor) {
    this.barsColor = barsColor;
  }



  /**
   * Use different colors for bars.
   *
   * @param useDifferentColors if true then chart use different colors for bars
   */
  public void setUseDifferentColors(boolean useDifferentColors) {
    this.useDifferentColors = useDifferentColors;
  }

  /**
   * Use bar color for labels color.
   *
   * @param labelsColorLikeBars if true then uses bar color for labels color
   */
  public void setLabelsColorLikeBars(boolean labelsColorLikeBars) {
    isLabelsColorLikeBars = labelsColorLikeBars;
  }

  /**
   * Sets a chart orientation
   *
   * @param orientation a chart orientation
   * @see BarChart#ORIENATION_HORIZONTAL
   * @see BarChart#ORIENATION_VERTICAL
   */
  public void setOrientation(int orientation) {
    this.orientation = orientation;
  }
}
