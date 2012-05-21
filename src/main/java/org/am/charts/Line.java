package org.am.charts;

import java.awt.*;

/**
 * The Line.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.1 $
 */
public class Line {
    protected Point[] points;
    protected Color color;

    public Line(Point[] points, Color color) {
        setPoints(points);
        setColor(color);
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}