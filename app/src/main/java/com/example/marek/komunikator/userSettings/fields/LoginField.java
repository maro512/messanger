package com.example.marek.komunikator.userSettings.fields;

import android.widget.EditText;

public class LoginField extends Field {
    public LoginField(EditText editText) {
        super(editText);
    }

    @Override
    public boolean isValid() {
        char[] chars = getText().toCharArray();

        for(char c : chars){
            if (!Character.isLetterOrDigit(c)){
                return false;
            }
        }

        return chars.length > 3;
    }

}
