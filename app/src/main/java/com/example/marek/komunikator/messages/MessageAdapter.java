package com.example.marek.komunikator.messages;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marek.komunikator.R;
import com.example.marek.komunikator.model.Message;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private static class ViewHolder {
        TextView content;
        TextView date;
        LinearLayout wrapper;
    }

    public MessageAdapter(@NonNull Context context, @NonNull List<Message> objects) {
        super(context, R.layout.my_message_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Message message = getItem(position);

//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(getContext())
//                    .inflate(R.layout.my_message_item, parent, false);
//
//            viewHolder.content = (TextView) convertView.findViewById(R.id.message);
//            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
//            viewHolder.wrapper = (LinearLayout) convertView.findViewById(R.id.message_wrapper);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.my_message_item, parent, false);
        }

        TextView content = (TextView) convertView.findViewById(R.id.message);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        LinearLayout wrapper = (LinearLayout) convertView.findViewById(R.id.message_wrapper);

        if (message != null) {
            content.setText(message.getContent());
            date.setText(message.getSendingDate());

            if (!message.isMy()){
                wrapper.setGravity(Gravity.START);
                content.setBackground(convertView.getResources().getDrawable(R.drawable.your_message, null));
            } else {
                wrapper.setGravity(Gravity.END);
                content.setBackground(convertView.getResources().getDrawable(R.drawable.my_message, null));
            }
        }

//        if (message != null && viewHolder != null) {
//            String s = message.getContent() + " " + position;
//            viewHolder.content.setText(s);
//            viewHolder.date.setText(message.getSendingDate());
//
//            if (!message.isMy()){
//                viewHolder.wrapper.setGravity(Gravity.START);
//                viewHolder.content.setBackground(convertView.getResources().getDrawable(R.drawable.your_message, null));
//            }
//        }

        return convertView;
    }

}
