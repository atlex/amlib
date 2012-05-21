/**
 * Date         Comments
 * ---------------------------------------------
 * 2005-10-03 Created.
 */
package org.am.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The Exporter.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class Exporter {
    protected static final String DELIM_LINE = System.getProperty("line.separator");
    protected static final String HTML = "<html>";
    protected static final String HTML_END = "</html>";
    protected static final String TR = "<tr>";
    protected static final String TR_END = "</tr>";
    protected static final String TD = "<td>";
    protected static final String TD_END = "</td>";

    protected static final String H = "<h1>";
    protected static final String H_END = "</h1>";
    protected static final String TABLE = "<table border=\"1\">";
    protected static final String TABLE_END = "</table>";
    protected static final String TR_BG_HEAD = "<tr bgcolor=\"#000066\">";
    protected static final String TR_BG = "<tr bgcolor=\"#C0C0C0\">";
    protected static final String FONT = "<font color=\"white\">";
    protected static final String FONT_END = "</font>";

    /**
     * Exports the table data to the HTML.
     *
     * @param table  the table
     * @param header the string header for HTML
     * @return       the HTML
     */
    public static StringBuffer exportToHtml(JTable table, String header) {
        StringBuffer html = new StringBuffer();

        //HTML header
        html.append(HTML); html.append(DELIM_LINE);
        html.append(H);
        html.append(header);
        html.append(H_END); html.append(DELIM_LINE);

        //Table header
        html.append(TABLE); html.append(DELIM_LINE);
        int colCount = table.getColumnCount();
        html.append(TR_BG_HEAD); html.append(DELIM_LINE);
        for (int i = 0; i < colCount; i++) {
            html.append(TD);
            html.append(FONT);
            html.append(table.getColumnName(i));
            html.append(FONT_END);
            html.append(TD_END);
        }
        html.append(TR_END);

        //Table data
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if ((i % 2) == 1) {
                html.append(TR_BG);
            } else {
                html.append(TR);
            }
            html.append(DELIM_LINE);
            for (int j = 0; j < colCount; j++) {
                html.append(TD);
                html.append(model.getValueAt(i, j));
                html.append(TD_END);
            }
            html.append(TR_END);
            html.append(DELIM_LINE);
        }
        html.append(TABLE_END);
        html.append(DELIM_LINE);
        html.append(HTML_END);

        return html;
    }

}
