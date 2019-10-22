package com.example.sqlitecrud2019.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitecrud2019.R;
import com.example.sqlitecrud2019.classes.connectionDB;

public class SignIn extends AppCompatActivity {

    EditText email, pass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String namePattern = "[A-Z][a-z]+( [A-Z][a-z]+)*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.sign_in_email_field);
        pass = findViewById(R.id.sign_in_password_field);
    }

    public void checkCredentials(View view){
        connectionDB manager = new connectionDB(this, "bd", null, 1);

        String EMAIL = email.getText().toString();
        String PASSWORD = pass.getText().toString();

        if(!EMAIL.isEmpty() && !PASSWORD.isEmpty()){
            if(EMAIL.trim().matches(emailPattern)){
                if(manager.checkUserCredentials(EMAIL, PASSWORD)){
                    Toast.makeText(this, R.string.signed_in, Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(getBaseContext(),UserList.class);
                    startActivity(myIntent);
                }else{
                    Toast.makeText(this, R.string.credentials_incorrect, Toast.LENGTH_SHORT).show();
                }
            }else{
                email.setError(getString(R.string.invalid_email));
                Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
            }

        }else{
            if(EMAIL.isEmpty()){
                email.setError(getString(R.string.invalid_field_message));
            }
            if(PASSWORD.isEmpty()){
                pass.setError(getString(R.string.invalid_field_message));
            }
            Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_SHORT).show();

        }
    }

    public void signUp(View view){
        Intent myIntent = new Intent(getBaseContext(),SignUp.class);
        startActivity(myIntent);
    }
}
