package com.sy.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * jdbc创建数据源的方式，可以对数据库进行增删改查 注:底层实现用的是反射方式
 *
 * */
public class DBUtil {
    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();


    public static Connection getConnection()  {
        try {
            //conn1
            Connection connection = THREAD_LOCAL.get();
            if(connection==null){

                DruidDataSource dataSource = new DruidDataSource();
                Properties properties = new Properties();
                properties.load(DBUtil.class.getClassLoader().getResourceAsStream("com/sy/util/db.properties"));
                dataSource.setConnectProperties(properties);
                connection = dataSource.getConnection();
                THREAD_LOCAL.set(connection);
            }
            //Map<Thread,Connection>
            //Map.get(Thread)
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        close(resultSet);
        close(statement);
        close(connection);
    }



    public static void close(ResultSet resultSet){
        try {
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void close(Statement statement){
        try {
            if(statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void close(Connection connection){
        try {
            if(connection != null){
                connection.close();
                THREAD_LOCAL.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用更新事物
     * @param sql sql语句
     * @param args 添加语句
     * @return 返回成功数
     * @throws Exception 抛异常
     */
    public static int executeUpdate(String sql,Object...args) throws Exception{

        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject((i+1), args[i]);
            }
            int i = preparedStatement.executeUpdate();
            return i;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            close(preparedStatement);
        }
    }

    /**
     *
     * 通用查询
     * 通过反射机制
     * @return 返回输入类型
     * @throws Exception 抛异常
     */
    public static <T> List<T> executeQuery(Class<T>clazz, String sql, Object...args) throws Exception{

        //1.创建封装数据的对像
        //2.怎么去结果集中取值
        //3.怎么给对像的属性付值
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<T> list = new ArrayList<>();
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject((i+1), args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                T t = clazz.newInstance();
                Field[] declaredFields = clazz.getDeclaredFields();
                for(Field f:declaredFields){
                    //让私有属性可见
                    f.setAccessible(true);
                    //属性名
                    String name = f.getName();
                    Object value = resultSet.getObject(name);
                    //属性，对像，值
                    //对像.属性=值; 属性.set(对像，值)

                    f.set(t, value);
                }
                list.add(t);
            }
            return list;
        }catch (Exception e){
            throw e;
        }finally {
            close(preparedStatement);
            close(resultSet);
        }
    }
    //开启事务
    public static void beginTx() throws Exception{
        try {
            getConnection().setAutoCommit(false);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    //事务提交
    public static void commitTx() throws Exception{
        Connection connection = null;
        try {
            connection = getConnection();
            connection.commit();
            connection.setAutoCommit(true);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            close(connection);
        }
    }

    //事务回滚
    public static void rollbackTx(){
        Connection connection = null;
        try {
            connection = getConnection();
            connection.rollback();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(connection);
        }
    }
}
