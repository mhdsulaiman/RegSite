package com.example.regsite.Tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.DateSorter;

import com.example.regsite.R;

import java.sql.Time;
import java.util.ArrayList;

public class SectionTable {
    private int SectionNo;
    private int RoomNo;
    private String time;
    private int CourseId;
    private int InstructorId;

    public SectionTable(int sectionNo, int roomNo, String time, int courseId, int instructorId) {
        SectionNo = sectionNo;
        RoomNo = roomNo;
        this.time = time;
        CourseId = courseId;
        InstructorId = instructorId;
    }

    public int getSectionNo() {
        return SectionNo;
    }

    public int getRoomNo() {
        return RoomNo;
    }

    public String getTime() {
        return time;
    }

    public int getCourseId() {
        return CourseId;
    }

    public int getInstructorId() {
        return InstructorId;
    }


    public static ArrayList getAllSectionsForCourse(DatabaseHelper db, int CourseId){
        ArrayList<SectionTable> sections = new ArrayList<>();

        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_section where CourseId_id=" + CourseId + ";", null);

        if (cursor.moveToFirst()){
            do{
                int SectionNo = cursor.getInt(cursor.getColumnIndex("SectionNo"));
                int RoomNo = cursor.getInt(cursor.getColumnIndex("RoomNo"));
                String Time = cursor.getString(cursor.getColumnIndex("Time"));
                int InstructorId = cursor.getInt(cursor.getColumnIndex("InstructorId_id"));

                SectionTable section = new SectionTable(
                        SectionNo,
                        RoomNo,
                        Time,
                        CourseId,
                        InstructorId);

                sections.add(section);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return sections;
    }


    public static ArrayList<SectionTable> getAllSections(DatabaseHelper db){

        ArrayList <SectionTable> sections = new ArrayList<>();

        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_section;", null);
        if (cursor.moveToFirst()) {
            do {
                SectionTable section = new SectionTable(
                        cursor.getInt(cursor.getColumnIndex("SectionNo")),
                        cursor.getInt(cursor.getColumnIndex("RoomNo")),
                        cursor.getString(cursor.getColumnIndex("Time")),
                        cursor.getInt(cursor.getColumnIndex("CourseId_id")),
                        cursor.getInt(cursor.getColumnIndex("InstructorId_id")));

                sections.add(section);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return sections;
    }

    public static void AddNewSection(DatabaseHelper db, int SectionNo, int RoomNo, String time, int CourseId, int InstructorId){
        SQLiteDatabase DataBase = db.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery("insert into Reg_admin_section VALUES("
                + SectionNo +","
                + RoomNo + ",'"
                + time + "',"
                + CourseId +","
                + InstructorId +");", null);

        if(cursor.moveToFirst()){
            DataBase.close();
            cursor.close();
        }
    }

    public static void UpdateSectionData(DatabaseHelper db, int SectionNo, int RoomNo, String time, int CourseId, int InstructorId){
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("UPDATE Reg_admin_section SET "
                + "RoomNo =" + RoomNo + ","
                + "Time ='" + time + "',"
                + "CourseId_id =" + CourseId + ","
                + "InstructorId_id =" + InstructorId + " WHERE SectionNo=" + SectionNo + ";", null);

        if (cursor.moveToFirst()) {
            DataBase.close();
            cursor.close();
        }
    }

    public static void DeleteSectionData(DatabaseHelper db, int SectionNo) {
        SQLiteDatabase DataBase = db.getWritableDatabase();
        DataBase.execSQL("Delete From Reg_admin_section WHERE SectionNo=" + SectionNo + ";");
    }

}
