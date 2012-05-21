/**
 * Sort tipBoxLabel by value
 * Arrows with tips
 */
package org.am.charts;

import java.awt.*;

/**
 * This class represents a pie chart.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.12 $
 */
public class PieChart extends BaseChart {
  public static final double PIE_MARGIN = 25.0;
  public static final double LEGEND_WIDTH = 15;
  public static final double LEGEND_MARGINS = 2.0;
  public static final String START_TIP = " (";
  public static final String END_TIP = ")";
  public static final String EQUALS = " = ";
  public static final Color TIPS_ARROW_COLOR = Color.GRAY;
  public static final Color LEGEND_COLOR = new Color(255, 255, 204);
  public static final double TIPS_ARROW_MARGIN = 10.0;

  protected double pieMargin = PIE_MARGIN;
  protected double totalValue;
  protected double legendHeight;
  protected Color legendColor = LEGEND_COLOR;
  protected Color tipsArrowColor = TIPS_ARROW_COLOR;
  protected boolean showLegend = false;

  /**
   * Creates a new PieChart object.
   *
   * @param title a title
   */
  public PieChart(String title) {
    super(title);
  }

  /**
   * @see javax.swing.JComponent#paintComponent
   */
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    //Chart area and axis
    drawChartArea(g2, chartMargin);
    //Pie
    drawPie(g2, false);
    drawPie(g2, true);
  }

  /**
   * Draws the pie.
   *
   * @param g          Graphics2D
   * @param drawLabels if ture then draws labels
   */
  protected void drawPie(Graphics2D g, boolean drawLabels) {
    if (!isValuesFilled()) {
      return;
    }
    double startAngle = 0.0;
    double arcAngle = 0.0;
    double xChart;
    double yChart;
    double chartWidth;
    double chartHeight;

    //Pie
    chartWidth = getPieWidth();
    chartHeight = getPieHeight();
    if (chartWidth > chartHeight) {
      chartWidth = chartHeight;
    } else {
      chartHeight = chartWidth;
    }
    xChart =  getCenterPoint(getWidth() , chartWidth);
    yChart =  getCenterPoint(getHeight() , chartHeight);

    for (int i = 0; i < getValues().length; i++) {
      startAngle = startAngle + arcAngle;
      arcAngle = getAngle(getValues()[i].getValue());
      g.setColor(getColor(i));
      g.fillArc(round(xChart),
          round(yChart),
          round(chartWidth),
          round(chartHeight),
          round(startAngle),
          round(arcAngle));

      //Tips with arrow
      drawTipsWithArrow(g, getValues()[i], startAngle, arcAngle, drawLabels);

      //Legend
      if (showLegend) {
        drawLegend(g, i);
      }
    }

    //Pie border
    if (getValues().length > 0) {
      g.setColor(tipsArrowColor);
      g.drawOval(round(xChart), round(yChart), round(chartWidth), round(chartHeight));
    }
  }

  protected void drawLegend(Graphics2D g, int valueNumber) {
    double y;
    double yLabel;
    //Tips
    g.setFont(labelsFont);
    g.setColor(getColor(valueNumber));
    legendHeight = getStringHeight(getValues()[valueNumber].getValue(), g);

    y = chartMargin + legendHeight * 1.3 * valueNumber + LEGEND_MARGINS;
    g.fillRect((int)(chartMargin + LEGEND_MARGINS), (int)y, (int)LEGEND_WIDTH, (int)legendHeight);

    yLabel = y + getStringHeight(getValues()[valueNumber].getValue(), g)/1.3;
    StringBuffer tip = new StringBuffer();
    tip.append(getValues()[valueNumber].getLabel());
    tip.append(START_TIP);
    tip.append(getValues()[valueNumber].getValue());
    tip.append(END_TIP);
    g.drawString(tip.toString(), (int)(chartMargin + LEGEND_WIDTH + 3), (int)yLabel);
  }

  protected void drawTipsWithArrow(Graphics2D g, Value value, double startAngle, double arcAngle, boolean drawLabels) {
    double xCenter;
    double yCenter;
    double radius;
    double xResult;
    double yResult;
    double arcRadian;
    int x;
    int y;
    int w;
    int h;
    double boxMargin = 2;

    //arrow
    arcAngle = startAngle + arcAngle / 2;
    arcRadian = Math.PI * arcAngle / 180;

    xCenter = (getPieWidth() + (pieMargin + chartMargin) * 2) / 2;
    yCenter = (getPieHeight()+ (pieMargin + chartMargin) * 2) / 2;

    if (getPieWidth() > getPieHeight()) {
      radius = getPieHeight() / 2;
    } else {
      radius = getPieWidth() / 2;
    }
    //the first point of arrow
    radius = radius + TIPS_ARROW_MARGIN;
    if (isRightSide(arcAngle)) {
      xResult = xCenter + Math.cos(arcRadian) * radius;
      yResult = yCenter - Math.sin(arcRadian) * radius;
    } else {
      xResult = xCenter + Math.cos(arcRadian) * radius;
      yResult = yCenter - Math.sin(arcRadian) * radius;
    }
    //the second point of arrow
    radius = radius - TIPS_ARROW_MARGIN * 4;
    if (isRightSide(arcAngle)) {
      xCenter = xCenter + Math.cos(arcRadian) * radius ;
      yCenter = yCenter - Math.sin(arcRadian) * radius;
    } else {
      xCenter = xCenter + Math.cos(arcRadian) * radius;
      yCenter = yCenter - Math.sin(arcRadian) * radius;
    }

    g.setColor(tipsArrowColor);
    x = round(xCenter);
    y = round(yCenter);
    w = round(xResult);
    h = round(yResult);
    g.drawLine(x, y, w, h);

    //box
    StringBuffer label = new StringBuffer();
    label.append(value.getLabel());
    label.append(EQUALS);
    label.append(format(value.getValue()));
    if (!drawLabels) {
      if (isRightSide(arcAngle)) {
        x = round(xResult);
      } else {
        x = round(xResult - getStringWidth(label.toString(), g) - boxMargin * 2);
      }
      if (isTopSide(arcAngle)) {
        y = round(yResult - getStringHeight(label.toString(), g));
      } else {
        y = round(yResult);
      }
      w = round(getStringWidth(label.toString(), g) + boxMargin * 2);
      h = round(getStringHeight(label.toString(), g));
      g.setColor(legendColor);
      g.fillRect(x, y, w, h);
      g.setColor(tipsArrowColor);
      g.drawRect(x, y, w, h);
    } else {
      //label
      if (isRightSide(arcAngle)) {
        x = round(xResult + boxMargin);
      } else {
        x = round(xResult - getStringWidth(label.toString(), g) - boxMargin);
      }
      if (isTopSide(arcAngle)) {
        y = round(yResult - boxMargin * 2);
      } else {
        y = round(yResult + getStringHeight(label.toString(), g) - boxMargin);
      }
      g.setColor(labelsColor);
      g.drawString(label.toString(), x, y);
    }
  }

  protected double getAngle(double value) {
    double percent;
    double angle;

    percent = value * 100 / totalValue;
    angle = 360 * percent / 100;

    return angle;
  }

  protected double getTotalValue() {
    if (!isValuesFilled()) {
      return 0.0;
    }

    double total = 0.0;
    for (int i = 0; i < getValues().length; i++) {
      total += getValues()[i].getValue();
    }
    return total;
  }

  protected double getPieWidth() {
    return getWidth() - (pieMargin + chartMargin) * 2;
  }
  protected double getPieHeight() {
    return getHeight() - (pieMargin + chartMargin) * 2;
  }

  /**
   * Return true if (270 < angle < 90) = true.
   *
   * @param angle an angle in degree
   * @return      true if (270 < angle < 90) = true, false otherwise
   */
  protected boolean isRightSide(double angle) {
    if (angle > 270) {
      return true;
    } else if (angle > 180) {
      return false;
    } else if (angle > 90) {
      return false;
    }
    return true;
  }
  protected boolean isTopSide(double angle) {
    if (angle > 270) {
      return false;
    } else if (angle > 180) {
      return false;
    } else if (angle > 90) {
      return true;
    }
    return true;
  }

  public void setValues(Value[] newValues) {
    super.setValues(newValues);
    totalValue = getTotalValue();
  }

  public void setShowLegend(boolean showLegend) {
    this.showLegend = showLegend;
  }
}