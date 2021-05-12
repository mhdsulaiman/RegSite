package com.example.regsite.Activities.Admin.TablesActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.regsite.Activities.Admin.SelectedTableActivity;
import com.example.regsite.R;
import com.example.regsite.Tables.CoursesTable;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.InstructorTable;
import com.example.regsite.Tables.SectionTable;
import com.example.regsite.Tables.StudentTable;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SectionTableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText SectionNoTxt;
    private EditText RoomNoTxt;
    private EditText TimeTxt;
    private Spinner CourseIdTxt;
    private Spinner InstructorIdTxt;

    private String Status;
    private DatabaseHelper dbTxt;

    public EditText getSectionNoTxt() {
        SectionNoTxt = findViewById(R.id.SectionNoInput);
        return SectionNoTxt;
    }

    public EditText getRoomNoTxt() {
        RoomNoTxt = findViewById(R.id.RoomNoInput);
        return RoomNoTxt;
    }

    public EditText getTimeTxt() {
        TimeTxt = findViewById(R.id.TimeInput);
        return TimeTxt;
    }


    public Spinner getCourseIdTxt() {
        CourseIdTxt = findViewById(R.id.CourseIdInput);
        return CourseIdTxt;
    }

    public Spinner getInstructorIdTxt() {
        InstructorIdTxt = findViewById(R.id.InstructorIdInput);
        return InstructorIdTxt;
    }

    public String getStatus() {
        Status = getIntent().getStringExtra("Status");
        return Status;
    }

    public DatabaseHelper getDb() {
        DatabaseHelper db = new DatabaseHelper(this);
        return db;
    }


    //Time Style
    public void TimePicker(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(SectionTableActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                getTimeTxt().setText(selectedHour + ":" + selectedMinute + ":00");
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.show();

    }

    //CourseId Style
    public void CourseIdSpinner() {
        ArrayList<Integer> courses_id = new ArrayList();
        ArrayList<CoursesTable> courses = CoursesTable.getAllCourses(getDb());
        for (CoursesTable c : courses) {
            courses_id.add(c.getCourseId());
        }
        getCourseIdTxt().setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the course list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses_id);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        getCourseIdTxt().setAdapter(aa);
    }

    //InstructorId style
    public void InstructorIdSpinner() {
        ArrayList<Integer> instructor_id = new ArrayList();
        ArrayList<InstructorTable> instructors = InstructorTable.getAllInstructors(getDb());
        for (InstructorTable i : instructors) {
            instructor_id.add(i.getInstructorId());
        }
        getInstructorIdTxt().setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the instructor list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, instructor_id);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        getInstructorIdTxt().setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Giving the input initial values if we call already exist item
    public void InitialValues() {

        getSectionNoTxt().setText(String.valueOf(getIntent().getIntExtra("SectionNo", 0)));
        getRoomNoTxt().setText(String.valueOf(getIntent().getIntExtra("RoomNo", 0)));
        getTimeTxt().setText(getIntent().getStringExtra("Time"));
        getCourseIdTxt().setSelection(getIntent().getIntExtra("CourseId", 0) - 1);
        getInstructorIdTxt().setSelection(getIntent().getIntExtra("InstructorId", 0) - 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_table);
        // implementing spinners
        CourseIdSpinner();
        InstructorIdSpinner();
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

                    SectionTable.AddNewSection(getDb(),
                            Integer.parseInt(getSectionNoTxt().getText().toString()),
                            Integer.parseInt(getRoomNoTxt().getText().toString()),
                            getTimeTxt().getText().toString(),
                            Integer.parseInt(getCourseIdTxt().getSelectedItem().toString()),
                            Integer.parseInt(getInstructorIdTxt().getSelectedItem().toString()));

                    Toast.makeText(SectionTableActivity.this, "Section Added Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(SectionTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Section");
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

                    SectionTable.UpdateSectionData(getDb(),
                            Integer.parseInt(getSectionNoTxt().getText().toString()),
                            Integer.parseInt(getRoomNoTxt().getText().toString()),
                            getTimeTxt().getText().toString(),
                            Integer.parseInt(getCourseIdTxt().getSelectedItem().toString()),
                            Integer.parseInt(getInstructorIdTxt().getSelectedItem().toString()));

                    Toast.makeText(SectionTableActivity.this, "Section Data Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(SectionTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Section");
                    startActivity(Intent);
                }
            });

            DeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SectionTable.DeleteSectionData(getDb(),
                            Integer.parseInt(getSectionNoTxt().getText().toString()));

                    Toast.makeText(SectionTableActivity.this, "Section Data Deleted Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(SectionTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Section");
                    startActivity(Intent);
                }
            });


        }
    }
}
