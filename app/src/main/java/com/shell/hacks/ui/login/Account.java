package com.shell.hacks.ui.login;

public class Account {
    private String email;
    private String password;
    private String age;
    private String disability;
    private String phoneNumber;

    Account(String email, String password, String age, String disability, String phoneNumber)
    {
        this.email = email;
        this.password = password;
        this.age = age;
        this.disability = disability;
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }

    public String getDisability() {
        return disability;
    }

    public String getPhoneNumber() { return phoneNumber; }
}


