package dutjava.tracnghiem.model.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;

public class DBUtils {
    private String URL = "jdbc:mysql://localhost:3306/data";
    private String username = "#";
    private String password = "#";
    private Connection connection = null;

    public static DBUtils instance = new DBUtils();
    private DBUtils() {}

    public void SetUsernamePassword(String username, String password)  {
        this.username = username;
        this.password = password;
    }

    public void setUrl(String url) {
        this.URL = url;
        connection = getConnection();
    }

    public Connection getConnection() {
        return getConnection(URL);
    }

    public Connection getConnection(String URL) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(username == "#" && password == "#") {
                System.out.println("Username and Password is not set! Establishing connection without security");
                connection = DriverManager.getConnection(URL);
            } else {
                connection = DriverManager.getConnection(URL, username, password);
            }
            System.out.println("Successfully connected to Database");
            return connection;
        } catch(Exception e) {
            System.out.println("Failed to connect to Database");
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String query) {
        if(connection == null)
            connection = getConnection();
        try {
            System.out.println("Executing update query: " + query);
            connection.createStatement().executeUpdate(query);
        } catch(Exception e) {
            System.out.println("Failed to execute query " + query);
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        if(connection == null)
            connection = getConnection();
        try {
            System.out.println("Executing update query: " + query);
            ResultSet result = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(query);
            return result;
        } catch(Exception e) {
            System.out.println("Failed to execute query " + query);
            e.printStackTrace();
        }
        return null;
    }

    public boolean testQuery(String query) {
        if(connection == null)
            connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            return statement.execute(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}