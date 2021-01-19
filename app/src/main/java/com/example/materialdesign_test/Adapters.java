package com.example.materialdesign_test;

public class Adapters {
    private String names,username,phoneno,password;

    public Adapters() {
    }

    public Adapters(String names, String username, String phoneno, String password) {
        this.names = names;
        this.username = username;
        this.phoneno = phoneno;
        this.password = password;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getUsername1() {
        return username;
    }

    public void setUsername1(String username1) {
        this.username = username1;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword1() {
        return password;
    }

    public void setPassword1(String password1) {
        this.password = password1;
    }
}
