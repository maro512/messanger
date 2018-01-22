package com.example.marek.komunikator.userSettings.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.marek.komunikator.ActivityManager;
import com.example.marek.komunikator.contacts.ContactsActivity;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.controllers.config.Request;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.userSettings.fields.EmailField;
import com.example.marek.komunikator.userSettings.fields.Field;
import com.example.marek.komunikator.userSettings.fields.LoginField;
import com.example.marek.komunikator.userSettings.fields.PasswordField;
import com.example.marek.komunikator.userSettings.tasks.UserLoginRegisterTask;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends UserActivity {
    private Field loginField;
    private Field emailField;
    private Field passwordField;
    private Field passwordRepeatField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText login = (EditText) findViewById(R.id.login);
        loginField = new LoginField(login);

        EditText email = (EditText) findViewById(R.id.email);
        emailField = new EmailField(email);

        EditText password = (EditText) findViewById(R.id.password);
        passwordField = new PasswordField(password);

        EditText passwordRepeat = (EditText) findViewById(R.id.password_repeat);
        passwordRepeatField = new PasswordField(passwordRepeat);

        findViewById(R.id.old_password_wrapper).setVisibility(View.GONE);
        findViewById(R.id.button2).setVisibility(View.GONE);

        Button mRegisterButton = (Button) findViewById(R.id.button1);
        mRegisterButton.setText(R.string.action_register);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                executeRequest();
            }
        });
    }

    @Override
    public void onResponse(Response response) {
        if (response.getApiPath().equals("/register")){
            mAuthTask = new UserLoginRegisterTask(response, this);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void executeAfterResponse(boolean success) {
        if (success) {
            Intent intent = new Intent(RegisterActivity.this, ContactsActivity.class);
            startActivity(intent);
            finish();
        } else {
            loginField.setError(getString(R.string.error_invalid_login));
            loginField.setFocus();
        }
    }

    @Override
    protected void executeRequest() {
        if (mAuthTask != null) {
            return;
        }

        focusView = null;

        checkRepeatPasswordField();
        checkPasswordField();
        checkEmailField();
        checkLoginField();

        if (focusView != null) {
            focusView.setFocus();
        } else {
            sendRequest();
        }
    }

    private void checkRepeatPasswordField(){
        passwordRepeatField.resetErrors();

        if (passwordRepeatField.isEmpty()
                || !passwordRepeatField.getText().equals(passwordField.getText())){
            passwordRepeatField.setError(getString(R.string.error_password_not_match));
            focusView = passwordRepeatField;
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

    private void checkEmailField(){
        emailField.resetErrors();

        if (emailField.isEmpty()){
            emailField.setError(getString(R.string.error_field_required));
            focusView = emailField;
        } else if (!emailField.isValid()){
            emailField.setError(getString(R.string.error_invalid_email));
            focusView = emailField;
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

    private void sendRequest(){
        showProgress(true);

        Request request = new Request("/register");

        try {
            String body = new JSONObject().put("nick", loginField.getText())
                    .put("password", passwordField.getText())
                    .put("mail", emailField.getText()).toString();
            request.setBody(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ActivityManager.webSocket.send(request.getRequest());
    }

}

