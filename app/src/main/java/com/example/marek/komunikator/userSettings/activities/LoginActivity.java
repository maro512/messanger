package com.example.marek.komunikator.userSettings.activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marek.komunikator.ActivityManager;
import com.example.marek.komunikator.contacts.ContactsActivity;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.controllers.config.Request;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.userSettings.fields.Field;
import com.example.marek.komunikator.userSettings.fields.LoginField;
import com.example.marek.komunikator.userSettings.fields.PasswordField;
import com.example.marek.komunikator.userSettings.tasks.UserFriendListTask;
import com.example.marek.komunikator.userSettings.tasks.UserLastActivity;
import com.example.marek.komunikator.userSettings.tasks.UserLoginRegisterTask;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends UserActivity {
    private Field loginField;
    private Field passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText login = (EditText) findViewById(R.id.login);
        loginField = new LoginField(login);

        EditText password = (EditText) findViewById(R.id.password);
        passwordField = new PasswordField(password);

        findViewById(R.id.email_wrapper).setVisibility(View.GONE);
        findViewById(R.id.old_password_wrapper).setVisibility(View.GONE);
        findViewById(R.id.password_repeat_wrapper).setVisibility(View.GONE);

        Button mSignInButton = (Button) findViewById(R.id.button1);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                executeRequest();
            }
        });

        Button mRemindPassword = (Button) findViewById(R.id.button2);
        mRemindPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RemindPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void executeAfterResponse(boolean success) {
        if (success) {
            showProgress(true);
            sendRequestWithoutBody("/friendList");
        } else {
            passwordField.setError(getString(R.string.error_incorrect_password));
            passwordField.setFocus();
        }
    }

    @Override
    protected void executeRequest() {
        if (mAuthTask != null) {
            return;
        }

        focusView = null;

        checkPasswordField();
        checkLoginField();

        if (focusView != null) {
            focusView.setFocus();
        } else {
            sendLoginRequest();
        }
    }

    private void checkPasswordField(){
        passwordField.resetErrors();

        if (passwordField.isEmpty()){
            passwordField.setError(getString(R.string.error_field_required));
            focusView = passwordField;
        } else if (!passwordField.isValid()){
            passwordField.setError(getString(R.string.error_invalid_password));
            focusView = passwordField;
        }
    }

    private void checkLoginField(){
        loginField.resetErrors();

        if (loginField.isEmpty()){
            loginField.setError(getString(R.string.error_field_required));
            focusView = loginField;
        } else if (!loginField.isValid()){
            loginField.setError(getString(R.string.error_invalid_login));
            focusView = loginField;
        }
    }

    private void sendLoginRequest(){
        showProgress(true);
        Request request = new Request("/login");

        try {
            String body = new JSONObject().put("nick", loginField.getText())
                    .put("password", passwordField.getText()).toString();
            request.setBody(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ActivityManager.webSocket.send(request.getRequest());
    }

    private void sendRequestWithoutBody(String apiPath){
        Request request = new Request(apiPath);

        ActivityManager.webSocket.send(request.getRequest());
    }

    public void onResponse(Response response){
        switch (response.getApiPath()){
            case "/login":
                mAuthTask = new UserLoginRegisterTask(response, this);
                break;
            case "/friendList":
                mAuthTask = new UserFriendListTask(response, this);
                break;
            case "/lastActivity":
                mAuthTask = new UserLastActivity(response, this);
                break;
        }
        mAuthTask.execute((Void) null);
    }

    private void showToast(CharSequence text){
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

    public void showUserList() {
        Intent intent = new Intent(LoginActivity.this, ContactsActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendLastActivityRequest() {
        showProgress(true);
        sendRequestWithoutBody("/lastActivity");
    }
}

