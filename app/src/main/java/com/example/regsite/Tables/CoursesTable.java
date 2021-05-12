package com.example.regsite.Tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CoursesTable {

    private int CourseId;
    private String Title;
    private int Hours;

    public CoursesTable(int courseId, String title, int hours) {
        CourseId = courseId;
        Title = title;
        Hours = hours;
    }

    public int getCourseId() {
        return CourseId;
    }

    public String getTitle() {
        return Title;
    }

    public int getHours() {
        return Hours;
    }

    public static int getNextId(DatabaseHelper db) {
        int id;
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_course;", null);

        if(cursor.moveToFirst()) {
            cursor.moveToLast();
            id = cursor.getInt(cursor.getColumnIndex("CourseId"));
        }
        else{
            id = 0;
        }
        return id;
    }


    public static ArrayList getAllCourses(DatabaseHelper db){

        ArrayList <CoursesTable> courses = new ArrayList<>();

        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_course;", null);
        if (cursor.moveToFirst()){
            do{
                int CourseId = cursor.getInt(cursor.getColumnIndex("CourseId"));
                String Title = cursor.getString(cursor.getColumnIndex("Title"));
                int Hours = cursor.getInt(cursor.getColumnIndex("Hours"));
                CoursesTable course = new CoursesTable(CourseId, Title, Hours);
                courses.add(course);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return courses;
    }

    public static CoursesTable getSpecificCourse(DatabaseHelper db, int CourseId){

        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_course where CourseId =" + CourseId +";", null);

        cursor.moveToFirst();

        String Title = cursor.getString(cursor.getColumnIndex("Title"));
        int Hours = cursor.getInt(cursor.getColumnIndex("Hours"));

        CoursesTable course = new CoursesTable(CourseId, Title, Hours);
        cursor.close();
        db.close();

        return course;
    }

    public static void AddNewCourse(DatabaseHelper db, String Title, int Hours){
        SQLiteDatabase DataBase = db.getWritableDatabase();
        // move to the next id
        int next_id = CoursesTable.getNextId(db)+1;
        Cursor cursor = DataBase.rawQuery("insert into Reg_admin_course VALUES("
                + next_id +",'"
                + Title + "',"
                + Hours +");", null);

        if(cursor.moveToFirst()){
            DataBase.close();
            cursor.close();
        }
    }

    public static void UpdateCourseData(DatabaseHelper db, int CourseId, String Title, int Hours) {
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("UPDATE Reg_admin_course SET "
                + "Title ='" + Title + "',"
                + "Hours =" + Hours + " WHERE CourseId=" + CourseId + ";", null);

        if (cursor.moveToFirst()) {
            DataBase.close();
            cursor.close();
        }
    }

    public static void DeleteCourseData(DatabaseHelper db, int CourseId) {
        SQLiteDatabase DataBase = db.getWritableDatabase();
        DataBase.execSQL("Delete From Reg_admin_course WHERE CourseId=" + CourseId + ";");
        DataBase.execSQL("Delete From Reg_admin_section WHERE CourseId_id=" + CourseId + ";");
        DataBase.execSQL("Delete From Reg_student_enrollment WHERE CourseId_id=" + CourseId + ";");
        DataBase.close();
    }
}

