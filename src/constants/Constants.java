package constants;

import java.util.Random;
import java.util.Scanner;

public class Constants {

    private static Scanner scanner = new Scanner(System.in);


    private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String getLetters(){
        return letters;
    }

    private static Random random = new Random();
    public static Scanner getScanner() {
        return scanner;
    }

    public static Random getRandom(){
        return random;
    }
}
