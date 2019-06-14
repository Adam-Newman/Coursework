import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static Connection db = null;
    private static Throwable exception;

    public static void writeToDatabase() {
        try{
            PreparedStatement ps = db.prepareStatement("Insert INTO Users (UserID, Username, Password, Email) VALUES (?, ?, ?, ?)");

            ps.setInt(1, 6);
            ps.setString(2, "Charlie");
            ps.setString(3, "pASSWORD");
            ps.setString(4, "CDE");

            ps.executeUpdate();
        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
        }


    }

    public static void main(String[] args) {
        openDatabase("courseworkDB.db");
        writeToDatabase();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username, Password, Email FROM Users ");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int UserID = results.getInt(1);
                String Username = results.getString(2);
                String Password = results.getString(3);
                String Email = results.getString(4);
                System.out.println(UserID + " " + Username + " " + Password + " " + Email);
            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
        closeDatabase();

    }



    private static void openDatabase(String dbFile) {
        try  {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }

    private static void closeDatabase(){
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }

}
