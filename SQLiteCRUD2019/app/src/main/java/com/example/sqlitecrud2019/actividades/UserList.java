package com.example.sqlitecrud2019.actividades;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitecrud2019.Clases.UserListAdapter;
import com.example.sqlitecrud2019.Clases.Users;
import com.example.sqlitecrud2019.Clases.connectionDB;
import com.example.sqlitecrud2019.R;
import com.example.sqlitecrud2019.classes.UserListAdapter;
import com.example.sqlitecrud2019.classes.Users;
import com.example.sqlitecrud2019.classes.connectionDB;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {
    //Call DataBase class connection
    connectionDB manager;
    //Create ListView variable
    ListView userlist;
    //Create an ArrayList variable
    ArrayList<Users> itemList;
    //Create an Array adapter variable
    UserListAdapter adapter;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        //Instant DB Connection
        manager = new connectionDB(this, "bd", null, 1);
        //Create an empty array
        //Call ListView id
        userlist = findViewById(android.R.id.list);
        registerForContextMenu(userlist);
        //Call viewData method
        itemList =  showAllUsers();
        adapter = new UserListAdapter(this, itemList);
        userlist.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }


    //show menu on long press on screen
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecccione");
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    //action on menu item press
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String user_email = ((TextView)info.targetView.findViewById(R.id.list_email)).getText().toString();

        switch (item.getItemId()){
            case R.id.update_item:
                Toast.makeText(this,user_email,Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getBaseContext(),Update.class);
                myIntent.putExtra("email", user_email);
                startActivity(myIntent);
                return true;
            case R.id.delete_item:
                deleteUser(user_email);
                itemList =  showAllUsers();
                adapter.updateList(itemList);
                Toast.makeText(this, R.string.user_was_deleted ,Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.men_filter:
                itemList = showOnlyMen();
                adapter.updateList(itemList);
                Toast.makeText(this, R.string.only_men,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.women_filter:
                itemList = showOnlyWomen();
                adapter.updateList(itemList);
                Toast.makeText(this, R.string.only_women,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.users_filter:
                itemList = showAllUsers();
                adapter.updateList(itemList);
                Toast.makeText(this, R.string.full_list,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.aboutof:
                Intent aboutIntent = new Intent(getBaseContext(),AboutOf.class);
                startActivity(aboutIntent);
                return true;
            case R.id.sign_out_link:
                Intent myIntent = new Intent(getBaseContext(),SignIn.class);
                startActivity(myIntent);
            default:
                return super.onContextItemSelected(item);
        }//switch
    }

    private ArrayList<Users> showAllUsers() {
        ArrayList<Users> list = new ArrayList<>();
        cursor = manager.selectUserData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this,
                    R.string.no_users, Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Users user = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                list.add(user);
            }
        }
        return list;
    }

    private ArrayList<Users> showOnlyMen() {
        ArrayList<Users> list = new ArrayList<>();
        cursor = manager.getAllMen();
        if (cursor.getCount() == 0) {
            Toast.makeText(this,
                    R.string.no_users, Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Users user = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                list.add(user);
            }
        }
        return list;
    }

    private ArrayList<Users> showOnlyWomen() {
        ArrayList<Users> list = new ArrayList<>();
        cursor = manager.getAllWomen();
        if (cursor.getCount() == 0) {
            Toast.makeText(this,
                    R.string.no_users, Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Users user = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                list.add(user);
            }
        }
        return list;
    }


    public void deleteUser(String email){
        manager.deleteUser(email);
    }
}
