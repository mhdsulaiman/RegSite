package com.example.regsite.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.regsite.R;
import com.example.regsite.Tables.AdminAuthTable;
import com.example.regsite.Tables.DatabaseHelper;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText AdminUsernameTxt;
    private EditText AdminPasswordTxt;
    private Button LoginBtn;
    private DatabaseHelper db;

    public String getAdminUsername() {
        AdminUsernameTxt = findViewById(R.id.AdminUsername);
        String AdminUsername = AdminUsernameTxt.getText().toString();
        return AdminUsername;
    }

    public String getAdminPassword() {
        AdminPasswordTxt = findViewById(R.id.AdminPassword);
        String AdminPassword = AdminPasswordTxt.getText().toString();
        return AdminPassword;
    }


    public DatabaseHelper Database() {
        db = new DatabaseHelper(this);
        return db;
    }

    public void LoginData(View view) {


        //Testing authentication of the admin
        if (AdminAuthTable.AdminIsExist(Database(), getAdminUsername(), getAdminPassword())) {
            Intent intent = new Intent(AdminLoginActivity.this, AdminProfileActivity.class);
            // intent.putExtra("StudentId", getStudentId());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Username or Password is not correct", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Try again.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

    }
}
