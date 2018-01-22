package com.example.marek.komunikator.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by marek on 15.01.18.
 */

public class Message {
    private String content;
    private Date sendingDate;
    private boolean isMy;

    public Message(String content, Date sendingDate) {
        this.content = content;
        this.sendingDate = sendingDate;
        isMy = true;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendingDate() {
        DateFormat df = DateFormat.getDateTimeInstance();
        return df.format(sendingDate);
    }

    public void setSendingDate(String sendingDate) {
        DateFormat df = DateFormat.getDateTimeInstance();
        try {
            this.sendingDate = df.parse(sendingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setSendingDateFromTimestamp(String sendingDateFromTimestamp){
        this.sendingDate = new Date(Long.parseLong(sendingDateFromTimestamp));
    }

    public boolean isMy() {
        return isMy;
    }

    public void setMy(boolean my) {
        isMy = my;
    }
}
