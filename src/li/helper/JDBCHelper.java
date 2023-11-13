package li.helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Manages database connections for a MySQL database
 */
public abstract class JDBCHelper {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql";
    private static final String location = "://localhost:3306/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcURL = protocol + vendor + location + databaseName ;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /**
     * Opens a connection to the database
     */
    public static void openConnection(){
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Successful Connection");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Closes an established database connection
     */
    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection closed");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
