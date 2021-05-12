package com.example.regsite.Tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class StudentTable {

    private int StudentId;
    private String First_name, Last_name, Password, RegYear, Gender, Address, mobileNo;

    public StudentTable(int studentId, String first_name, String last_name, String password, String regYear, String gender, String address, String mobileNo) {
        StudentId = studentId;
        First_name = first_name;
        Last_name = last_name;
        Password = password;
        RegYear = regYear;
        Gender = gender;
        Address = address;
        this.mobileNo = mobileNo;
    }

    public int getStudentId() {
        return StudentId;
    }

    public String getFirst_name() {
        return First_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRegYear() {
        return RegYear;
    }

    public String getGender() {
        return Gender;
    }

    public String getAddress() {
        return Address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public static int getNextId(DatabaseHelper db) {
        int id;
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_student;", null);

        if(cursor.moveToFirst()) {
            cursor.moveToLast();
            id = cursor.getInt(cursor.getColumnIndex("StudentId"));
        }
        else{
            id = 0;
        }
        return id;
    }

    public static boolean StudentIsExist(DatabaseHelper db, int studentId, String password) {
        //authenticate the student
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_student where StudentId =" + studentId + " and Password = '" + password + "' ;", null);

        if (cursor.moveToFirst()) {
            DataBase.close();
            return true;
        } else {
            DataBase.close();
            return false;
        }
    }

    public static StudentTable getStudentProfileData(DatabaseHelper db, int studentId) {

        SQLiteDatabase DataBase = db.getWritableDatabase();
        // getting the student data based on student id
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_student where StudentId =" + studentId + ";", null);

        if (cursor.moveToFirst()) {
            DataBase.close();

             StudentTable student = new StudentTable(studentId,
                    cursor.getString(cursor.getColumnIndex("First_name")),
                    cursor.getString(cursor.getColumnIndex("Last_name")),
                    cursor.getString(cursor.getColumnIndex("Password")),
                    cursor.getString(cursor.getColumnIndex("RegYear")),
                    cursor.getString(cursor.getColumnIndex("Gender")),
                    cursor.getString(cursor.getColumnIndex("Address")),
                    cursor.getString(cursor.getColumnIndex("mobileNo")));

             return student;
        }
        return new StudentTable(studentId,
                "",
                "",
                "",
                "",
                "",
                "",
                "");
    }

    public static ArrayList<StudentTable> getAllStudents(DatabaseHelper db){

        ArrayList <StudentTable> students = new ArrayList<>();

        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_student;", null);
        if (cursor.moveToFirst()) {
            do {
                StudentTable student = new StudentTable(
                        cursor.getInt(cursor.getColumnIndex("StudentId")),
                        cursor.getString(cursor.getColumnIndex("First_name")),
                        cursor.getString(cursor.getColumnIndex("Last_name")),
                        cursor.getString(cursor.getColumnIndex("Password")),
                        cursor.getString(cursor.getColumnIndex("RegYear")),
                        cursor.getString(cursor.getColumnIndex("Gender")),
                        cursor.getString(cursor.getColumnIndex("Address")),
                        cursor.getString(cursor.getColumnIndex("mobileNo")));

                students.add(student);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return students;
    }

    public static void AddNewStudent(DatabaseHelper db, String FirstName, String LastName, String Password, String RegYear, String Gender, String Address, String MobileNo){
        SQLiteDatabase DataBase = db.getWritableDatabase();
        // move to the next id
        int next_id = StudentTable.getNextId(db)+1;
        Cursor cursor = DataBase.rawQuery("insert into Reg_admin_student VALUES("
                + next_id +",'"
                + FirstName + "','"
                + LastName + "','"
                + Password +"','"
                + RegYear +"','"
                + Gender +"','"
                + Address +"','"
                + MobileNo +"');", null);

        if(cursor.moveToFirst()){
            DataBase.close();
            cursor.close();
        }
    }

    public static void UpdateStudentData(DatabaseHelper db, int StudentId, String FirstName, String LastName, String Password, String RegYear, String Gender, String Address, String MobileNo) {
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("UPDATE Reg_admin_student SET "
                + "First_name ='" + FirstName + "',"
                + "Last_name ='" + LastName + "',"
                + "Password ='" + Password + "',"
                + "RegYear ='" + RegYear + "',"
                + "Gender ='" + Gender + "',"
                + "Address ='" + Address + "',"
                + "mobileNo ='" + MobileNo + "' WHERE StudentId=" + StudentId + ";", null);

        if (cursor.moveToFirst()) {
            DataBase.close();
            cursor.close();
        }
    }
    public static void DeleteStudentData(DatabaseHelper db, int StudentId) {
        SQLiteDatabase DataBase = db.getWritableDatabase();
        DataBase.execSQL("Delete From Reg_admin_student WHERE StudentId=" + StudentId + ";");
        DataBase.execSQL("Delete From Reg_student_enrollment WHERE StudentId_id=" + StudentId + ";");
        DataBase.close();
    }
}
