package com.example.pustikom.adapterplay.com.example.pustikom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;

import static android.content.ContentValues.TAG;

/**
 * Created by asus on 11/11/2016.
 */

public class StudentDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME ="college.db";
    private static final int DATABASE_VERSION=1;
    private Appendable displayView;

    public StudentDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE" + StudentContract.TABLE_NAME + " " +
                StudentContract._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentContract.COLUMN_NIM + "TEXT NOT NULL," +
                StudentContract.COLUMN_NAME + "TEXT NOT NULL," +
                StudentContract.COLUMN_GENDER + "INTEGER," +
                StudentContract.COLUMN_EMAIL + "TEXT," +
                StudentContract.COLUMN_PHONE + "TEXT" +
                StudentContract.COLUMN_DELETE + "TEXT,";
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(StudentDbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL(StudentContract.COLUMN_DELETE);
        onCreate(db);
    }

    public void insertStudent(SQLiteDatabase db, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_NIM, student.getNoreg());
        values.put(StudentContract.COLUMN_NAME, student.getName());
        values.put(StudentContract.COLUMN_GENDER, student.getGender());
        values.put(StudentContract.COLUMN_EMAIL, student.getMail());
        values.put(StudentContract.COLUMN_PHONE, student.getPhone());
        db.insert(StudentContract.TABLE_NAME,null,values);
    }

    public void updateStudent(SQLiteDatabase db, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_NIM, student.getNoreg());
        values.put(StudentContract.COLUMN_NAME, student.getName());
        values.put(StudentContract.COLUMN_GENDER, student.getGender());
        values.put(StudentContract.COLUMN_EMAIL, student.getMail());
        values.put(StudentContract.COLUMN_PHONE, student.getPhone());
        String condition = StudentContract._ID + "= ?";
        String[] conditionArgs = {student.getId()+""};
        db.update(StudentContract.TABLE_NAME,values,condition,conditionArgs);
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = StudentDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StudentContract._ID,
                StudentContract.COLUMN_GENDER,
                StudentContract.COLUMN_NAME,
                StudentContract.COLUMN_EMAIL,
                StudentContract.COLUMN_NIM,
                StudentContract.COLUMN_PHONE};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                StudentContract.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        try {
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(StudentContract._ID + " - " +
                    StudentContract.TABLE_NAME + " - " +
                    StudentContract.COLUMN_DELETE + " - " +
                    StudentContract.COLUMN_EMAIL+ " - " +
                    StudentContract.COLUMN_NIM+ " - " +
                    StudentContract.COLUMN_PHONE+ " - " +
            );
        }
        // Figure out the index of each column
        int idColumnIndex = cursor.getColumnIndex(StudentContract._ID);
        int genderColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_GENDER);
        int nameColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_NAME);
        int emailColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_EMAIL);
        int nimColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_NIM);
        int phoneColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_PHONE);
        // Iterate through all the returned rows in the cursor
        while (cursor.moveToNext()) {
            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentEmail = cursor.getString(emailColumnIndex);
            int currentGender = cursor.getInt(genderColumnIndex);
            int currentNim = cursor.getInt(nimColumnIndex);
            String currentPhone = cursor.getString(phoneColumnIndex);
            // Display the values from each column of the current row in the cursor in the TextView
            displayView.append(("\n" + currentID + " - " +
                    currentName + " - " +
                    currentEmail + " - " +
                    currentGender + " - " +
                    currentPhone + " - " +
                    currentNim + " - " +
            ));
        }
        finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }


}