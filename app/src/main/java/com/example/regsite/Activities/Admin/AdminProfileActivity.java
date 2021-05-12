package com.example.regsite.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.regsite.CustomAdapters.CustomAdapterTables;
import com.example.regsite.R;

import java.util.ArrayList;

public class AdminProfileActivity extends AppCompatActivity {

    private ListView TableList;

    public void ModifyingTableListView() {

        TableList = (ListView) findViewById(R.id.TableList);


        final ArrayList<String> tables = new ArrayList<>();
        //tables name
        tables.add("Course");
        tables.add("Instructor");
        tables.add("Section");
        tables.add("Student");

        CustomAdapterTables customAdapterTables = new CustomAdapterTables(this, tables);

        TableList.setAdapter(customAdapterTables);

        TableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminProfileActivity.this, SelectedTableActivity.class);

                intent.putExtra("TableName", tables.get(position));
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        ModifyingTableListView();
    }
}
