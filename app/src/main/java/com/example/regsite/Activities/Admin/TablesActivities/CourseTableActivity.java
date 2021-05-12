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
import com.example.regsite.Tables.CoursesTable;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.StudentTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CourseTableActivity extends AppCompatActivity {
    private EditText TitleTxt;
    private EditText HoursTxt;
    private String Status;
    private DatabaseHelper db;

    public EditText getTitleTxt() {
        TitleTxt = findViewById(R.id.TitleInput);
        return TitleTxt;
    }

    public EditText getHoursTxt() {
        HoursTxt = findViewById(R.id.HoursInput);
        return HoursTxt;
    }

    public String getStatus() {
        Status = getIntent().getStringExtra("Status");
        return Status;
    }

    public DatabaseHelper getDb() {
        DatabaseHelper db = new DatabaseHelper(this);
        return db;
    }

    // Giving the input initial values if we call already exist item
    public void InitialValues() {
        getTitleTxt().setText(getIntent().getStringExtra("Title"));
        getHoursTxt().setText(String.valueOf(getIntent().getIntExtra("Hours", 0)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_table);

        // Testing the status of the call for the activity
        LinearLayout LL = findViewById(R.id.InputLinearLayout3);
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

                    CoursesTable.AddNewCourse(getDb(),
                            getTitleTxt().getText().toString(),
                            Integer.parseInt(getHoursTxt().getText().toString()));

                    Toast.makeText(CourseTableActivity.this, "Course Added Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(CourseTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Course");
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

                    CoursesTable.UpdateCourseData(getDb(),
                            getIntent().getIntExtra("CourseId", 0),
                            getTitleTxt().getText().toString(),
                            Integer.parseInt(getHoursTxt().getText().toString()));

                    Toast.makeText(CourseTableActivity.this, "Student Data Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(CourseTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Course");
                    startActivity(Intent);
                }
            });

            DeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CoursesTable.DeleteCourseData(getDb(),
                            getIntent().getIntExtra("CourseId", 0));

                    Toast.makeText(CourseTableActivity.this, "Student Data Deleted Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(CourseTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Course");
                    startActivity(Intent);
                }
            });


        }
    }
}

