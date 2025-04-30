package src.database;

public class DataConnection {
    private static String USR = "a24joechiher_padel";
    private static String PWD = "Holaquetal123";
    private static String URL = "jdbc:mysql://daw.inspedralbes.cat:3306/a24joechiher_reservapadel";

    public static String getUSR() {
        return USR;
    }

    public static String getPWD() {
        return PWD;
    }

    public static String getURL() {
        return URL;
    }
}