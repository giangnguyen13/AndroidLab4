package giang.nguyen.s301033256.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {
            SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_FIRSTNAME,
            SQLiteHelper.COLUMN_LASTNAME,
            SQLiteHelper.COLUMN_DEPARTMENT,
    };

//    private String[] allColumnsTests = {
//            SQLiteHelper.COLUMN_ID,
//            SQLiteHelper.COLUMN_PATIENT_ID,
//            SQLiteHelper.COLUMN_BLOOD_PRESSURE,
//            SQLiteHelper.COLUMN_CHOLESTEROL,
//            SQLiteHelper.COLUMN_TEMPERATURE,
//            SQLiteHelper.COLUMN_TEST_DATE
//    };

    public DataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Patient createPatient(String firstname,String lastname,String department) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_FIRSTNAME, firstname);
        values.put(SQLiteHelper.COLUMN_LASTNAME, lastname);
        values.put(SQLiteHelper.COLUMN_DEPARTMENT, department);

        long insertId = database.insert(SQLiteHelper.TABLE_PATIENTS, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_PATIENTS,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Patient newPatient = cursorToComment(cursor);
        cursor.close();
        return newPatient;
    }

    public void deletePatient(Patient patient) {
        long id = patient.getId();
        System.out.println("Comment deleted with id: " + id);
        // database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
        //         + " = " + id, null);

        database.delete(SQLiteHelper.TABLE_PATIENTS, SQLiteHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public List<Patient> getAllPatients() {
        List<Patient> comments = new ArrayList<Patient>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_PATIENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Patient patient = cursorToComment(cursor);
            comments.add(patient);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

//    public List<Test> getAllTests() {
//        List<Test> tests = new ArrayList<Test>();
//
//        Cursor cursor = database.query(SQLiteHelper.TABLE_PATIENTS,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Patient patient = cursorToComment(cursor);
//            tests.add(patient);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return tests;
//    }

    private Patient cursorToComment(Cursor cursor) {
        Patient patient = new Patient();

        patient.setId(cursor.getLong(0));
        patient.setFirstName(cursor.getString(1));
        patient.setLastName(cursor.getString(2));
        patient.setDepartment(cursor.getString(3));

        return patient;
    }
}
