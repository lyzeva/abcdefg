package com.oneapm.research.correlation;

import java.sql.*;

/**
 * Created by ruan on 16-5-25.
 * Connect mySQL and execute mySQL query.
 */
public class DataBaseConnection {
    private String machineIp = "";
    private String machinePort = "";
    private String machinePath = "";
    private Connection connection = null;

    public DataBaseConnection(String driver, String ip, String port, String path) throws SQLException {
        machineIp = ip;
        machinePort = port;
        machinePath = path;
        setConnection(driver);
    }

    private void setConnection(String driver) throws SQLException {
        try {
            if (driver.equals("druid_driver"))
                Class.forName("com.blueocn.tps.jdbc.driver.Driver");
            else
                Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load driver file", e);
        }
        String jdbcUrl = "";
        if (driver.equals("druid_driver")) {
            jdbcUrl = machineIp + ":" + machinePort + machinePath;

        }
        else {
            jdbcUrl = machineIp + ":" + machinePort + machinePath + "?user=root&password=root&useUnicode=true&characterEncoding=UTF8";
        }
        this.connection = DriverManager.getConnection(jdbcUrl);
     }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getResultForSql(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        return statement.getResultSet();
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
