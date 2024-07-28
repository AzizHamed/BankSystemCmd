package jdbc;

import entity.Account;
import entity.User;
import exceptions.MysqlException;

import java.sql.*;

public class Sql {

    private static Connection connection;
    public Sql() throws MysqlException {
        try {
            connection = DriverManager.getConnection(SqlParams.getUrl(),SqlParams.getUserName(),SqlParams.getPassword());
        } catch (SQLException e) {
            throw new MysqlException("Connection");
        }
    }



    // add new user to database
    public static String insertUser(User user) throws MysqlException {
        String sql = "INSERT INTO user (id,name,accountId) VALUES (?,?,?)";

        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getId());
            ps.setString(2,user.getName());
            ps.setString(3,user.getAccount().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MysqlException("prepared statement");
        }
        insertAccount(user.getAccount());
        return "Created new User Account";
    }



    //adds new account to database
    public static void insertAccount(Account account) throws MysqlException {
        String sql = "INSERT INTO account (id,balance,user_id,username,password) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,account.getId());
            ps.setDouble(2,account.getBalance());
            ps.setString(3,account.getUserId());
            ps.setString(4,account.getUserName());
            ps.setString(5,account.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MysqlException("CREATE ACCOUNT");
        }
    }


    //get the suitable account for these user name and password and return account object , if account not found it throw an exception
    public static Account checkForSignin(String userName, String password) throws MysqlException, SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        Account account;

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,userName);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            rs.next();
            account = new Account(rs.getString(1), rs.getDouble(2),rs.getString(3),rs.getString(4), rs.getString(5));




        return account;



    }


    // Adds deposit to account balance
    public static void addDeposit(Account account, double amount) throws SQLException {



        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1,amount);
        ps.setString(2,account.getId());
        ps.executeUpdate();
    }


    // Withdraws money from account
    public static void withdrawMoney(Account account, double amount) throws SQLException {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1,amount);
        ps.setString(2,account.getId());
        ps.executeUpdate();
    }


    // returns the account with the specific id , if there is no accounts found throw an exception
    public static Account getAccount(String accountId) throws SQLException {

        String sql = "SELECT * FROM account WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,accountId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new Account(rs.getString(1),rs.getDouble(2),rs.getString(3),rs.getString(4),rs.getString(5));
    }


    // transfer money from account to account
    public static void transferMoney(Account account, String accountId, double newBalance, double amount) throws SQLException {

        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1,newBalance);
        ps.setString(2,accountId);
        ps.executeUpdate();

        String sql2 = "UPDATE ACCOUNT SET balance = ? WHERE id = ?";
        ps = connection.prepareStatement(sql2);
        ps.setDouble(1,account.getBalance() - amount);
        ps.setString(2, account.getId());
        ps.executeUpdate();

    }


    //deletes account from the database
    public static void closeAccount(Account account) throws SQLException {

        String sql = "DELETE FROM account WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,account.getId());
        ps.executeUpdate();

        String sql2 = "DELETE FROM user WHERE id = ?";
        ps = connection.prepareStatement(sql2);
        ps.setString(1,account.getUserId());
        ps.executeUpdate();
    }
}
