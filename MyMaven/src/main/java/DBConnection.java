import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class DBConnection {
    String HOST;
    String USER;
    String PASSWORD;

    Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                HOST = new DBConnection().getPropertyValue("DB_HOST");
                USER = new DBConnection().getPropertyValue("DB_USERNAME");
                PASSWORD = new DBConnection().getPropertyValue("DB_PASSWORD");
                connection = DriverManager.getConnection(HOST, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    Scanner scanner;

    public Scanner getScanner() {
        scanner = new Scanner(System.in);
        return scanner;
    }

    public String getPropertyValue(String propertyName) {
        String propertyValue = "";
        Properties properties = new Properties();

        try (InputStream is = this.getClass().getResourceAsStream("prop.properties")) {
            properties.load(is);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }


}
