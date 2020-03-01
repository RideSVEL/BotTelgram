package database;

import java.sql.*;

public class DBManager {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/telegram_bot"
            + "?user=root&password=147258&serverTimezone=UTC";
    private static final String SQL_INSERT_CHAT = "INSERT INTO telegram_bot.users VALUES (DEFAULT, ?, DEFAULT)";
    private static final String SQL_FIND_USER_BY_CHAT_ID = "SELECT * FROM telegram_bot.users WHERE chat_id=?";
    private static final String SQL_UPDATE_NAME = "UPDATE telegram_bot.users SET name_user = ? WHERE chat_id = ?";
    private static final String SQL_GET_NAME = "SELECT name_user FROM telegram_bot.users WHERE chat_id = ?";

    private static DBManager instance; // == null

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }

    public Connection getConnection() throws DBExceptions {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(CONNECTION_URL);
        } catch (ClassNotFoundException e) {
            throw new DBExceptions("Class not find", e);
        } catch (SQLException e) {
            throw new DBExceptions(e);
        }
    }

    public void insertChat(long chatId) throws DBExceptions {
        try (Connection con = getConnection()) {
            if (checkUser(chatId) != 0) {
                return;
            }
            PreparedStatement ps = con.prepareStatement(SQL_INSERT_CHAT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, String.valueOf(chatId));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DBExceptions(ex);
        }
    }

    public void updateName(long chatId, String newName) throws DBExceptions {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_NAME);
            ps.setString(1, newName);
            ps.setString(2, String.valueOf(chatId));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DBExceptions(ex);
        }
    }

    public String getName(long chatId) throws DBExceptions {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(SQL_GET_NAME);
            ps.setString(1, String.valueOf(chatId));
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getString("name_user");
            }
        } catch (SQLException ex) {
            throw new DBExceptions(ex);
        }
        return null;
    }

    private int checkUser(long chatId) throws DBExceptions {
        try (Connection con = getConnection()) {
            PreparedStatement statement = con.prepareStatement(SQL_FIND_USER_BY_CHAT_ID);
            statement.setString(1, String.valueOf(chatId));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("chat_id");
            }
        } catch (SQLException e) {
            throw new DBExceptions(e);
        }
        return 0;
    }

}
