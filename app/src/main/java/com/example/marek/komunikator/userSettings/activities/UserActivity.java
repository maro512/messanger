package com.example.marek.komunikator.userSettings.activities;

import android.os.Bundle;
import android.view.View;

import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.userSettings.fields.Field;
import com.example.marek.komunikator.userSettings.tasks.ResponseTask;

public abstract class UserActivity extends MyBaseActivity {
    protected ResponseTask mAuthTask = null;
    protected Field focusView = null;

    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    protected void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void resetDataAfterResponse(){
        mAuthTask = null;
        showProgress(false);
    }

    public abstract void executeAfterResponse(boolean success);

    protected abstract void executeRequest();
}
