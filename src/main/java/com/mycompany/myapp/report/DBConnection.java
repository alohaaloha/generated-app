package com.mycompany.myapp.report;


    import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null)
            try {
                open();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        return conn;
    }

    public static void open() throws ClassNotFoundException, SQLException {
        if (conn != null)
            return;

        //Class.forName("net.sourceforge.jtds.jdbc.Driver");
        // conn = DriverManager.getConnection(url, username, password);
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","admin");
        conn.setAutoCommit(false);
    }

    public static void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
