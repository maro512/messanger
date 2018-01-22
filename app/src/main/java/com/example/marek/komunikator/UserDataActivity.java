package com.example.marek.komunikator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.marek.komunikator.userSettings.tasks.UserLoginRegisterTask;

public class UserDataActivity extends AppCompatActivity {
    private UserLoginRegisterTask mAuthTask = null;

    private EditText mFirstName;
    private EditText mSurname;
    private EditText mAge;
    private Spinner mGender;
    private EditText mCity;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        mFirstName = (EditText) findViewById(R.id.first_name);
        mSurname = (EditText) findViewById(R.id.surname);
        mAge = (EditText) findViewById(R.id.age);
        mGender = (Spinner) findViewById(R.id.gender);
        mCity = (EditText) findViewById(R.id.city);

        Button button = (Button) findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mFirstName.setError(null);
        mSurname.setError(null);
        mAge.setError(null);
        mCity.setError(null);


        // Store values at the time of the login attempt.
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        String age = mAge.getText().toString();
        String city = mCity.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(firstName) && !isName(firstName)) {
            mFirstName.setError(getString(R.string.error_invalid_name));
            focusView = mFirstName;
            cancel = true;
        }

        if (!TextUtils.isEmpty(surname) && !isSurname(surname)) {
            mSurname.setError(getString(R.string.error_invalid_name));
            focusView = mSurname;
            cancel = true;
        }

//        // Check for a valid login.
//        if (TextUtils.isEmpty(login)) {
//            mLoginView.setError(getString(R.string.error_field_required));
//            focusView = mLoginView;
//            cancel = true;
//        } else if (!isLoginValid(login)) {
//            mLoginView.setError(getString(R.string.error_invalid_login));
//            focusView = mLoginView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginRegisterTask(login, password);
//            mAuthTask.setLoginActivity(this);
//            mAuthTask.execute((Void) null);
//        }
    }

    private boolean isSurname(String surname) {
        String surnameRegex = "[a-zA-Z]+-?[a-zA-Z]+";
        return surname.matches(surnameRegex);
    }

    private boolean isName(String name){
        char[] chars = name.toCharArray();

        for(char c : chars){
            if (!Character.isLetter(c)){
                return false;
            }
        }

        return name.length() > 2;
    }
}
