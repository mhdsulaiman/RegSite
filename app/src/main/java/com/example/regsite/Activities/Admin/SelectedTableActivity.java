package com.example.regsite.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.regsite.Activities.Admin.TablesActivities.CourseTableActivity;
import com.example.regsite.Activities.Admin.TablesActivities.InstructorTableActivity;
import com.example.regsite.Activities.Admin.TablesActivities.SectionTableActivity;
import com.example.regsite.Activities.Admin.TablesActivities.StudentTableActivity;
import com.example.regsite.Activities.Admin.TablesLayoutView.CourseTableView;
import com.example.regsite.Activities.Admin.TablesLayoutView.InstructorTableView;
import com.example.regsite.Activities.Admin.TablesLayoutView.SectionTableView;
import com.example.regsite.Activities.Admin.TablesLayoutView.StudentTableView;
import com.example.regsite.R;
import com.example.regsite.Tables.CoursesTable;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.InstructorTable;
import com.example.regsite.Tables.SectionTable;
import com.example.regsite.Tables.StudentTable;

import java.util.ArrayList;

public class SelectedTableActivity extends AppCompatActivity {

    private Button AddNewItem;
    private TableLayout ItemsTable;
    private String TableName;
    private ArrayList<String> ColumnsName;

    private DatabaseHelper db;

    public String getTableName() {
        TableName = getIntent().getStringExtra("TableName");
        return TableName;
    }

    public DatabaseHelper getDb() {
        db = new DatabaseHelper(this);
        return db;
    }

    public void ModifyingTable() {
        // Table Name
        TextView TableNameTxt = findViewById(R.id.TableNameTxt);
        TableNameTxt.setText(getTableName() + ":");

        // Table Columns
        ItemsTable = findViewById(R.id.ItemTable);
        TextView FirstCol = findViewById(R.id.FirstCol);
        TextView SecondCol = findViewById(R.id.SecondCol);
        TextView ThirdCol = findViewById(R.id.ThirdCol);

        switch (getTableName()) {
            case "Course":
                FirstCol.setText("Course ID");
                SecondCol.setText("Title");
                ThirdCol.setText("Hours");

                ArrayList<CoursesTable> CT = CoursesTable.getAllCourses(getDb());
                CourseTableView ctv = new CourseTableView(this, CT, ItemsTable);
                ctv.Table();

                break;
            case "Instructor":
                FirstCol.setText("Instructor ID");
                SecondCol.setText("First Name");
                ThirdCol.setText("Last Name");

                ArrayList<InstructorTable> IT = InstructorTable.getAllInstructors(getDb());
                InstructorTableView itv = new InstructorTableView(this, IT, ItemsTable);
                itv.Table();
                break;

            case "Section":
                FirstCol.setText("Section No");
                SecondCol.setText("RoomNo");
                ThirdCol.setText("Time");

                ArrayList<SectionTable> ST = SectionTable.getAllSections(getDb());
                SectionTableView stv = new SectionTableView(this, ST, ItemsTable);
                stv.Table();
                break;
            case "Student":
                FirstCol.setText("Student ID");
                SecondCol.setText("First Name");
                ThirdCol.setText("Last Name");

                ArrayList<StudentTable> SUT = StudentTable.getAllStudents(getDb());
                StudentTableView sutv = new StudentTableView(this, SUT, ItemsTable);
                sutv.Table();
                break;
        }

    }


    public void AddNewItem(View view) {
        switch (getTableName()) {
            case "Course":
                Intent CourseIntent = new Intent(SelectedTableActivity.this, CourseTableActivity.class);
                CourseIntent.putExtra("Status", "Add");
                startActivity(CourseIntent);
                break;
            case "Instructor":
                Intent InstructorIntent = new Intent(SelectedTableActivity.this, InstructorTableActivity.class);
                InstructorIntent.putExtra("Status", "Add");
                startActivity(InstructorIntent);
                break;
            case "Section":
                Intent SectionIntent = new Intent(SelectedTableActivity.this, SectionTableActivity.class);
                SectionIntent.putExtra("Status", "Add");
                startActivity(SectionIntent);
                break;
            case "Student":
                Intent StudentIntent = new Intent(SelectedTableActivity.this, StudentTableActivity.class);
                StudentIntent.putExtra("Status", "Add");
                startActivity(StudentIntent);
                break;
        }
    }

    public void ReturnToAdminProfile(View view) {
        Intent AdminIntent = new Intent(SelectedTableActivity.this, AdminProfileActivity.class);
        startActivity(AdminIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_table);

        ModifyingTable();

    }
}
