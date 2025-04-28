package src.database;

public class DataConnection {
    private static String USR = "a24joechiher_padel";
    private static String PWD = "Holaquetal123";
    private static String URL = "https://daw.inspedralbes.cat/phpmyadmin";

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