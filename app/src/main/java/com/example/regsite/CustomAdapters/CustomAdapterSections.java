package com.example.regsite.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regsite.Activities.Admin.SelectedTableActivity;
import com.example.regsite.Activities.Admin.TablesActivities.StudentTableActivity;
import com.example.regsite.Activities.Student.StudentAccountActivity;
import com.example.regsite.R;
import com.example.regsite.Tables.DatabaseHelper;
import com.example.regsite.Tables.EnrollmentTable;
import com.example.regsite.Tables.InstructorTable;
import com.example.regsite.Tables.SectionTable;

import java.util.ArrayList;

public class CustomAdapterSections extends ArrayAdapter<SectionTable> {

    Context context;
    ArrayList<SectionTable> Sections;
    int StudentId;


    public CustomAdapterSections(Context context, ArrayList<SectionTable> Sections, int StudentId) {
        super(context, 0, Sections);
        this.context = context;
        this.Sections = Sections;
        this.StudentId = StudentId;
    }

    public int getStudentId() {
        return StudentId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SectionTable section = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.coursedetails_listview, parent, false);
        }

        TextView SectionNum = convertView.findViewById(R.id.SectionNumber);
        TextView RoomNum = convertView.findViewById(R.id.SectionRoomNumber);
        TextView Time = convertView.findViewById(R.id.SectionTime);

        // getting the instructor first and last name from instructor table
        final DatabaseHelper db = new DatabaseHelper(context);
        InstructorTable instructor = InstructorTable.getSpecificInstructorData(db, section.getInstructorId());

        TextView InstructorName = (TextView) convertView.findViewById(R.id.SectionInstructorName);

        SectionNum.setText("- Section Number: " + String.valueOf(section.getSectionNo()));
        RoomNum.setText("> Room Number: " + String.valueOf(section.getRoomNo()));
        Time.setText("> Time: " + String.valueOf(section.getTime()));
        InstructorName.setText("> Instructor Name: " + instructor.getFirst_name() + " " + instructor.getLast_name());

        // making sure to check the register situation
        RelativeLayout RL = convertView.findViewById(R.id.Register);
        if (EnrollmentTable.RegisteredInSection(db, section.getSectionNo(), getStudentId())) {

            TextView tv_register = new TextView(context);
            tv_register.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv_register.setText("You Already Registered in this Section.");
            tv_register.setTextColor(Color.RED);
            tv_register.setGravity(Gravity.CENTER);
            RL.addView(tv_register);
            // Reload = false;
        } else {
            Button btn_register = new Button(context);

            btn_register.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btn_register.setText("Register");
            btn_register.setTextColor(Color.WHITE);
            btn_register.setBackground(context.getResources().getDrawable(R.drawable.registerbtn));
            RL.addView(btn_register);

            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnrollmentTable.insertNewEnrollment(db, section.getSectionNo(), section.getCourseId(), getStudentId());
                    Toast.makeText(context, "Registered Successfully", Toast.LENGTH_LONG).show();

                    Intent Intent = new Intent(context, StudentAccountActivity.class);
                    Intent.putExtra("StudentId", getStudentId());
                    getContext().startActivity(Intent);
                }
            });
        }


        return convertView;
    }
}
