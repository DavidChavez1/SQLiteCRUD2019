package com.example.sqlitecrud2019.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlitecrud2019.R;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<Users> {
    private final Context context;
    private ArrayList<Users> itemList;

    public UserListAdapter(Context context, ArrayList<Users> itemList) {
        super(context, -1, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list, parent, false);
        TextView name = rowView.findViewById(R.id.showName);
        TextView email = rowView.findViewById(R.id.showEmail);
        ImageView avatar = rowView.findViewById(R.id.list_avatar_icon);
        name.setText(itemList.get(position).firstName + " " + itemList.get(position).lastName);
        email.setText(itemList.get(position).email);

        return rowView;
    }

    public void updateList(ArrayList<Users> newlist) {
        itemList = newlist;
        this.clear();
        this.addAll(itemList);
        this.notifyDataSetChanged();
    }
}
