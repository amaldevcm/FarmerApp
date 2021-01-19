package com.example.materialdesign_test;

public class Adapter {
    private String name,username,phoneno,password;

    public Adapter() {
    }

    public Adapter(String name, String username, String phoneno, String password) {
        this.name = name;
        this.username = username;
        this.phoneno = phoneno;
        this.password = password;
    }

    public String getname() {
        return name;
    }

    public void setame(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
