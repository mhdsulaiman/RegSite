package com.example.regsite.Tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AdminAuthTable {
    private String AdminUsername;
    private String AdminPassword;

    public AdminAuthTable(String adminUsername, String adminPassword) {
        AdminUsername = adminUsername;
        AdminPassword = adminPassword;
    }


    public static boolean AdminIsExist(DatabaseHelper db, String admin_username, String admin_password){

        //authenticate the student
        SQLiteDatabase DataBase = db.getWritableDatabase();
        Cursor cursor = DataBase.rawQuery("SELECT * from auth_user where username='" + admin_username + "' and Password = '" + admin_password + "' ;", null);
        if (cursor.moveToFirst()) {
            DataBase.close();
            return true;
        } else {
            DataBase.close();
            return false;
        }

    }
}
