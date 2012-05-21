package org.am.charts;

import java.awt.*;

/**
 * The DefaultChart. 
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class DefaultChart extends BaseChart {
  public static final int LABELS_NUMBER = 10;
  public static final double LABELS_LEFT_MARGIN = 2.0;

  public static final Color GRID_COLOR = Color.LIGHT_GRAY;

  protected boolean showChartYLabels = true;
  protected int labelsNumber = LABELS_NUMBER;
  protected double[] chartYLabels;

  protected boolean showGrid = true;
  protected Color gridColor = GRID_COLOR;

  protected double maxValue;
  protected double minValue;

  protected boolean showPointsLabels = true;
   //Stores valueCircles
  protected Point[] valueCircles;

  /**
   * Creates a new DefaultChart object.
   * @param title a title
   */
  public DefaultChart(String title) {
    super(title);

  }

  public void setShowChartYLabels(boolean showChartYLabels) {
    this.showChartYLabels = showChartYLabels;
  }

  /**
   * Draws chart Y labels and horizontal grid.
   *
   * @param g Graphics2D
   */
  protected void drawYLabels(Graphics2D g) {
    if (isValuesFilled()) {
      double labelTop = getBottomY();
      double gridTop = getBottomY();
      double yChartYLabel;
      int x;
      int y;
      int w;
      int h;

      for (int i = 0; i < labelsNumber; i++) {
        //chart left Y label
        labelTop = labelTop - (getBottomY() - chartMargin)/labelsNumber;
        if (showChartYLabels) {
          yChartYLabel = labelTop + getStringHeight(chartYLabels[i], g)/3;
          x = round(LABELS_LEFT_MARGIN);
          y = round(yChartYLabel);
          g.setColor(labelsColor);
          g.drawString(format(chartYLabels[i]), x, y);
        }

        if (showGrid) {
          //chart grid
          gridTop = gridTop - (getBottomY() - chartMargin)/labelsNumber;
          x = round(leftChartMargin);
          y = round(gridTop);
          w = round(getWidth() - chartMargin);
          h = round(gridTop);
          g.setColor(gridColor);
          g.drawLine(x, y, w, h);
        }
      }

//      if (showChartYLabels) {
//        //0 label
//        x = round(LABELS_LEFT_MARGIN);
//        y = round(getBottomY());
//        g.setColor(labelsColor);
//        g.drawString(ZERO, x, y);
//      }
    }
  }

  /**
   * Draws X labels.
   *
   * @param g Graphics2D
   */
  protected void drawXLabels(Graphics2D g) {
    if (isValuesFilled()) {
      int x, y, xValue;
      double pointsStep = (getWidth() - (leftChartMargin)* 2) / getValues().length;

      for (int i = 0; i < getValues().length; i++) {
        xValue = round(leftChartMargin + pointsStep * i); //x of value
        x = round(xValue - getStringWidth(getValues()[i].getLabel(), g) / 2); //x of centered X label
        y = round(getHeight() - getCenterPoint(chartMargin, getStringHeight(getValues()[i].getLabel(), g)));
        g.setColor(labelsColor);
        g.drawString(getValues()[i].getLabel(), x, y);

        //Vertical grid
        if (showGrid) {
          g.setColor(gridColor);
          g.drawLine(xValue, y, xValue, round(chartMargin));
        }
      }
    }
  }

  public void setShowGrid(boolean showGrid) {
    this.showGrid = showGrid;
  }

  public void setLabelsNumber(int labelsNumber) {
    this.labelsNumber = labelsNumber;
  }

  protected void calcYLabels() {
    chartYLabels = new double[labelsNumber];

    double step = (maxValue - minValue) / labelsNumber;

    double label = minValue - step;
    for (int i = 0; i < labelsNumber; i++) {
      //chart left Y label
      label = label + step;
      chartYLabels[i] = label;
    }
  }

  protected void calcLeftChartMargin(Graphics2D g) {
    if (chartYLabels == null) {
      return;
    }

    double widest = 0;
    double width;
    for (int i = 0; i < chartYLabels.length; i++) {
      width = getStringWidth(chartYLabels[i], g);
      if (width > widest) {
        widest = width;
      }
    }
    leftChartMargin = widest + LABELS_LEFT_MARGIN * 2;
    if (leftChartMargin < chartMargin) {
      leftChartMargin = chartMargin;
    }
  }

  /**
   * Gets max value of values.
   *
   * @return max value of values
   */
  protected double calcMaxValue() {
    if (getValues() == null) {
      return 0;
    }

    double maxValue = Double.MIN_VALUE;
    for (Value value : getValues()) {
      if (value.getValue() > maxValue) {
        maxValue = value.getValue();
      }
    }
    return maxValue;
  }

  /**
   * Gets min value of values.
   *
   * @return min value of values
   */
  protected double calcMinValue() {
    if (getValues() == null) {
      return 0;
    }

    double minValue = Double.MAX_VALUE;
    for (Value value : getValues()) {
      if (value.getValue() < minValue) {
        minValue = value.getValue();
      }
    }
    return minValue;
  }

  public void setValues(Value[] newValues) {
    setValues(newValues, MAX_FRACTION_DIGITS);
//    setValues(newValues, MAX_FRACTION_DIGITS_SMALL);
  }

  public void setValues(Value[] newValues, int maxFractionDigits) {
    super.setValues(newValues);
    setMaxFractionDigits(maxFractionDigits);

    maxValue = calcMaxValue();
    minValue = calcMinValue();

    if (newValues != null) {
      valueCircles = new Point[newValues.length];
    } else {
      valueCircles = null;
    }
    repaint();
  }

  /**
   * Gets point's top y coordinate.
   *
   * @param value an input value
   * @return      bar top y coordinate
   */
  protected double getPointTop(double value) {
    if (value == 0) {
      return getBottomY();
    }
    return chartMargin + getBarHeight(maxValue) - getBarHeight(value);
  }


  /**
   * Gets bar height in pixels.
   *
   * @param value an input value
   * @return      bar height in pixels
   */
  protected double getBarHeight(double value) {
    if (value == 0) {
      return 0;
    }
    double factor = (maxValue - minValue)/value;
    return (getBottomY() - getTopY()) / factor;
  }

  public void setShowPointsLabels(boolean showPointsLabels) {
    this.showPointsLabels = showPointsLabels;
  }

  protected void addValueCircle(int index, int x, int y) {
    valueCircles[index] = new Point(x, y);
  }

}
