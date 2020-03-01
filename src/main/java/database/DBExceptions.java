package database;

public class DBExceptions extends Exception {

    public DBExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public DBExceptions(Throwable cause){
        this("Sorry database not available.", cause);
    }
}
