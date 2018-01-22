package com.example.marek.komunikator.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.messages.MessageActivity;
import com.example.marek.komunikator.model.Contact;
import com.example.marek.komunikator.model.ContactList;
import com.example.marek.komunikator.userSettings.tasks.GetMessageTask;
import com.example.marek.komunikator.userSettings.tasks.ResponseTask;

import java.util.List;

public class ContactsActivity extends MyBaseActivity {
    private ContactAdapter contacts;
    protected ResponseTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsActivity.this, FindContactActivity.class);
                startActivity(intent);
            }
        });

        List<Contact> contactList = ContactList.getContactList();

        contacts = new ContactAdapter(this, contactList);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(contacts);
    }

    @Override
    public void onResponse(Response response) {
        if (response.getApiPath().equals("/getMessages")){
            mAuthTask = new GetMessageTask(response, this);
            mAuthTask.execute((Void) null);
        }
    }

    public void updateContacts(){
        contacts.notifyDataSetChanged();
    }

    @Override
    public void resetDataAfterResponse() {
        mAuthTask = null;
    }

    @Override
    public void executeAfterResponse(boolean success) {
        if (success){
            contacts.notifyDataSetChanged();
            Intent intent = new Intent(ContactsActivity.this, MessageActivity.class);
            startActivity(intent);
        }
    }

}
