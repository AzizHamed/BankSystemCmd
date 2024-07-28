package runner;

import constants.Constants;
import exceptions.MysqlException;
import operations.Interface;

import java.sql.SQLException;

public class Runner {

    public static void run() throws MysqlException, SQLException {
        int choice=0;

        while (true){
            System.out.println("1 - Open new Account");
            System.out.println("2 - Sign in");
            while (!Constants.getScanner().hasNextInt()){}
            choice = Constants.getScanner().nextInt();
            switch (choice){
                case 1 -> {
                    System.out.println(Interface.openNewAccount());
                }

                case 2 ->{
                    Interface.signIn();
                }

                default ->{
                        System.out.println("Unknown choice");
                }
            }
        }
    }
}
