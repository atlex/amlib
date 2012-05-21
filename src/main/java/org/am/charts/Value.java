package org.am.charts;

/**
 * This class stores the information about chart value.
 */
public class Value {
    protected String label;
    protected double value = 0;

    public Value(String label, double value) {
        this.label = label;
        this.value = value;
    }
    public String getLabel() {
        return label;
    }
    public double getValue() {
        return value;
    }
}
