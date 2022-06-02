package com.example.myapplication;

public class UserMessage {
    private String name;
    private String surname;
    private String text;

    public UserMessage(String name, String surname, String text){

        this.name=name;
        this.surname = surname;
        this.text=text;
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

    public String gettext() {
        return this.text;
    }

    public void settext(String text) {
        this.text = text;
    }
}
