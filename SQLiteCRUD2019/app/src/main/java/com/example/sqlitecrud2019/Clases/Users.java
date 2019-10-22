package com.example.sqlitecrud2019.Clases;

public class Users {

    public int Id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public Users(int id, String firstName, String lastName, String email, String password) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
