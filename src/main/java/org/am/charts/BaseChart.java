package org.am.charts;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

/**
 *
 */
public class BaseChart extends JPanel {
  public static final String TITLE = "Title";
  public static final Color TITLE_COLOR = Color.BLACK;
  public static final float TITLE_FONT_SIZE = (float)14.0;
  public static final Color AXIS_COLOR = Color.GRAY;
  public static final Color CHART_AREA_COLOR = Color.WHITE;
  public static final int MAX_FRACTION_DIGITS = 0;       //for values > 1
  public static final int MAX_FRACTION_DIGITS_SMALL = 1; //for values < 1
  public static final String ZERO = "0";
  public static final String EMPTY = "";
  public static final double CHART_MARGIN = 25.0;
  public static final float LABELS_FONT_SIZE = (float)11.0;
  public static final Color LABELS_COLOR = Color.BLACK;
  public static final String TYPE_JPEG = "jpeg";
  public static final String TYPE_PNG = "png";

  public static final Color[] COLORS = new Color[]{Color.RED, Color.GREEN, Color.BLUE,
      Color.ORANGE, Color.MAGENTA, Color.YELLOW, Color.PINK, Color.CYAN,
      new Color(255, 102, 102), new Color(153, 0, 0), new Color(0, 102, 0),
      new Color(51, 102, 255)};


  protected double chartMargin = CHART_MARGIN;
  protected double leftChartMargin = chartMargin;
  protected Font labelsFont;
  protected Color labelsColor = LABELS_COLOR;
  /**
   * Axis
   */
  protected Color axisColor = AXIS_COLOR;
  /**
   * Title
   */
  protected String title = TITLE;
  protected Font titleFont;
  protected Color titleColor = TITLE_COLOR;
  /**
   * Values
   */
  private Value[] values;

  protected Color chartAreaColor = CHART_AREA_COLOR;
  /**
   * Stores antialiasing value.
   */
  protected boolean useAntialiasing = true;
  private NumberFormat numFormat;

  /**
   * Creates a new BaseChart object.
   */
  public BaseChart() {
    this(TITLE);
  }

  public BaseChart(String title) {
    titleFont = getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE);
    setTitle(title);

    labelsFont = getFont().deriveFont(LABELS_FONT_SIZE);

    numFormat = NumberFormat.getNumberInstance();
    numFormat.setMaximumFractionDigits(MAX_FRACTION_DIGITS);

    ChartMouseAdapter mouseAdapter = new ChartMouseAdapter();
    this.addMouseListener(mouseAdapter);
    this.addMouseMotionListener(mouseAdapter);
    
  }

  /**
   * @see javax.swing.JComponent#paintComponent
   */
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    if (useAntialiasing) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    //Title
    g.setColor(titleColor);
    g.setFont(titleFont);
    int x = round(getCenterX(getWidth(), title, g2));
    int y = round(getCenterY(chartMargin, title, g2));
    g.drawString(title, x, y);

    g.setFont(labelsFont);

//    drawChartArea(g2, leftChartMargin);
  }

  protected void drawChartArea(Graphics2D g, double leftMargin) {
    g.setColor(chartAreaColor);
    g.fillRect((int)(leftMargin),
        (int)(chartMargin),
        (int)(getWidth() - (leftMargin + chartMargin)),
        (int)(getHeight() - chartMargin * 2));


    g.setColor(axisColor);
    //X-Top axis
    g.drawLine((int)leftMargin, (int)getTopY(), (int)(getWidth() - chartMargin), (int)getTopY());
    //Y-Bottom axis
    g.drawLine((int)leftMargin, (int)getBottomY(), (int)(getWidth() - chartMargin), (int)getBottomY());

    //Y-Left axis
    g.drawLine((int)leftMargin, (int)getBottomY(), (int)leftMargin, (int)chartMargin);
    //Y-Right axis
    g.drawLine((int)(getWidth() - chartMargin), (int)getBottomY(), (int)(getWidth() - chartMargin), (int)chartMargin);
  }

  public void setAntialiasing(boolean useAntialiasing) {
    this.useAntialiasing = useAntialiasing;
  }
  public void setTitle(String newTitle) {
    title = newTitle;
  }

  protected double getStringWidth(String text, Graphics2D g) {
    return g.getFontMetrics().getStringBounds(text, g).getWidth();
  }
  protected double getStringWidth(double text, Graphics2D g) {
    return getStringWidth(format(text), g);
  }
  protected double getStringHeight(String text, Graphics2D g) {
    return g.getFontMetrics().getStringBounds(text, g).getHeight();
  }
  protected double getStringHeight(double text, Graphics2D g) {
    return getStringHeight(format(text), g);
  }
  protected double getCenterX(double first, String value, Graphics2D g) {
    double second = getStringWidth(value, g);
    return getCenterPoint(first, second);
  }
  protected double getCenterY(double first, String value, Graphics2D g) {
    double second = getStringHeight(value, g);
    return getCenterPoint(first, second) + second / 1.5;
  }
  protected double getCenterX(double first, double value, Graphics2D g) {
    double second = getStringWidth(format(value), g);
    return getCenterPoint(first, second);
  }

  protected double getCenterPoint(double first, double second) {
    if (first > second) {
      return (first - second) / 2.0;
    } else {
      return (second - first) / 2.0;
    }
  }

  protected double getTopY() {
    return chartMargin;
  }

  protected double getBottomY() {
    return getHeight() - chartMargin;
  }

  public int round(double value) {
    return Math.round((float)value);
  }
  public String format(double value) {
    return numFormat.format(value);
  }

  /**
   * Generates a color.
   *
   * @param i a number of chart part
   * @return  a color
   */
  protected Color getColor(int i) {
    Color color;

    if (i < COLORS.length) {
      color =  COLORS[i];
    } else {
      int r = round(Math.random() * 127);
      int g = round(Math.random() * 127);
      int b = round(Math.random() * 127);
      int rest = i % 3;
      if (rest == 0)  {
        r += 127;
      } else if (rest == 1)  {
        g += 127;
      } if (rest == 2)  {
        b += 127;
      }
      color = new Color(r, g, b);
    }

    return color;
  }

  public void setChartMargin(double newChartMargin) {
    chartMargin = newChartMargin;
  }
  public void setLabelsFont(Font labelsFont) {
    this.labelsFont = labelsFont;
  }

  public void clearChart() {
    setValues(null);
  }

  public void setMaxFractionDigits(int value) {
    numFormat.setMaximumFractionDigits(value);
  }
  
  public void setValues(Value[] newValues) {
    values = newValues;
    repaint();
  }

  public Value[] getValues() {
    return values;
  }
  public Value getValue( int index) {
    if (isValuesFilled()) {
      return values[index];
    }
    return null;
  }

  public boolean isValuesFilled() {
    return values != null && values.length > 0;
  }

  /**
   * Saves a chart as image.
   *
   * @param imageType    an image type
   * @param width        width
   * @param height       height
   * @param fileName     file name
   * @throws IOException an exception
   * @see BaseChart#TYPE_JPEG
   * @see BaseChart#TYPE_PNG
   */
  protected void saveAsImage(String imageType, int width, int height, String fileName) throws IOException {
    BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = (Graphics2D) buffImage.getGraphics();

    double sx = (double)width / (double) getWidth();
    double sy = (double) height/ (double) getHeight();

    g2.scale(sx, sy);
    paintComponent(g2);
    ImageIO.write(buffImage, imageType, new File(fileName));
  }

  /**
   * Saves a chart as JPEG image.
   *
   * @param width        width
   * @param height       height
   * @param fileName     file name
   * @throws IOException an exception
   */
  public void saveAsJPEG(int width, int height, String fileName) throws IOException {
    saveAsImage(TYPE_JPEG, width, height, fileName);
  }

  /**
   * Saves a chart as PNG image.
   *
   * @param width        width
   * @param height       height
   * @param fileName     file name
   * @throws IOException an exception
   */
  public void saveAsPNG(int width, int height, String fileName) throws IOException {
    saveAsImage(TYPE_PNG, width, height, fileName);
  }

  /**
   * Draws a string with a shadow.
   *
   * @param g
   * @param str
   * @param x
   * @param y
   */
  protected void drawShadowString(Graphics2D g, String str, int x, int y) {
    Color oldColor = g.getColor();
    g.setColor(Color.GRAY);
    g.drawString(str, x + 1, y + 1);
    g.setColor(oldColor);
    g.drawString(str, x, y);
  }

  protected void mouseMotion(MouseEvent e) {
  }
  protected void mouseLeftButtonDoubleClick(MouseEvent e) {
  }

  protected class ChartMouseAdapter extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
       if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1) {
         mouseLeftButtonDoubleClick(e);
       }
    }

    public void mouseMoved(MouseEvent e) {
      mouseMotion(e);
    }
  }
}
