package com.example.sqlitecrud2019.actividades;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitecrud2019.R;
import com.example.sqlitecrud2019.classes.connectionDB;

public class SignUp extends AppCompatActivity {

    EditText firstName, lastName, eMail, pass, confirm;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String namePattern = "[A-Z][a-z]+( [A-Z][a-z]+)*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.firstNameInput);
        lastName = findViewById(R.id.lastNameInput);
        eMail = findViewById(R.id.emailInput);
        pass = findViewById(R.id.passInput);
        confirm = findViewById(R.id.confirmInput);
    }
    public void Register(View view){
        connectionDB manager = new connectionDB(this,
                "bd", null, 1);
        SQLiteDatabase database = manager.getWritableDatabase();

        String FNAME = firstName.getText().toString();
        String LNAME = lastName.getText().toString();
        String EMAIL = eMail.getText().toString();
        String PASSWD = pass.getText().toString();
        String CONFIRM = confirm.getText().toString();

        if(!FNAME.isEmpty() && !LNAME.isEmpty() && !EMAIL.isEmpty() && !PASSWD.isEmpty() && !CONFIRM.isEmpty()) {
            if(FNAME.trim().matches(namePattern)){
                if(LNAME.trim().matches(namePattern)){
                    if(EMAIL.trim().matches(emailPattern)){
                        if(CONFIRM.equals(PASSWD)){
                            //Validation: Don't repeat email if exists.
                            if(manager.checkEmailAvailability(EMAIL)) {
                                Toast.makeText(this, R.string.user_exists, Toast.LENGTH_SHORT).show();
                            }else {
                                ContentValues DATA = new ContentValues();
                                DATA.put("firstname", FNAME);
                                DATA.put("lastname", LNAME);
                                DATA.put("email", EMAIL);
                                DATA.put("password", PASSWD);
                                firstName.setText("");
                                lastName.setText("");
                                eMail.setText("");
                                pass.setText("");
                                confirm.setText("");
                                database.insert("users", null, DATA);
                                database.close();
                                Toast.makeText(this, R.string.user_created, Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(getBaseContext(),SignIn.class);
                                startActivity(myIntent);
                            }
                        }else{
                            confirm.setError(getString(R.string.passwords_error));
                            Toast.makeText(this, R.string.passwords_error, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        eMail.setError(getString(R.string.invalid_email));
                        Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    lastName.setError(getString(R.string.invalid_last_name));
                    Toast.makeText(this, R.string.invalid_last_name, Toast.LENGTH_SHORT).show();
                }
            }else{
                firstName.setError(getString(R.string.invalid_name_error));
                Toast.makeText(this, R.string.invalid_name_error, Toast.LENGTH_SHORT).show();
            }
        }else{
            if(FNAME.isEmpty()){
                firstName.setError(getString(R.string.invalid_field_message));
            }
            if (LNAME.isEmpty()){
                lastName.setError(getString(R.string.invalid_field_message));
            }
            if(EMAIL.isEmpty()){
                eMail.setError(getString(R.string.invalid_field_message));
            }
            if(PASSWD.isEmpty()){
                pass.setError(getString(R.string.invalid_field_message));
            }
            if(CONFIRM.isEmpty()){
                confirm.setError(getString(R.string.invalid_field_message));
            }
            Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_SHORT).show();
        }
    }

}
