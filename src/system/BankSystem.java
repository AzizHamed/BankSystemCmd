package system;

import constants.Constants;
import entity.Account;
import entity.User;
import exceptions.BalanceException;
import exceptions.SameAccountException;
import exceptions.MysqlException;
import jdbc.Sql;

import java.sql.SQLException;
import java.util.Random;

// bank system server
//singleton pattern

public class BankSystem {

    private static BankSystem bankSystem = null;

    private BankSystem(){


    }


    public static BankSystem getInstance() throws MysqlException {
        if(bankSystem == null) {
            bankSystem = new BankSystem();
            Sql sql = new Sql();
        }

        return bankSystem;
    }


    //open new Account function
    // takes the name of user and it opens a new account with random id and save user with random id
    public static String openNewAccount(String name) throws MysqlException {

        StringBuilder id = new StringBuilder();
        String letters = Constants.getLetters();
        Random random = Constants.getRandom();

        for(int i=0 ; i<8 ; i++)
            id.append(letters.charAt(random.nextInt(61)));

        User user = new User(id.toString(),name);

        StringBuilder password = new StringBuilder();
        for(int i=0 ; i<8 ; i++)
            password.append(letters.charAt(random.nextInt(61)));

        StringBuilder accountId = new StringBuilder();
        for(int i=0 ; i<6 ; i++)
            accountId.append(letters.charAt(random.nextInt(61)));

        Account account = new Account(accountId.toString(),0,id.toString(),name,password.toString());

        Sql sql = new Sql();
        user.setAccount(account);
        Sql.insertUser(user);

        return "Account created with username = " + name + " and password = " + password;
    }

    //sign in method
    //takes user name and password as a params and checks if there is account with these params in the database
    public static Account signIn(String userName, String password) throws MysqlException, SQLException {

        Account account;

        account = Sql.checkForSignin(userName,password);

        return account;
    }


    // Add deposit to account
    // takes account and amount as a params and add amount to account deposit
    public static String addDeposit(Account account, double amount) throws SQLException {


        Sql.addDeposit(account,amount + account.getBalance());
        account.setBalance(amount + account.getBalance());
        return amount + "$ added to balance";
    }


    // Withdraw money
    //takes an account and amount as a params and withdraw money from account
    public static String withdrawMoney(Account account, double amount) throws BalanceException, SQLException {
        if(account.getBalance() < amount )
            throw new BalanceException("Your balance is smaller than " + amount);
        Sql.withdrawMoney(account, account.getBalance() - amount);
        account.setBalance(account.getBalance() - amount);
        return amount + "$ withdrawn from your account";
    }
    // Transfer money to another account
    //takes an account of teh sender , account id of the receiver , amount as a params and takes money from sender account and puts them in the receiver account
    public static String transferMoney(Account account, String accountId, double amount) throws SQLException, BalanceException, SameAccountException {

        if(account.getId().equals(accountId))
            throw new SameAccountException("this is your account, you cant transfer to your account");
        if(account.getBalance() < amount)
            throw new BalanceException("Your balance is smaller than " + amount);
        Account ReceiverAccount = Sql.getAccount(accountId);


        Sql.transferMoney(account,accountId,ReceiverAccount.getBalance() + amount, amount);
        account.setBalance(account.getBalance() - amount);
        return amount + "$ transferred to account with id : " + accountId;

    }


    //check the balance of the account
    //takes account and return its balance
    public static String checkBalance(Account account) {
        return "you have " + account.getBalance() + "$ in your account";
    }


    //Close account
    //takes an account as a param and delete it from the database
    public static String closeAccount(Account account) throws SQLException {

        Sql.closeAccount(account);
        String message = null;
        if(account.getBalance() > 0)
            message = "You have " + account.getBalance() + "$ take them, ";
        message += "account with id : " + account.getId()  + "has been closed successfully";
        return message;
    }
}
