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

import com.example.regsite.Activities.Admin.TablesActivities.SectionTableActivity;
import com.example.regsite.Tables.SectionTable;

import java.util.ArrayList;

public class SectionTableView {
    private Context context;
    private ArrayList<SectionTable> sections;
    private TableLayout TL;

    public SectionTableView(Context context, ArrayList<SectionTable> sections, TableLayout TL) {
        this.context = context;
        this.sections = sections;
        this.TL = TL;
    }


    public Context getContext() {
        return context;
    }

    public ArrayList<SectionTable> getSections() {
        return sections;
    }

    public TableLayout getTL() {
        return TL;
    }

    public void Table() {
        for (final SectionTable i : getSections()) {

            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv_SectionNo = new TextView(getContext());
            TextView tv_RoomNo = new TextView(getContext());
            TextView tv_Time = new TextView(getContext());
            TextView tv_change = new TextView(getContext());

            tv_SectionNo.setText(String.valueOf(i.getSectionNo()));
            tv_RoomNo.setText(String.valueOf(i.getRoomNo()));
            tv_Time.setText(i.getTime());

            // Customizing the details textview
            tv_change.setText("Change");
            tv_change.setTextColor(Color.rgb(240, 230, 140));
            tv_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SectionTableActivity.class);
                    intent.putExtra("SectionNo", i.getSectionNo());
                    intent.putExtra("RoomNo", i.getRoomNo());
                    intent.putExtra("Time", i.getTime());
                    intent.putExtra("CourseId", i.getCourseId());
                    intent.putExtra("InstructorId", i.getInstructorId());

                    intent.putExtra("Status", "UpdateOrDelete");
                    context.startActivity(intent);
                }
            });


            tv_SectionNo.setGravity(Gravity.CENTER);
            tv_RoomNo.setGravity(Gravity.CENTER);
            tv_Time.setGravity(Gravity.CENTER);
            tv_change.setGravity(Gravity.CENTER);

            tr.addView(tv_SectionNo);
            tr.addView(tv_RoomNo);
            tr.addView(tv_Time);
            tr.addView(tv_change);

            getTL().addView(tr);
        }
    }
}
