package com.example.marek.komunikator.userSettings.fields;

import android.widget.EditText;

public class PasswordField extends Field {
    public PasswordField(EditText editText) {
        super(editText);
    }

    @Override
    public boolean isValid() {
        return getText().length() > 4;
    }
}
