package com.example.mobdevfinal_amaan.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobdevfinal_amaan.Student;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

//    Declaring constants and coloumns names for the DB
    private static final String DATABASE_NAME = "students.db";
    private static final String TABLE_NAME = "Students_List";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "FATHER_NAME";
    private static final String COL4 = "SURNAME";
    private static final String COL5 = "NATIONAL_ID";
    private static final String COL6 = "GENDER";
    private static final String COL7 = "DATE_OF_BIRTH";

    // Constructor
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Code runs automatically when the DB is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID TEXT PRIMARY KEY, NAME TEXT, FATHER_NAME TEXT, SURNAME TEXT, NATIONAL_ID TEXT, GENDER TEXT, DATE_OF_BIRTH TEXT)";
        db.execSQL(createTable);
    }

    // Every time the DB is updated or upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Inserting data into the DB
    public boolean insertData(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, student.getId());
        contentValues.put(COL2, student.getName());
        contentValues.put(COL3, student.getFathersName());
        contentValues.put(COL4, student.getSurname());
        contentValues.put(COL5, student.getNationalId());
        contentValues.put(COL6, student.getGender());
        contentValues.put(COL7, student.getDateOfBirth());

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    // Get Specific result
    public Cursor getDataById(String ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = ?", new String[]{ID});
        return res;
    }

    // Updating a row
    public boolean updateDataById(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, student.getId());
        contentValues.put(COL2, student.getName());
        contentValues.put(COL3, student.getFathersName());
        contentValues.put(COL4, student.getSurname());
        contentValues.put(COL5, student.getNationalId());
        contentValues.put(COL6, student.getGender());
        contentValues.put(COL7, student.getDateOfBirth());

        long result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{student.getId()});

        return result != -1;
    }

    // Deleting a row using ID
    public boolean deleteDataByID(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL1 + "=" + ID, null) > 0;
    }

    // Delete all data
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    // Checking existence of ID
    public boolean isIdExist(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where ID = ?",new String[]{id});
        if(res.getCount() <= 0) {
            return false;
        }
        return true;
    }

    // Return all data
    public ArrayList<Student> getAllData() {
        ArrayList<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);

        while(res.moveToNext()) {
            Student student = new Student();
            student.setId(res.getString(0));
            student.setName(res.getString(1));
            student.setFathers_name(res.getString(2));
            student.setSurname(res.getString(3));
            student.setNational_id(res.getString(4));
            student.setGender(res.getString(5));
            student.setDateOfBirth(res.getString(6));
            studentList.add(student);
        }
        return studentList;
    }

    // Return everything inside the dB
    public Cursor getAllDataInCursor() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


}