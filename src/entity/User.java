package entity;

public class User {

    private String  id;

    private String name;


    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private Account account;

    public String getId() {
        return id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }
}
