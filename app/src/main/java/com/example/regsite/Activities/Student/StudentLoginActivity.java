package com.example.regsite.Activities.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regsite.Activities.Admin.AdminLoginActivity;
import com.example.regsite.R;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.StudentTable;

public class StudentLoginActivity extends AppCompatActivity {
    private Button SubmitBtn;
    private EditText TxtStudentId;
    private EditText TxtPassword;
    private TextView AdminLogin;
    private DatabaseHelper db;

    public int getStudentId() {
        TxtStudentId = findViewById(R.id.StudentId);
        int StudentId = Integer.parseInt(TxtStudentId.getText().toString());
        return StudentId;
    }

    public String getPassword() {
        TxtPassword = findViewById(R.id.Password);
        String Password = TxtPassword.getText().toString();
        return Password;
    }

    public DatabaseHelper Database() {
        db = new DatabaseHelper(this);
        return db;
    }

    public void SubmitUserData(View view) {


        //Testing authentication of the user
        if (StudentTable.StudentIsExist(Database(), getStudentId(), getPassword())) {
            Intent intent = new Intent(StudentLoginActivity.this, StudentAccountActivity.class);
            intent.putExtra("StudentId", getStudentId());
            startActivity(intent);
        } else {
            Toast.makeText(this, "StudentID or Password is not correct", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Try again.", Toast.LENGTH_SHORT).show();

        }
    }

    public void AdminLogin(View view) {

        Intent intent = new Intent(StudentLoginActivity.this, AdminLoginActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

    }

}
