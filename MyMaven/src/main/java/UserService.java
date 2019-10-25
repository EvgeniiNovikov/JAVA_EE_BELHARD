import java.sql.*;

public class UserService extends DBConnection implements UserDAO {

    Connection connection = getConnection();

    public boolean addUser(User user) throws SQLException {
        boolean result = false;
        String createUser = "INSERT INTO USER (login, password, first_name, last_name, age, sex, description) VALUES" +
                "(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(createUser);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setInt(5, user.getAge());
            ps.setString(6, user.getSex().name());
            ps.setString(7, user.getDescription());
            ps.executeUpdate();
            result = true;
            System.out.println("Новый пользователь добавлен.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public boolean userEnter() throws SQLException {
        boolean result = false;
        System.out.println("Enter your login: ");
        String login = getScanner().nextLine();
        System.out.println("Enter your password: ");
        String password = getScanner().nextLine();
        String userFromUsers = "SELECT * FROM USER WHERE login='" + login + "' and password='" + password + "';";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(userFromUsers);
            if (resultSet.first()) {
                System.out.println("Авторизация прошла успешно");
                info(login, password);
                result = true;
            } else {
                System.out.println("Такого пользователя не существует попробуйте еще раз");
                UserServiceImpl us = new UserServiceImpl();
                us.start();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public String info(String login, String password) throws SQLException {
        String information = "";
        String sql = "SELECT description FROM USER WHERE login='" + login + "' and password='" + password + "';";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.first()) {
                information = resultSet.getString("description");
                if (information != null) {
                    System.out.printf("Пользователь: %s - info[" + information + "]", login);
                } else {
                    System.out.printf("Пользователь: %s - info[информации нет]", login);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return information;
    }

}
