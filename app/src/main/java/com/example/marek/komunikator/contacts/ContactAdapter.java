package com.example.marek.komunikator.contacts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marek.komunikator.ActivityManager;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.controllers.config.Request;
import com.example.marek.komunikator.model.Contact;
import com.example.marek.komunikator.model.ContactList;
import com.example.marek.komunikator.model.MessageList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private static class ViewHolder {
        ImageView ivOn;
        ImageView ivOff;
        TextView name;
    }

    public ContactAdapter(@NonNull Context context, @NonNull List<Contact> objects) {
        super(context, R.layout.contact_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Contact contact = getItem(position);
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(getContext())
//                    .inflate(R.layout.contact_item, parent, false);
//
//            viewHolder.name = (TextView) convertView.findViewById(R.id.contact_name);
//            viewHolder.name.setTag(contact);
//            viewHolder.name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showToast(contact != null ? contact.getLogin() : "pusty kontakt");
//                }
//            });
//            viewHolder.ivOn = (ImageView) convertView.findViewById(R.id.imageViewOn);
//            viewHolder.ivOff = (ImageView) convertView.findViewById(R.id.imageViewOff);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        if (contact != null && viewHolder != null) {
//            viewHolder.name.setText(contact.getLogin());
//            if (contact.isActive()){
//                viewHolder.ivOff.setVisibility(View.GONE);
//                viewHolder.ivOn.setVisibility(View.VISIBLE);
//            }
//        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.contact_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.contact_name);
        name.setTag(contact);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request("/getMessages");

                try {
                    String body = null;
                    if (contact != null) {
                        body = new JSONObject().put("userNick", contact.getLogin()).toString();
                        MessageList.friendName = contact.getLogin();
                        ContactList.changeContactNewMessages(contact.getLogin(), false);
                    }
                    request.setBody(body);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ActivityManager.webSocket.send(request.getRequest());
            }
        });

        ImageView ivOn = (ImageView) convertView.findViewById(R.id.imageViewOn);
        ImageView ivOff = (ImageView) convertView.findViewById(R.id.imageViewOff);

        if (contact != null) {
            name.setText(contact.getLogin());

            if (contact.isNewMessage()){
                name.setTypeface(null, Typeface.BOLD);
            } else {
                name.setTypeface(null, Typeface.NORMAL);
            }

            if (contact.isActive()){
                ivOff.setVisibility(View.GONE);
                ivOn.setVisibility(View.VISIBLE);
            } else {
                ivOff.setVisibility(View.VISIBLE);
                ivOn.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    private void showToast(CharSequence text){
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(getContext(), text, duration);
        toast.show();
    }
}
