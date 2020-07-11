package com.atguigu1.transaction;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/30 21:00
 */
public class User {
    private String user;
    private String password;
    private int balance;

    public User() {
    }

    public User(String user, String passwird, int balance) {
        this.user = user;
        this.password = passwird;
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswird() {
        return password;
    }

    public void setPasswird(String passwird) {
        this.password = passwird;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", passwird='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
