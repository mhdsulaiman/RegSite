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

import com.example.regsite.Activities.Admin.TablesActivities.InstructorTableActivity;
import com.example.regsite.Tables.InstructorTable;

import java.util.ArrayList;

public class InstructorTableView {

    private Context context;
    private ArrayList<InstructorTable> instructors;
    private TableLayout TL;

    public InstructorTableView(Context context, ArrayList<InstructorTable> instructors, TableLayout TL) {
        this.context = context;
        this.instructors = instructors;
        this.TL = TL;
    }

    public Context getContext() {
        return context;
    }

    public ArrayList<InstructorTable> getInstructor() {
        return instructors;
    }

    public TableLayout getTL() {
        return TL;
    }

    public void Table() {
        for (final InstructorTable i : getInstructor()) {

            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv_indtructor_id = new TextView(getContext());
            TextView tv_instructor_firstname = new TextView(getContext());
            TextView tv_instructor_lastname = new TextView(getContext());
            TextView tv_change = new TextView(getContext());

            tv_indtructor_id.setText(String.valueOf(i.getInstructorId()));
            tv_instructor_firstname.setText(i.getFirst_name());
            tv_instructor_lastname.setText(i.getLast_name());

            // Customizing the details textview
            tv_change.setText("Change");
            tv_change.setTextColor(Color.rgb(240, 230, 140));
            tv_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InstructorTableActivity.class);
                    intent.putExtra("InstructorId", i.getInstructorId());
                    intent.putExtra("FirstName", i.getFirst_name());
                    intent.putExtra("LastName", i.getLast_name());
                    intent.putExtra("Gender", i.getGender());
                    intent.putExtra("Address", i.getAddress());
                    intent.putExtra("mobileNo", i.getMobileNo());

                    intent.putExtra("Status", "UpdateOrDelete");
                    context.startActivity(intent);
                }
            });


            tv_indtructor_id.setGravity(Gravity.CENTER);
            tv_instructor_firstname.setGravity(Gravity.CENTER);
            tv_instructor_lastname.setGravity(Gravity.CENTER);
            tv_change.setGravity(Gravity.CENTER);

            tr.addView(tv_indtructor_id);
            tr.addView(tv_instructor_firstname);
            tr.addView(tv_instructor_lastname);
            tr.addView(tv_change);

            getTL().addView(tr);

        }
    }
}
