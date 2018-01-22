package com.example.marek.komunikator.userSettings.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.marek.komunikator.PremiumActivity;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.User;
import com.example.marek.komunikator.userSettings.fields.EmailField;
import com.example.marek.komunikator.userSettings.fields.Field;
import com.example.marek.komunikator.userSettings.fields.LoginField;
import com.example.marek.komunikator.userSettings.tasks.UserRemindPasswordTask;

public class RemindPasswordActivity extends UserActivity {
    private Field loginField;
    private Field emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText login = (EditText) findViewById(R.id.login);
        loginField = new LoginField(login);

        EditText email = (EditText) findViewById(R.id.email);
        emailField = new EmailField(email);

        findViewById(R.id.password_wrapper).setVisibility(View.GONE);
        findViewById(R.id.old_password_wrapper).setVisibility(View.GONE);
        findViewById(R.id.password_repeat_wrapper).setVisibility(View.GONE);
        findViewById(R.id.button2).setVisibility(View.GONE);

        Button mResetButton = (Button) findViewById(R.id.button1);
        mResetButton.setText(R.string.reset);
        mResetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                executeRequest();
            }
        });

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void executeAfterResponse(boolean success) {
        finish();
        Intent intent = new Intent(RemindPasswordActivity.this, PremiumActivity.class);
        startActivity(intent);
    }

    @Override
    protected void executeRequest() {
        if (mAuthTask != null) {
            return;
        }

        focusView = null;

        checkEmailField();
        checkLoginField();

        if (focusView != null) {
            focusView.setFocus();
        } else {
            sendRequest();
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
        mAuthTask = new UserRemindPasswordTask(null, this);
        mAuthTask.execute((Void) null);
    }
}

