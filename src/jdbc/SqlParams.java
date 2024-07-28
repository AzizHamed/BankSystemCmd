package jdbc;

public class SqlParams {

    private static String url = "jdbc:mysql://localhost:3306/banksystem";

    private static String userName = "root";

    private static String password = "1234";

    public static String getUrl() {
        return url;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }
}
