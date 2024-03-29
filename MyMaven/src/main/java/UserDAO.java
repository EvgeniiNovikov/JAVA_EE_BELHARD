import java.sql.SQLException;

interface UserDAO {
    boolean addUser(User user) throws SQLException;

    boolean userEnter() throws SQLException;

    String info(String login, String password) throws SQLException;
}
