package com.example.marek.komunikator.model;

public class Contact {
    private String login;
    private boolean isActive;
    private boolean newMessage;

    public Contact(String login) {
        this.login = login;
        this.isActive = false;
        this.newMessage = false;
    }

    public String getLogin() {
        return login;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }
}
