package com.edu.dao;

import com.edu.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 19:02
 */
public class Dao {
    public Boolean addObj(String sql) {
        DBUtil dbUtil = new DBUtil();
        int flag = 0;
        Statement statement = dbUtil.getStatement();
        try {
            flag = statement.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dbUtil.close();

        if(flag > 0)
            return true;
        else
            return false;
    }

    public List<?> query(String sql, Class<?> class1) {
        // TODO Auto-generated method stub
        List<?> list = null;

        DBUtil dbUtil = new DBUtil();
        ResultSet resultSet = dbUtil.getResultSet(sql);
        list = (List<?>) dbUtil.resultToList(resultSet, class1);
        dbUtil.close();

        return list;
    }
}