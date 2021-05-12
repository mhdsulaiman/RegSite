package com.example.regsite.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.regsite.R;

import java.util.ArrayList;

public class CustomAdapterTables extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> Tables;


    public CustomAdapterTables(Context context, ArrayList<String> Tables) {
        super(context, 0, Tables);
        this.context = context;
        this.Tables = Tables;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String TableName = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tables_listview, parent, false);
        }

        TextView TableTitle = convertView.findViewById(R.id.TableTitleTxt);

        TableTitle.setText(TableName);
        return convertView;
    }
}
