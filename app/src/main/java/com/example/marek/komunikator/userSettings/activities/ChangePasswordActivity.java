package com.example.marek.komunikator.userSettings.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marek.komunikator.PremiumActivity;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.User;
import com.example.marek.komunikator.userSettings.fields.Field;
import com.example.marek.komunikator.userSettings.fields.PasswordField;
import com.example.marek.komunikator.userSettings.tasks.UserChangePasswordTask;

public class ChangePasswordActivity extends UserActivity {
    private Field oldPasswordField;
    private Field newPasswordField;
    private Field passwordRepeatField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText oldPassword = (EditText) findViewById(R.id.old_password);
        oldPasswordField = new PasswordField(oldPassword);

        EditText password = (EditText) findViewById(R.id.password);
//        password.setHint(R.string.prompt_new_password);
        newPasswordField = new PasswordField(password);

        EditText passwordRepeat = (EditText) findViewById(R.id.password_repeat);
        passwordRepeatField = new PasswordField(passwordRepeat);

        findViewById(R.id.login_wrapper).setVisibility(View.GONE);
        findViewById(R.id.email_wrapper).setVisibility(View.GONE);

        Button mChangePasswordButton = (Button) findViewById(R.id.button1);
        mChangePasswordButton.setText(R.string.action_change_password);
        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeRequest();
            }
        });

        findViewById(R.id.button2).setVisibility(View.GONE);
    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void executeAfterResponse(boolean success) {
        if (success) {
            finish();
            Intent intent = new Intent(ChangePasswordActivity.this, PremiumActivity.class);
            startActivity(intent);
        } else {
            oldPasswordField.setError(getString(R.string.error_incorrect_password));
            oldPasswordField.setFocus();
        }
    }

    @Override
    protected void executeRequest() {
        if (mAuthTask != null) {
            return;
        }

        focusView = null;

        checkRepeatPasswordField();
        checkPasswordField(newPasswordField);
        checkPasswordField(oldPasswordField);

        if (focusView != null) {
            focusView.setFocus();
        } else {
            sendRequest();
        }
    }

    private void checkRepeatPasswordField(){
        passwordRepeatField.resetErrors();

        if (passwordRepeatField.isEmpty()
                || !passwordRepeatField.getText().equals(newPasswordField.getText())){
            passwordRepeatField.setError(getString(R.string.error_password_not_match));
            focusView = passwordRepeatField;
        }
    }

    private void checkPasswordField(Field passwordField){
        passwordField.resetErrors();

        if (passwordField.isEmpty()){
            passwordField.setError(getString(R.string.error_field_required));
            focusView = passwordField;
        } else if (!passwordField.isValid()){
            passwordField.setError(getString(R.string.error_invalid_password));
            focusView = passwordField;
        }
    }

    private void sendRequest(){
        mAuthTask = new UserChangePasswordTask(null, this);
        mAuthTask.execute((Void) null);
    }
}
