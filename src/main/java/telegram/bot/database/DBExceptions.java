package telegram.bot.database;

public class DBExceptions extends Exception {

    public DBExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public DBExceptions(Throwable cause){
        this("Sorry telegram.bot.database not available.", cause);
    }
}
