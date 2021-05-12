package com.example.regsite.Tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class InstructorTable {
    private int InstructorId;
    private String First_name, Last_name, Gender, Address, mobileNo;

    public InstructorTable(int instructorId, String first_name, String last_name, String gender, String address, String mobileNo) {
        InstructorId = instructorId;
        First_name = first_name;
        Last_name = last_name;
        Gender = gender;
        Address = address;
        this.mobileNo = mobileNo;
    }

    public int getInstructorId() {
        return InstructorId;
    }

    public String getFirst_name() {
        return First_name;
    }

    public String getLast_name() {
        return Last_name;
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
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_instructor;", null);

        if(cursor.moveToFirst()) {
            cursor.moveToLast();
            id = cursor.getInt(cursor.getColumnIndex("InstructorId"));
        }
        else{
            id = 0;
        }
        return id;
    }

    public static InstructorTable getSpecificInstructorData(DatabaseHelper db, int InstructorId){

        SQLiteDatabase DataBase = db.getWritableDatabase();
        // getting the instructor data based on instructor id
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_Instructor where InstructorId =" + InstructorId + ";", null);

        if (cursor.moveToFirst()) {
            DataBase.close();

            InstructorTable instructor= new InstructorTable(InstructorId,
                    cursor.getString(cursor.getColumnIndex("First_name")),
                    cursor.getString(cursor.getColumnIndex("Last_name")),
                    cursor.getString(cursor.getColumnIndex("Gender")),
                    cursor.getString(cursor.getColumnIndex("Address")),
                    cursor.getString(cursor.getColumnIndex("mobileNo")));

            return instructor;
        }
        return new InstructorTable(InstructorId,
                "",
                "",
                "",
                "",
                "");
    }


    public static ArrayList<InstructorTable> getAllInstructors(DatabaseHelper db){

        ArrayList <InstructorTable> instructors = new ArrayList<>();

        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from Reg_admin_instructor;", null);
        if (cursor.moveToFirst()) {
            do {
                InstructorTable instructor = new InstructorTable(
                        cursor.getInt(cursor.getColumnIndex("InstructorId")),
                        cursor.getString(cursor.getColumnIndex("First_name")),
                        cursor.getString(cursor.getColumnIndex("Last_name")),
                        cursor.getString(cursor.getColumnIndex("Gender")),
                        cursor.getString(cursor.getColumnIndex("Address")),
                        cursor.getString(cursor.getColumnIndex("mobileNo")));

                instructors.add(instructor);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return instructors;
    }

    public static void AddNewInstructor(DatabaseHelper db, String FirstName, String LastName, String Gender, String Address, String MobileNo){
        SQLiteDatabase DataBase = db.getWritableDatabase();
        // move to the next id
        int next_id = InstructorTable.getNextId(db)+1;
        Cursor cursor = DataBase.rawQuery("insert into Reg_admin_instructor VALUES("
                + next_id +",'"
                + FirstName + "','"
                + LastName + "','"
                + Gender +"','"
                + Address +"','"
                + MobileNo +"');", null);

        if(cursor.moveToFirst()){
            DataBase.close();
            cursor.close();
        }
    }

    public static void UpdateInstructorData(DatabaseHelper db, int InstructorId, String FirstName, String LastName, String Gender, String Address, String MobileNo) {
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("UPDATE Reg_admin_instructor SET "
                + "First_name ='" + FirstName + "',"
                + "Last_name ='" + LastName + "',"
                + "Gender ='" + Gender + "',"
                + "Address ='" + Address + "',"
                + "mobileNo ='" + MobileNo + "' WHERE InstructorId=" + InstructorId + ";", null);

        if (cursor.moveToFirst()) {
            DataBase.close();
            cursor.close();
        }
    }

    public static void DeleteInstructorData(DatabaseHelper db, int InstructorId) {
        SQLiteDatabase DataBase = db.getWritableDatabase();
        DataBase.execSQL("Delete From Reg_admin_instructor WHERE InstructorId=" + InstructorId + ";");
        DataBase.execSQL("Delete From Reg_admin_section WHERE InstructorId_id=" + InstructorId + ";");
    }
}
