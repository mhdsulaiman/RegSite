package com.example.regsite.Activities.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.regsite.R;
import com.example.regsite.Tables.CoursesTable;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.StudentTable;

import java.util.ArrayList;

public class StudentAccountActivity extends AppCompatActivity {
    private TextView ProfileTitle;
    private DatabaseHelper db;
    private TableLayout ct;

    // Dialog to wait until the data load
    public void DialogDataLoading() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait until we load your data...");
        progress.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 2000);

    }

    public int getStudentId() {
        int StudentId = getIntent().getIntExtra("StudentId", 0);
        return StudentId;
    }

    // Modifying the title of the profile to fit the student data
    public void ModifyingTheTitle() {

        db = new DatabaseHelper(this);
        // Receive the student id from the main activity


        // changing the title of the profile to welcome first name
        ProfileTitle = findViewById(R.id.ProfileTitle);
        ProfileTitle.setText("Welcome " + StudentTable.getStudentProfileData(db, getStudentId()).getFirst_name());

    }

    // Building the table courses view
    public void CoursesTableView() {
        ArrayList<CoursesTable> courses = CoursesTable.getAllCourses(db);

        ct = (TableLayout) findViewById(R.id.CoursesTable);


        for (final CoursesTable c : courses) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv_courseid = new TextView(this);
            TextView tv_coursetitle = new TextView(this);
            TextView tv_coursetime = new TextView(this);
            TextView tv_coursedetails = new TextView(this);

            tv_courseid.setText(String.valueOf(c.getCourseId()));
            tv_coursetitle.setText(c.getTitle());
            tv_coursetime.setText(String.valueOf(c.getHours()) + "hr");

            // Customizing the details textview
            tv_coursedetails.setText("details");
            tv_coursedetails.setTextColor(Color.BLUE);
            tv_coursedetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StudentAccountActivity.this, CourseDetailsActivity.class);
                    intent.putExtra("CourseId", c.getCourseId());
                    intent.putExtra("StudentId", getStudentId());

                    startActivity(intent);
                }
            });


            tv_courseid.setGravity(Gravity.CENTER);
            tv_coursetitle.setGravity(Gravity.CENTER);
            tv_coursetime.setGravity(Gravity.CENTER);
            tv_coursedetails.setGravity(Gravity.CENTER);

            tr.addView(tv_courseid);
            tr.addView(tv_coursetitle);
            tr.addView(tv_coursetime);
            tr.addView(tv_coursedetails);

            ct.addView(tr);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AccountTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account);


        DialogDataLoading();
        ModifyingTheTitle();
        CoursesTableView();

    }
}
