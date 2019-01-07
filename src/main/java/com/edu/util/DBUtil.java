package com.edu.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 15:58
 * 数据库连接与关闭类
 */
public class DBUtil {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private DataSource data = new ComboPooledDataSource();

    /**
     * 获得数据库连接
     * @return
     */
    private Connection getConnection() {
        try {
            connection = data.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 获得Statement对象
     * @return
     */
    public Statement getStatement() {
        connection = getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return statement;
    }

    /**
     * 获得ResurtSet结果集
     * @param sql
     * @return
     */
    public ResultSet getResultSet(String sql) {
        statement = getStatement();
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 关闭连接
     */
    public void close() {
        if(null!=resultSet){
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            resultSet = null;
        }

        if(null!=statement) {
            try {
                statement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            statement = null;
        }


        if(null!=connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            connection = null;
        }
    }

    /**
     * 封装list
     * @param resultSet
     * @param classs
     * @return
     */
    public Object resultToList(ResultSet resultSet,Class<?> classs){
        //用来封装实体类对象
        List<Object> list = new ArrayList<Object>();
        try {
            while(resultSet.next()){
                //是用哪个反射机制创建实体类对象
                Object entity = classs.newInstance();
                for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
                    //根据索引获取当前字段名称
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    //获取当前索引的值
                    Object value = resultSet.getObject(i);
                    //根据字段名称获得当前类中的单一属性
                    Field field = classs.getDeclaredField(columnName);
                    //将当前属性设置为可赋值状态
                    field.setAccessible(true);
                    //为指定对象的舒心进行赋值
                    field.set(entity, value);
                }
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        DBUtil dbUtils = new DBUtil();
        Connection connection = dbUtils.getConnection();
        if(connection != null)
            System.out.println("数据库连接成功！");
        else
            System.out.println("数据库连接失败！");
    }
}
