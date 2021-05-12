package com.example.regsite.Activities.Admin.TablesActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.regsite.R;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.regsite.Activities.Admin.SelectedTableActivity;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.InstructorTable;
import com.example.regsite.Tables.StudentTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InstructorTableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText FirstNameTxt;
    private EditText LastNameTxt;
    private Spinner GenderTxt;
    private EditText AddressTxt;
    private EditText MobileNoTxt;
    private String Status;
    private DatabaseHelper dbTxt;

    public EditText getFirstNameTxt() {
        FirstNameTxt = findViewById(R.id.FirstNameInput);
        return FirstNameTxt;
    }

    public EditText getLastNameTxt() {
        LastNameTxt = findViewById(R.id.LastNameInput);
        return LastNameTxt;
    }

    public Spinner getGenderTxt() {
        GenderTxt = findViewById(R.id.GenderInput);
        return GenderTxt;
    }

    public EditText getAddressTxt() {
        AddressTxt = findViewById(R.id.AddressInput);
        return AddressTxt;
    }

    public EditText getMobileNoTxt() {
        MobileNoTxt = findViewById(R.id.MobileNoInput);
        return MobileNoTxt;
    }

    public String getStatus() {
        Status = getIntent().getStringExtra("Status");
        return Status;
    }

    public DatabaseHelper getDb() {
        DatabaseHelper db = new DatabaseHelper(this);
        return db;
    }


    //Gender Style
    public void GenderSpinner() {
        String[] gender = {"Male", "Female"};
        getGenderTxt().setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the gender list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        getGenderTxt().setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Giving the input initial values if we call already exist item
    public void InitialValues() {
        getFirstNameTxt().setText(getIntent().getStringExtra("FirstName"));
        getLastNameTxt().setText(getIntent().getStringExtra("LastName"));
        // Selecting the Gender that equals the data sended

        if (getIntent().getStringExtra("Gender").equals("Male"))
            getGenderTxt().setSelection(0);
        else
            getGenderTxt().setSelection(1);

        getAddressTxt().setText(getIntent().getStringExtra("Address"));
        getMobileNoTxt().setText(getIntent().getStringExtra("mobileNo"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_table);
        // implementing spinner
        GenderSpinner();
        // Testing the status of the call for the activity
        LinearLayout LL = findViewById(R.id.InputLinearLayout6);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 20, 20, 20);
        if (getStatus().equals("Add")) {
            Button AddBtn = new Button(this);
            AddBtn.setLayoutParams(params);
            AddBtn.setText("Add");
            AddBtn.setTextColor(Color.WHITE);
            AddBtn.setBackgroundColor(Color.rgb(50, 205, 50));

            LL.addView(AddBtn);

            AddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    InstructorTable.AddNewInstructor(getDb(),
                            getFirstNameTxt().getText().toString(),
                            getLastNameTxt().getText().toString(),
                            getGenderTxt().getSelectedItem().toString(),
                            getAddressTxt().getText().toString(),
                            getMobileNoTxt().getText().toString());

                    Toast.makeText(InstructorTableActivity.this, "Instructor Added Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(InstructorTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Instructor");
                    startActivity(Intent);
                }
            });

        } else if (getStatus().equals("UpdateOrDelete")) {
            InitialValues();


            Button SaveBtn = new Button(this);
            Button DeleteBtn = new Button(this);

            DeleteBtn.setLayoutParams(params);
            DeleteBtn.setText("Delete");
            DeleteBtn.setTextColor(Color.WHITE);
            DeleteBtn.setBackgroundColor(Color.rgb(139, 0, 0));

            SaveBtn.setLayoutParams(params);
            SaveBtn.setText("Save");
            SaveBtn.setTextColor(Color.WHITE);
            SaveBtn.setBackgroundColor(Color.rgb(135, 206, 235));

            LL.addView(DeleteBtn);
            LL.addView(SaveBtn);

            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    InstructorTable.UpdateInstructorData(getDb(),
                            getIntent().getIntExtra("InstructorId", 0),
                            getFirstNameTxt().getText().toString(),
                            getLastNameTxt().getText().toString(),
                            getGenderTxt().getSelectedItem().toString(),
                            getAddressTxt().getText().toString(),
                            getMobileNoTxt().getText().toString());

                    Toast.makeText(InstructorTableActivity.this, "Instructor Data Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(InstructorTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Instructor");
                    startActivity(Intent);
                }
            });

            DeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    InstructorTable.DeleteInstructorData(getDb(),
                            getIntent().getIntExtra("InstructorId", 0));

                    Toast.makeText(InstructorTableActivity.this, "Instructor Data Deleted Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(InstructorTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Instructor");
                    startActivity(Intent);
                }
            });


        }
    }
}
