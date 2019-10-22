package com.example.sqlitecrud2019.actividades;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitecrud2019.Clases.connectionDB;
import com.example.sqlitecrud2019.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Update extends AppCompatActivity {

    EditText etBirthday, fname, lname, passwd, email,birth, phone;
    Calendar calendario = Calendar.getInstance();
    Spinner pais, genero;
    String mail;
    connectionDB manager;
    ArrayAdapter<String> adapterP, adapterG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        manager = new connectionDB(this, "bd", null, 1);

        fname = findViewById(R.id.txtFname);
        lname = findViewById(R.id.txtLname);
        passwd = findViewById(R.id.txtPassword);
        email = findViewById(R.id.txtEmail);
        birth = findViewById(R.id.txtFecha);
        phone = findViewById(R.id.txtPhone);
        email.setEnabled(false);

        etBirthday = findViewById(R.id.txtFecha);
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Update.this, date, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        pais = findViewById(R.id.spPais);
        String [] opP = {"COLOMBIA", "ECUADOR", "VENEZUELA", "BOLIVIA", "ARGENTINA", "PERU", "JAPAN", "CHINA"};
        adapterP = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opP);
        pais.setAdapter(adapterP);

        genero = findViewById(R.id.spGenero);
        String [] opG = {"MASCULINO", "FEMENINO", "SIN DEFINIR"};
        adapterG = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opG);
        genero.setAdapter(adapterG);

        Intent intent = getIntent();
        mail = intent.getStringExtra("email");

        viewData();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateInput();
        }

    };

    private void updateInput() {
        String FormFecha = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat fec = new SimpleDateFormat(FormFecha, Locale.US);

        etBirthday.setText(fec.format(calendario.getTime()));
    }

    private void viewData() {
        Cursor cursor = manager.updateUserData(mail);

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No hay usuarios", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                fname.setText(cursor.getString(1));
                lname.setText(cursor.getString(2));
                email.setText(cursor.getString(3));
                passwd.setText(cursor.getString(4));
                birth.setText(cursor.getString(5));

                phone.setText(cursor.getString(7));

            }
        }

    }

    public  void updateUserData(View view){
        String Fname = fname.getText().toString();
        String Lname = lname.getText().toString();
        String Email = email.getText().toString();
        String Passwd = passwd.getText().toString();
        String Birth = birth.getText().toString();
        String Phone = phone.getText().toString();
        String Pais = pais.getSelectedItem().toString();
        String Genero = genero.getSelectedItem().toString();

        if (!Fname.isEmpty() && !Lname.isEmpty() && !Email.isEmpty() && !Passwd.isEmpty()){
            SQLiteDatabase database = manager.getWritableDatabase();
            ContentValues DATA = new ContentValues();
            DATA.put("firstname", Fname);
            DATA.put("lastname", Lname);
            DATA.put("email", Email);
            DATA.put("password", Passwd);
            DATA.put("phone", Phone);
            DATA.put("birth", Birth);
            DATA.put("country", Pais);
            DATA.put("gender", Genero);
            fname.setText("");
            lname.setText("");
            email.setText("");
            passwd.setText("");
            birth.setText("");
            phone.setText("");
            pais.setAdapter(adapterP);
            genero.setAdapter(adapterG);
            database.update("users",DATA,"email='"+mail+"'",null);

            Intent myIntent = new Intent(getBaseContext(),UserList.class);
            startActivity(myIntent);
        }else{
            Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT).show();
        }

    }
}
