package operations;

import constants.Constants;
import entity.Account;
import exceptions.BalanceException;
import exceptions.SameAccountException;
import exceptions.MysqlException;
import runner.Runner;
import system.BankSystem;

import java.sql.SQLException;

public class Interface {

    private BankSystem bankSystem = BankSystem.getInstance();

    public Interface() throws MysqlException {
    }

    public static String openNewAccount() throws MysqlException {


        System.out.println("Enter your name : ");
        String name = Constants.getScanner().next();
//        BankSystem bankSystem = BankSystem.getInstance();

        return BankSystem.openNewAccount(name);
    }

    public static void signIn() throws SQLException, MysqlException {

        System.out.println("Enter user name : ");
        String userName = Constants.getScanner().next();

        System.out.println("Enter password ");
        String password = Constants.getScanner().next();

//        BankSystem bankSystem = BankSystem.getInstance();

        Account account = null;

        try {
            account = BankSystem.signIn(userName,password);
        } catch (SQLException e) {
            System.out.println("user name or password are incorrect");
            signIn();
        }

        catch (NullPointerException e){
            System.out.println("user name or password are incorrect");
            signIn();
        }

        int choice = -1;

        System.out.println();
        while (choice != 6) {
            System.out.println("Welcome " + account.getUserName());
            System.out.println("Account id : " + account.getId());
            System.out.println();
            System.out.println("    1 - add deposit");
            System.out.println("    2 - withdraw money");
            System.out.println("    3 - transfer money");
            System.out.println("    4 - check balance");
            System.out.println("    5 - close account");
            System.out.println("    6 - exit");
            while (!Constants.getScanner().hasNextInt()){}

            choice = Constants.getScanner().nextInt();
            switch (choice){
                case 1 -> {
                    addDeposit(account);
                }

                case 2 -> {
                    withdrawMoney(account);
                }

                case 3 -> {
                    transferMoney(account);
                }

                case 4 -> {
                    System.out.println(checkBalance(account));
                    System.out.println();
                }

                case 5 -> {
                    System.out.println(BankSystem.closeAccount(account));
                    Runner.run();
                }

                case 6 -> {
                    Runner.run();
                }
            }
        }



    }

    private static String checkBalance(Account account) {


        return BankSystem.checkBalance(account);

    }

    private static void transferMoney(Account account) {
        System.out.println("give the account id that you want to transfer to :");
        while (!Constants.getScanner().hasNext()) {
        }
        String accountId = Constants.getScanner().next();
        waitForAmount();

        try {
            System.out.println(BankSystem.transferMoney(account, accountId, Constants.getScanner().nextDouble()));
        } catch (BalanceException e) {
            System.out.println(e.getMessage());
            waitForAmount();
        } catch (SQLException e) {
            System.out.println("account not found");
            transferMoney(account);
        } catch (SameAccountException e) {
            System.out.println(e.getMessage());
            transferMoney(account);
        }
        System.out.println();
    }

    private static void waitForAmount() {
        System.out.println("give me amount you want to transfer : ");
        while (!Constants.getScanner().hasNextDouble()) {
        }
    }


    private static void withdrawMoney(Account account) throws SQLException {
        System.out.println("give me amount to withdraw : ");
        while (!Constants.getScanner().hasNextDouble()){}
        try {
            System.out.println(BankSystem.withdrawMoney(account,Constants.getScanner().nextDouble()));
        } catch (BalanceException e) {
            System.out.println(e.getMessage());
            withdrawMoney(account);
        }
        System.out.println();
    }

    private static void addDeposit(Account account) throws SQLException {
        System.out.println("give me amount to add : ");
        while (!Constants.getScanner().hasNextDouble()){}
        System.out.println(BankSystem.addDeposit(account,Constants.getScanner().nextDouble()));
        System.out.println();
    }

}
