package com.example.sqlitecrud2019.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitecrud2019.Clases.connectionDB;
import com.example.sqlitecrud2019.R;

public class SignIn extends AppCompatActivity {

    EditText email, pwd;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.txtEmailUsuario);
        pwd = findViewById(R.id.txtpasswordusuario);
    }

    public void checkCredentials(View view){
        connectionDB manager = new connectionDB(this, "bd", null, 1);

        String EMAIL = email.getText().toString();
        String PASSWORD = pwd.getText().toString();

        if(!EMAIL.isEmpty() && !PASSWORD.isEmpty()){
            if(EMAIL.trim().matches(emailPattern)){
                if(manager.checkUserCredentials(EMAIL, PASSWORD)){
                    Toast.makeText(this, "Starting session", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(getBaseContext(),UserList.class);
                    startActivity(myIntent);
                }else{
                    Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
            }else{
                email.setError("Invalid email");
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            }

        }else{
            if(EMAIL.isEmpty()){
                email.setError("Field empty");
            }
            if(PASSWORD.isEmpty()){
                pwd.setError("Field empty");
            }
            Toast.makeText(this, "The fields are empty", Toast.LENGTH_SHORT).show();

        }
    }

    public void signUp(View view){
        Intent myIntent = new Intent(getBaseContext(),SignUp.class);
        startActivity(myIntent);
    }
}
