package com.example.sqlitecrud2019.actividades;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitecrud2019.Clases.connectionDB;
import com.example.sqlitecrud2019.R;

public class SignUp extends AppCompatActivity {

    EditText Fname, Lname, Email, pwd, confirm;
    String emailC = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String nombreC = "[A-Z][a-z]+( [A-Z][a-z]+)*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Fname = findViewById(R.id.firstNameInput);
        Lname = findViewById(R.id.lastNameInput);
        Email = findViewById(R.id.emailInput);
        pwd = findViewById(R.id.passInput);
        confirm = findViewById(R.id.confirmInput);
    }
    public void Register(View view){
        connectionDB manager = new connectionDB(this,
                "bd", null, 1);
        SQLiteDatabase database = manager.getWritableDatabase();

        String FNAME = Fname.getText().toString();
        String LNAME = Lname.getText().toString();
        String EMAIL = Email.getText().toString();
        String PASSORWD = pwd.getText().toString();
        String CONFIRM = confirm.getText().toString();

        if(!FNAME.isEmpty() && !LNAME.isEmpty() && !EMAIL.isEmpty() && !PASSORWD.isEmpty() && !CONFIRM.isEmpty()) {
            if(FNAME.trim().matches(nombreC)){
                if(LNAME.trim().matches(nombreC)){
                    if(EMAIL.trim().matches(emailC)){
                        if(CONFIRM.equals(PASSORWD)){
                            //Validation: Don't repeat email if exists.
                            if(manager.checkEmailAvailability(EMAIL)) {
                                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }else {
                                ContentValues DATA = new ContentValues();
                                DATA.put("firstname", FNAME);
                                DATA.put("lastname", LNAME);
                                DATA.put("email", EMAIL);
                                DATA.put("password", PASSORWD);
                                Fname.setText("");
                                Lname.setText("");
                                Email.setText("");
                                pwd.setText("");
                                confirm.setText("");
                                database.insert("users", null, DATA);
                                database.close();
                                Toast.makeText(this,"usuario creado", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(getBaseContext(),SignIn.class);
                                startActivity(myIntent);
                            }
                        }else{
                            confirm.setError("password error");
                            Toast.makeText(this, "password error", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Email.setError("email incorrecto");
                        Toast.makeText(this, "email incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Lname.setError("Apellido incorrecto");
                    Toast.makeText(this, "Apellido icorrecto", Toast.LENGTH_SHORT).show();
                }
            }else{
                Fname.setError("Nombre incorrecto");
                Toast.makeText(this, "nombre incorrecto", Toast.LENGTH_SHORT).show();
            }
        }else{
            if(FNAME.isEmpty()){
                Fname.setError("Nombre invalido");
            }
            if (LNAME.isEmpty()){
                Lname.setError("Apellido invalido");
            }
            if(EMAIL.isEmpty()){
                Email.setError("Email invalido");
            }
            if(PASSORWD.isEmpty()){
                pwd.setError("Password invalido");
            }
            if(CONFIRM.isEmpty()){
                confirm.setError("No debe estar vacio");
            }
            Toast.makeText(this,"Campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

}
