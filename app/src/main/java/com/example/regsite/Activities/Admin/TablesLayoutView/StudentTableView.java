package com.example.regsite.Activities.Admin.TablesLayoutView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.regsite.Activities.Admin.TablesActivities.StudentTableActivity;
import com.example.regsite.Tables.StudentTable;

import java.util.ArrayList;

public class StudentTableView {
    private Context context;
    private ArrayList<StudentTable> students;
    private TableLayout TL;

    public StudentTableView(Context context, ArrayList<StudentTable> students, TableLayout TL) {
        this.context = context;
        this.students = students;
        this.TL = TL;
    }


    public Context getContext() {
        return context;
    }

    public ArrayList<StudentTable> getStudents() {
        return students;
    }

    public TableLayout getTL() {
        return TL;
    }

    public void Table() {
        for (final StudentTable i : getStudents()) {

            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv_studentid = new TextView(getContext());
            TextView tv_first_name = new TextView(getContext());
            TextView tv_last_name = new TextView(getContext());
            TextView tv_change = new TextView(getContext());

            tv_studentid.setText(String.valueOf(i.getStudentId()));
            tv_first_name.setText(i.getFirst_name());
            tv_last_name.setText(i.getLast_name());

            // Customizing the details textview
            tv_change.setText("Change");
            tv_change.setTextColor(Color.rgb(240, 230, 140));
            tv_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StudentTableActivity.class);
                    intent.putExtra("StudentId", i.getStudentId());
                    intent.putExtra("FirstName", i.getFirst_name());
                    intent.putExtra("LastName", i.getLast_name());
                    intent.putExtra("Password", i.getPassword());
                    intent.putExtra("RegYear", i.getRegYear());
                    intent.putExtra("Gender", i.getGender());
                    intent.putExtra("Address", i.getAddress());
                    intent.putExtra("mobileNo", i.getMobileNo());

                    intent.putExtra("Status", "UpdateOrDelete");
                    context.startActivity(intent);
                }
            });


            tv_studentid.setGravity(Gravity.CENTER);
            tv_first_name.setGravity(Gravity.CENTER);
            tv_last_name.setGravity(Gravity.CENTER);
            tv_change.setGravity(Gravity.CENTER);

            tr.addView(tv_studentid);
            tr.addView(tv_first_name);
            tr.addView(tv_last_name);
            tr.addView(tv_change);

            getTL().addView(tr);
        }
    }
}
