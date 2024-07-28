package entity;

public class Account {

    private String id;

    private double balance;


    private String userId;


    private String userName;

    private String password;

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account(String id, double balance, String userId, String userName, String password) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
