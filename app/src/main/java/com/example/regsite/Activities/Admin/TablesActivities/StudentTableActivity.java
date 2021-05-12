package com.example.regsite.Activities.Admin.TablesActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

import android.app.DatePickerDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.regsite.Activities.Admin.SelectedTableActivity;
import com.example.regsite.Activities.Student.StudentAccountActivity;
import com.example.regsite.R;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.StudentTable;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StudentTableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText FirstNameTxt;
    private EditText LastNameTxt;
    private EditText PasswordTxt;
    private EditText RegYearTxt;
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

    public EditText getPasswordTxt() {
        PasswordTxt = findViewById(R.id.PasswordInput);
        return PasswordTxt;
    }

    public EditText getRegYearTxt() {
        RegYearTxt = findViewById(R.id.RegYearInput);
        return RegYearTxt;
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


    //RegYear Style
    public void RegYearCalendar(View view) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(StudentTableActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth){
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = format.format(calendar.getTime());
                        getRegYearTxt().setText(dateString);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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
        getPasswordTxt().setText(getIntent().getStringExtra("Password"));
        getRegYearTxt().setText(getIntent().getStringExtra("RegYear"));

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
        setContentView(R.layout.activity_student_table);
        // implementing spinner
        GenderSpinner();
        // Testing the status of the call for the activity
        LinearLayout LL = findViewById(R.id.InputLinearLayout8);
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

                    StudentTable.AddNewStudent(getDb(),
                            getFirstNameTxt().getText().toString(),
                            getLastNameTxt().getText().toString(),
                            getPasswordTxt().getText().toString(),
                            getRegYearTxt().getText().toString(),
                            getGenderTxt().getSelectedItem().toString(),
                            getAddressTxt().getText().toString(),
                            getMobileNoTxt().getText().toString());

                    Toast.makeText(StudentTableActivity.this, "Student Added Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(StudentTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Student");
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

                    StudentTable.UpdateStudentData(getDb(),
                            getIntent().getIntExtra("StudentId", 0),
                            getFirstNameTxt().getText().toString(),
                            getLastNameTxt().getText().toString(),
                            getPasswordTxt().getText().toString(),
                            getRegYearTxt().getText().toString(),
                            getGenderTxt().getSelectedItem().toString(),
                            getAddressTxt().getText().toString(),
                            getMobileNoTxt().getText().toString());

                    Toast.makeText(StudentTableActivity.this, "Student Data Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(StudentTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Student");
                    startActivity(Intent);
                }
            });

            DeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StudentTable.DeleteStudentData(getDb(),
                            getIntent().getIntExtra("StudentId", 0));

                    Toast.makeText(StudentTableActivity.this, "Student Data Deleted Successfully", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(StudentTableActivity.this, SelectedTableActivity.class);
                    Intent.putExtra("TableName", "Student");
                    startActivity(Intent);
                }
            });


        }
    }
}
