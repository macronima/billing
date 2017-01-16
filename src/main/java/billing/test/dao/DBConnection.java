package billing.test.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class    DBConnection {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "javatest";
    private static final String DB_PASSWORD = "test";

    public static Connection getDBConnection() {

        Connection con = null;

        try {

            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);


        } catch (ClassNotFoundException | SQLException e) {

            System.out.println(e.getMessage());

        }
        return con;

    }

}