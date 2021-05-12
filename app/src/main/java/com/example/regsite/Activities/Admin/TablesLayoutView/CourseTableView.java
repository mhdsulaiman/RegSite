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

import com.example.regsite.Activities.Admin.TablesActivities.CourseTableActivity;
import com.example.regsite.Tables.CoursesTable;

import java.util.ArrayList;

public class CourseTableView {
    private Context context;
    private ArrayList<CoursesTable> courses;
    private TableLayout TL;

    public CourseTableView(Context context, ArrayList<CoursesTable> courses, TableLayout TL) {
        this.context = context;
        this.courses = courses;
        this.TL = TL;
    }

    public Context getContext() {
        return context;
    }

    public ArrayList<CoursesTable> getCourses() {
        return courses;
    }

    public TableLayout getTL() {
        return TL;
    }

    public void Table() {
        for (final CoursesTable c : getCourses()) {

            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv_courseid = new TextView(getContext());
            TextView tv_coursetitle = new TextView(getContext());
            TextView tv_coursetime = new TextView(getContext());
            TextView tv_change = new TextView(getContext());

            tv_courseid.setText(String.valueOf(c.getCourseId()));
            tv_coursetitle.setText(c.getTitle());
            tv_coursetime.setText(String.valueOf(c.getHours()) + "hr");

            // Customizing the details textview
            tv_change.setText("Change");
            tv_change.setTextColor(Color.rgb(240, 230, 140));
            tv_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CourseTableActivity.class);
                    intent.putExtra("CourseId", c.getCourseId());
                    intent.putExtra("Title", c.getTitle());
                    intent.putExtra("Hours", c.getHours());

                    intent.putExtra("Status", "UpdateOrDelete");
                    context.startActivity(intent);
                }
            });


            tv_courseid.setGravity(Gravity.CENTER);
            tv_coursetitle.setGravity(Gravity.CENTER);
            tv_coursetime.setGravity(Gravity.CENTER);
            tv_change.setGravity(Gravity.CENTER);

            tr.addView(tv_courseid);
            tr.addView(tv_coursetitle);
            tr.addView(tv_coursetime);
            tr.addView(tv_change);

            getTL().addView(tr);

        }
    }
}
