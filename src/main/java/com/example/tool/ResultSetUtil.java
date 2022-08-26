package com.example.tool;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * ResultSet工具类
 * @author zql
 * @createTime 2020-11-30 22:44:09
 * @version 1.1
 * @modifyLog 1.1 优化代码
 *
 */
public class ResultSetUtil {

    /**
     * 屏蔽构造函数，避免被实例化
     */
    private ResultSetUtil() {}

    /**
     * 将ResultSet结果集转换为Json字符串
     * @author zql
     * @createTime 2020-11-30 22:44:19
     *
     * @param rs ResultSet结果集
     * @return Json字符串
     * @throws SQLException
     */
    public static String resultSetToJson(ResultSet rs) throws SQLException {
        ResultSetMetaData resultsetmd = rs.getMetaData();
        // 总数
        int total = 0;
        StringBuffer jsonstr = new StringBuffer();
        jsonstr.append("[");
        while (rs.next()) {
            total++;
            jsonstr.append("{");
            for (int i = 0, r = 1 ,len = resultsetmd.getColumnCount(); i < len; i++, r++) {
                jsonstr.append("\"").append(resultsetmd.getColumnName(r)).append("\":\"").append(rs.getString(r)).append("\",");
            }
            String s = jsonstr.toString();
            s = s.substring(0, s.length() - 1);
            jsonstr = new StringBuffer();
            jsonstr.append(s);
            jsonstr.append("},");
        }
        jsonstr.append("{");
        jsonstr.append("\"total\":\"").append(total).append("\"");
        jsonstr.append("}");
        jsonstr.append("]");
        return jsonstr.toString();
    }
}