package com.example.regsite.Tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EnrollmentTable {
    private int Grads;
    private int CourseId_id;
    private int SectionNo_id;
    private int StudentId_id;

    public EnrollmentTable(int grads, int courseId_id, int sectionNo_id, int studentId_id) {
        Grads = grads;
        CourseId_id = courseId_id;
        SectionNo_id = sectionNo_id;
        StudentId_id = studentId_id;
    }

    public int getGrads() {
        return Grads;
    }

    public int getCourseId_id() {
        return CourseId_id;
    }

    public int getSectionNo_id() {
        return SectionNo_id;
    }

    public int getStudentId_id() {
        return StudentId_id;
    }

    public static int getNextId(DatabaseHelper db) {
        int id;
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_student_enrollment;", null);

        if(cursor.moveToFirst()) {
            cursor.moveToLast();
             id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        else{
             id = 0;
        }
        return id;
    }


    public static boolean RegisteredInSection(DatabaseHelper db, int SectionNo, int StudentId){
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_student_enrollment where SectionNo_id =" + SectionNo + " and StudentId_id="+ StudentId + ";", null);

        if (cursor.moveToFirst()) {
            DataBase.close();
            return true;
        } else {
            DataBase.close();
            return false;
        }
    }

    public static void insertNewEnrollment(DatabaseHelper db, int SectionNo, int CourseId, int StudentId){
        SQLiteDatabase DataBase = db.getWritableDatabase();
        // move to the next id
        int next_id = EnrollmentTable.getNextId(db)+1;


        Cursor cursor = DataBase.rawQuery("insert into Reg_student_enrollment VALUES("
                + next_id +","
                + null + ","
                + CourseId+ ","
                + SectionNo +","
                + StudentId +");", null);

        if(cursor.moveToFirst()){
            DataBase.close();
            cursor.close();
        }
    }
}
