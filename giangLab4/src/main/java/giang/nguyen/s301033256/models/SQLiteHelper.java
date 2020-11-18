package giang.nguyen.s301033256.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper{
    //-- PATIENTS TABLE --//
    public static final String TABLE_PATIENTS = "patients";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_DEPARTMENT = "department";
    public static final String COLUMN_GENDER = "gender";

    //-- TEST TABLE --//
    public static final String TABLE_TESTS = "tests";
    //public static final String COLUMN_ID = "id";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_BLOOD_PRESSURE = "blood_pressure";
    public static final String COLUMN_CHOLESTEROL = "cholesterol";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_TEST_DATE = "test_date";

    private static final String DATABASE_NAME = "lab4.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (\n" +
                    "    %s INTEGER primary key autoincrement,\n" +
                    "    %s TEXT,\n" +
                    "    %s TEXT,\n" +
                    "    %s TEXT,\n" +
                    "    %s TEXT\n" +
                    ");",
            TABLE_PATIENTS,
            COLUMN_ID,
            COLUMN_FIRSTNAME,
            COLUMN_LASTNAME,
            COLUMN_DEPARTMENT,
            COLUMN_GENDER
    );

    private static final String DATABASE_CREATE_TESTS = String.format(
            "CREATE TABLE %s (\n" +
                    "    %s INTEGER primary key autoincrement,\n" +
                    "    %s INTEGER ,\n" +
                    "    %s TEXT,\n" +
                    "    %s TEXT,\n" +
                    "    %s TEXT,\n" +
                    "    %s TEXT\n" +
                    ");",
            TABLE_TESTS,
            COLUMN_ID,
            COLUMN_PATIENT_ID,
            COLUMN_BLOOD_PRESSURE,
            COLUMN_CHOLESTEROL,
            COLUMN_TEMPERATURE,
            COLUMN_TEST_DATE
    );

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE_TESTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);
        onCreate(db);
    }

    public Patient getPatientById(String patientID) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_PATIENTS + " where "+ COLUMN_ID + " = "+String.valueOf(patientID)+";", null );
        Patient patient = new Patient(); //create a new Student object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row
            //initialize the instance variables of task object
            patient.setId(cursor.getInt(0));
            patient.setFirstName(cursor.getString(1));
            patient.setLastName(cursor.getString(2));
            patient.setDepartment(cursor.getString(3));

            cursor.close();

        } else {
            patient = null;
        }
        db.close();
        return patient;
    }

    public List<Patient> getPatientByDepartment(String department) throws Exception{
        List<Patient> patients = new ArrayList<Patient>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_PATIENTS + " where "+ COLUMN_DEPARTMENT + " LIKE '%"+department+"%';", null );
        Patient patient = new Patient();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            patient = cursorToComment(cursor);
            patients.add(patient);
            cursor.moveToNext();
        }
        cursor.close();

        db.close();
        return patients;
    }

    private Patient cursorToComment(Cursor cursor) {
        Patient patient = new Patient();

        patient.setId(cursor.getLong(0));
        patient.setFirstName(cursor.getString(1));
        patient.setLastName(cursor.getString(2));
        patient.setDepartment(cursor.getString(3));
        patient.setGender(cursor.getString(4));

        return patient;
    }
}
