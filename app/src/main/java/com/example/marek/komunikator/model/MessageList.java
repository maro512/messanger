package com.example.marek.komunikator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marek on 21.01.18.
 */

public class MessageList {
    public static String friendName;
    private static List<Message> messageList = new ArrayList<>();

    public static void addToList(Message message){
        messageList.add(message);
    }

    public static List<Message> getMessageList() {
        return messageList;
    }

    public static void setMessageList(List<Message> messageList) {
        MessageList.messageList = messageList;
    }
}
