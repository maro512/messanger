package com.example.marek.komunikator.userSettings.fields;

import android.text.TextUtils;
import android.widget.EditText;

public abstract class Field implements Validator {
    private EditText editText;

    public Field(EditText editText) {
        this.editText = editText;
    }

    @Override
    public boolean isEmpty() {
        return TextUtils.isEmpty(getText());
    }

    public void setFocus(){
        editText.requestFocus();
    }

    public void setError(CharSequence error){
        editText.setError(error);
    }

    public void resetErrors(){
        setError(null);
    }

    public String getText(){
        return editText.getText().toString();
    }
}
