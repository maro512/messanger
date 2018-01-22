package com.example.marek.komunikator.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marek.komunikator.ActivityManager;
import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.controllers.config.Request;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.userSettings.fields.Field;
import com.example.marek.komunikator.userSettings.fields.LoginField;
import com.example.marek.komunikator.userSettings.tasks.AddFriendTask;
import com.example.marek.komunikator.userSettings.tasks.ResponseTask;

import org.json.JSONException;
import org.json.JSONObject;

public class FindContactActivity extends MyBaseActivity {
    private Field nameField;
    private Field focusView;
    private ResponseTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_contact);

        EditText name = (EditText) findViewById(R.id.contact_name);
        nameField = new LoginField(name);

        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRequest();
            }
        });
    }

    @Override
    public void onResponse(Response response) {
        if (response.getApiPath().equals("/addToFriend")){

            mAuthTask = new AddFriendTask(response, this);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void resetDataAfterResponse() {

    }

    @Override
    public void executeAfterResponse(boolean success) {
        if (success){
            finish();
        } else {
            nameField.setFocus();
            nameField.setError(getString(R.string.error_invalid_login));
        }
    }

    private void executeRequest() {
        focusView = null;

        checkLoginField();

        if (focusView != null) {
            focusView.setFocus();
        } else {
            sendRequest();
        }
    }

    private void sendRequest() {
//        showProgress(true);
        Request request = new Request("/addToFriend");

        try {
            String body = new JSONObject().put("userNick", nameField.getText()).toString();
            request.setBody(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ActivityManager.webSocket.send(request.getRequest());
//        finish();
//        Intent intent = new Intent(FindContactActivity.this, PremiumActivity.class);
//        startActivity(intent);
    }

    private void checkLoginField(){
        nameField.setError(null);

        if (nameField.isEmpty()){
            nameField.setError(getString(R.string.error_field_required));
            focusView = nameField;
        } else if (!nameField.isValid()){
            nameField.setError(getString(R.string.error_invalid_login));
            focusView = nameField;
        }
    }
}
