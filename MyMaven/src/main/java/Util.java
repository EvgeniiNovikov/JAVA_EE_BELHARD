import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Util {

    private static final String HOST = new Util().getPropertyValue("DB_HOST");
    private static final String USER = new Util().getPropertyValue("DB_USERNAME");
    private static final String PASSWORD = new Util().getPropertyValue("DB_PASSWORD");

    Connection connection;

    Scanner scanner;

    public Scanner getScanner() {
        scanner = new Scanner(System.in);
        return scanner;
    }


    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public User createUser() {
        User user = new User();
        System.out.println("Введите логин:");
        String login = getScanner().nextLine();
        user.setLogin(login);
        System.out.println("Введите пароль:");
        String password = getScanner().nextLine();
        user.setPassword(password);
        System.out.println("Введите имя:");
        String firstName = getScanner().nextLine();
        user.setFirstName(firstName);
        System.out.println("Введите фамилию:");
        String lastName = getScanner().nextLine();
        user.setLastName(lastName);
        System.out.println("Введите свой возвраст:");
        boolean flag = true;
        while (flag) {
            int age = getScanner().nextInt();
            if (age > 0 && age < 100) {
                user.setAge(age);
                flag = false;
            }
        }
        System.out.println("Введите свой пол:\n1 - М\n2 - Ж");
        flag = true;
        while (flag) {
            int num = getScanner().nextInt();
            if (num == 1) {
                user.setSex(Sex.MAN);
                flag = false;
            } else if (num == 2) {
                user.setSex(Sex.WOMAN);
                flag = false;
            }
        }
        System.out.println("Введите информацию о себе:");
        String description = getScanner().nextLine();
        user.setDescription(description);
        scanner.close();
        return user;
    }

    public void start() {
        System.out.println("Добро пожаловать!\n1.Вход\n2.Регистрация");
        int num = getScanner().nextInt();
        switch (num) {
            case 1: {
                UserServiceImpl user = new UserServiceImpl();
                try {
                    user.userEnter();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                UserServiceImpl user = new UserServiceImpl();
                User user1 = createUser();
                try {
                    user.addUser(user1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            break;
            default: {
                System.out.println("Неверный ввод. Попробуйте еще раз!");
                start();
            }
        }
    }

    public String getPropertyValue(String propertyName) {
        String propertyValue = "";
        Properties properties = new Properties();

        try(InputStream is = this.getClass().getResourceAsStream("prop.properties")) {
            properties.load(is);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }
}
