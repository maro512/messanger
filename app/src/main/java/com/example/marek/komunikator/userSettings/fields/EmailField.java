package com.example.marek.komunikator.userSettings.fields;

import android.widget.EditText;

public class EmailField extends Field {
    public EmailField(EditText editText) {
        super(editText);
    }

    @Override
    public boolean isValid() {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return getText().matches(emailRegex);
    }
}
