/* 
 * Galantis Framework 
 * 
 * Copyright (C) 2006. All Rights Reserved. 
 *  
 * $Id: SQLHelper.java,v 1.0 2007/01/03 12:56:07 amaximenya Exp $ 
 * Created on 07.08.2007 18:04:13 
 * Last modification $Date: 2007/01/03 12:56:07 $ 
 */
package org.am.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Date;

/**
 * The SQLHelper. This class doesn't work!!!
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.0 $ $Date: 2007/01/03 12:56:07 $
 *          modified by: $Author: amaximenya $
 */
public class SQLHelper {
    /**
     * Creates a new SQLHelper object.
     */
    public SQLHelper() {

    }

    private String getSelect(List<String> tables, Map<String, Object> params) {
        if (tables.size() < 1) {
            return "ERROR: The tables list is empty";
        }

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");


        for (int i = 0; i < tables.size(); i++) {
            String table = tables.get(i);
            sql.append(table.toLowerCase());
            if (i < tables.size() - 1) {
                sql.append(", ");
            }
        }

        sql.append(" FROM ");

        for (int i = 0;  i < tables.size(); i++) {
            String table = tables.get(i);
            sql.append(table);
            sql.append(" ");
            sql.append(table.toLowerCase());
            if (i < tables.size() - 1) {
                sql.append(", ");
            }
        }

        if (params.isEmpty()) {
            return sql.toString();
        }

        sql.append(" WHERE ");

        int i = 0;
        for (String paramName : params.keySet()) {
            Object paramValue = params.get(paramName);
            sql.append(paramName);
            sql.append("=");
            if (paramValue instanceof java.sql.Date) {
                sql.append("'");
                sql.append(paramValue);
                sql.append("'");
            } else if (paramValue instanceof String) {
                sql.append("\"");
                sql.append(paramValue);
                sql.append("\"");    
            } else {
                sql.append(paramValue);
            }

            if (i < params.size() - 1) {
                sql.append(" AND ");
            }
            i++;
        }

        return sql.toString();
    }

    public static void main(String args[]) {
        SQLHelper sqlHelper = new SQLHelper();

        List<String> tables = new ArrayList<String>();
        tables.add("UttPl");
        tables.add("UttDuty");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uttpl.deptId", 1);
        params.put("uttpl.dateOpen", new java.sql.Date(new java.util.Date().getTime()));
        params.put("uttduty.driver", 3);
        params.put("uttduty.regNum", "4");
        params.put("uttduty.garNum", "5");

        String sql = sqlHelper.getSelect(tables, params);
        System.out.println(sql);
    }
}
