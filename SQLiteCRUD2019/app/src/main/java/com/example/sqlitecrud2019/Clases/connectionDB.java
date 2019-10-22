package com.example.sqlitecrud2019.Clases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class connectionDB extends SQLiteOpenHelper {

    public connectionDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE users (" +
                "id integer primary key autoincrement not null," +
                "firstname text not null," +
                "lastname text not null," +
                "email text not null," +
                "password text not null," +
                "birth date," +
                "country text," +
                "phone text," +
                "gender text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor selectUserData(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor rows = database.rawQuery("SELECT * FROM users",null);
        return rows;
    }

    public Cursor updateUserData(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor rows = database.rawQuery("SELECT * FROM users WHERE email = '" + email +"'",null);
        return rows;
    }

    public Boolean checkEmailAvailability(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor row = database.rawQuery("SELECT email FROM users WHERE email = '" + email + "'",null);
        return row.getCount() > 0;
    }

    public Boolean checkUserCredentials(String email, String password){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor row = database.rawQuery("SELECT email, password FROM users WHERE email = '" + email + "' AND password = '" + password + "'",null);
        return row.getCount() > 0;
    }

    public Integer deleteUser(String user_email){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete("users", "email = ?",new String[] {user_email});
    }

    public Cursor getAllMen(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("SELECT * FROM users WHERE gender ='Male'",null);
    }

    public Cursor getAllWomen(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("SELECT * FROM users WHERE gender ='Female'",null);
    }
}
