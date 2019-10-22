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
                                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(this,"The user was created", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(getBaseContext(),SignIn.class);
                                startActivity(myIntent);
                            }
                        }else{
                            confirm.setError("Invalid password");
                            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Email.setError("Invalid email");
                        Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Lname.setError("Invalid lastname");
                    Toast.makeText(this, "Invalid lastname", Toast.LENGTH_SHORT).show();
                }
            }else{
                Fname.setError("Invalid firstname");
                Toast.makeText(this, "Invalid firstname", Toast.LENGTH_SHORT).show();
            }
        }else{
            if(FNAME.isEmpty()){
                Fname.setError("Invalid firstname");
            }
            if (LNAME.isEmpty()){
                Lname.setError("Invalid lastname");
            }
            if(EMAIL.isEmpty()){
                Email.setError("Invalid email");
            }
            if(PASSORWD.isEmpty()){
                pwd.setError("Invalid password");
            }
            if(CONFIRM.isEmpty()){
                confirm.setError("The field must not be empty");
            }
            Toast.makeText(this,"Empty fields", Toast.LENGTH_SHORT).show();
        }
    }

}
