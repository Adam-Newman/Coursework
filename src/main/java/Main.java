import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static Connection db = null;
    private static Throwable exception;

//    public static void writeToDatabase() {
//        try{
//            PreparedStatement ps = db.prepareStatement("Insert INTO Users (UserID, Username, Password, Email) VALUES (?, ?, ?, ?)");
//
//            ps.setInt(1, 6);
//            ps.setString(2, "Charlie");
//            ps.setString(3, "pASSWORD");
//            ps.setString(4, "CDE");
//
//            ps.executeUpdate();
//        } catch (Exception exception){
//            System.out.println("Database error: " + exception.getMessage());
//        }
//
//
//    }

    public static void main(String[] args) {
        openDatabase("MusicDB.db");
        //writeToDatabase();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT SongID, SongName, TLength, BPM from Songs inner join  Artist ON Songs.ArtistID=Artist.ArtistName   ");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int SongID = results.getInt(1);
                String SongName = results.getString(2);
                String TLength = results.getString(3);
                int BPM = results.getInt(4);
  //              int ArtistID = results.getInt(5);
                String ArtistName = results.getString(6);
                System.out.println(SongID + " " + SongName + " " + TLength + " " + BPM + " " + ArtistName);
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
