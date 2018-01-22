package com.example.marek.komunikator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marek on 21.01.18.
 */

public class ContactList {
    private static List<Contact> contactList = new ArrayList<>();

    public static void addToList(Contact contact){
        contactList.add(contact);
    }

    public static List<Contact> getContactList() {
        return contactList;
    }

    public static void setContactList(List<Contact> contactList) {
        ContactList.contactList = contactList;
    }

    public static void changeContactStatus(String contactName, boolean active){
        for (Contact c : contactList) {
            if (c.getLogin().equals(contactName)){
                c.setActive(active);
            }
        }
    }

    public static void changeContactNewMessages(String contactName, boolean newMessage){
        for (Contact c :contactList){
            if (c.getLogin().equals(contactName)){
                c.setNewMessage(newMessage);
            }
        }
    }
}
