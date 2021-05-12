package com.example.regsite.Activities.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.regsite.CustomAdapters.CustomAdapterSections;
import com.example.regsite.R;
import com.example.regsite.Tables.CoursesTable;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.SectionTable;

import java.util.ArrayList;

public class CourseDetailsActivity extends AppCompatActivity {

    private TextView CourseTitleTxt;
    private TextView CourseIdTxt;
    private TextView CourseTimeTxt;
    private int CourseId;
    private int StudentId;
    private ListView SectionsList;

    private DatabaseHelper db;


    public int getCourseId() {
        int CourseId = getIntent().getIntExtra("CourseId", 0);
        return CourseId;
    }

    public int getStudentId() {
        int StudentId = getIntent().getIntExtra("StudentId", 0);
        return StudentId;
    }


    public void ModifyingTitleLayout() {
        // Receive the course id from the UserAccount activity
        db = new DatabaseHelper(this);

        CourseTitleTxt = findViewById(R.id.CourseTitle);
        CourseIdTxt = findViewById(R.id.CourseId);
        CourseTimeTxt = findViewById(R.id.CourseTime);

        CoursesTable course = CoursesTable.getSpecificCourse(db, getCourseId());

        CourseTitleTxt.setText(course.getTitle());
        CourseIdTxt.setText("CourseID: " + String.valueOf(course.getCourseId()));
        CourseTimeTxt.setText("Time: " + course.getHours() + "hr");

    }

    public void ModifyingListView() {

        SectionsList = (ListView) findViewById(R.id.SectionList);
        db = new DatabaseHelper(this);


        ArrayList<SectionTable> sections = SectionTable.getAllSectionsForCourse(db, getCourseId());
        CustomAdapterSections customAdapterSections = new CustomAdapterSections(this, sections, getStudentId());
        SectionsList.setAdapter(customAdapterSections);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        ModifyingTitleLayout();
        ModifyingListView();
    }


}
