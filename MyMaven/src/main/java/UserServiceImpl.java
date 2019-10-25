import java.sql.*;


public class UserServiceImpl extends UserService {

    public User createUser() throws SQLException {
        User user = new User();
        System.out.println("Введите логин:");
        user.setLogin(checkUser());
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
            } else {
                System.out.println("Введите свой пол:\n1 - М\n2 - Ж");
            }
        }
        System.out.println("Введите информацию о себе:");
        String description = getScanner().nextLine();
        user.setDescription(description);
        scanner.close();
        return user;
    }

    public void start() {
        System.out.println("Добро пожаловать!\n1.Вход\n2.Регистрация\n0.Выход");
        boolean flag = true;
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
                try {
                    User user1 = createUser();
                    user.addUser(user1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 0: {
                System.out.println("Завершение работы...");
                break;
            }
            default: {
                System.out.println("Неверный ввод. Попробуйте еще раз!");
                start();
            }
        }
    }


    public String checkUser() {
        String login = getScanner().nextLine();
        boolean flag = true;
        while (flag) {
            String checkLogin = "SELECT * FROM USER WHERE login='" + login + "';";
            Statement statement = null;
            try {
                statement = getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(checkLogin);
                if (resultSet.first()) {
                    String loginDB = resultSet.getString("login");
                    if (login.equals(loginDB)) {
                        System.out.println("Такой пользователь уже зарегистрирован. Попробуйте ввести другой логин");
                        login = getScanner().nextLine();
                    }
                } else {
                    flag = false;

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return login;
    }
}
