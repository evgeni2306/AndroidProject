package com.example.myapplication;

public class UserSearch {
    private String name;
    private String surname;
    private String id;

    public UserSearch(String name, String surname, String id){

        this.name=name;
        this.surname = surname;
        this.id=id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getid() {
        return this.id;
    }

    public void setid(String id) {
        this.id = id;
    }
}
