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
            SQLiteHelper.COLUMN_GENDER,
    };

    private String[] allColumnsTests = {
            SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_PATIENT_ID,
            SQLiteHelper.COLUMN_BLOOD_PRESSURE,
            SQLiteHelper.COLUMN_CHOLESTEROL,
            SQLiteHelper.COLUMN_TEMPERATURE,
            SQLiteHelper.COLUMN_TEST_DATE,
            SQLiteHelper.COLUMN_HEART_BEAT_RATE,
            SQLiteHelper.COLUMN_COVID,
    };

    public DataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        //dbHelper.onUpgrade(database,1,2);
    }

    public void close() {
        dbHelper.close();
    }

    public Patient createPatient(String firstname,String lastname,String department, String gender) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_FIRSTNAME, firstname);
        values.put(SQLiteHelper.COLUMN_LASTNAME, lastname);
        values.put(SQLiteHelper.COLUMN_DEPARTMENT, department);
        values.put(SQLiteHelper.COLUMN_GENDER, gender);

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

//    public Patient getPatientByName(String fullName){
//        final Cursor cursor = database.rawQuery("SELECT * " +
//                "FROM patients " +
//                "where "+SQLiteHelper.COLUMN_FIRSTNAME+" like '%"+fullName+"%' AND " +
//                SQLiteHelper.COLUMN_LASTNAME+ " like '%"+fullName+"%';", null);
//        Patient patient = null;
//        if (cursor != null) {
//            try {
//                if (cursor.moveToFirst()) {
//                    patient = cursorToComment(cursor);
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//        return patient;
//    }

    public Test createTest(long patient_id,String bloodPressure,String respiratory,String bloodOxygen,String testDate,String heartBeatRate,String covid) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_PATIENT_ID, patient_id);
        values.put(SQLiteHelper.COLUMN_BLOOD_PRESSURE, bloodPressure);
        values.put(SQLiteHelper.COLUMN_CHOLESTEROL, respiratory);
        values.put(SQLiteHelper.COLUMN_TEMPERATURE, bloodOxygen);
        values.put(SQLiteHelper.COLUMN_TEST_DATE, testDate);
        values.put(SQLiteHelper.COLUMN_HEART_BEAT_RATE, heartBeatRate);
        values.put(SQLiteHelper.COLUMN_COVID, covid);

        long insertId = database.insert(SQLiteHelper.TABLE_TESTS, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_TESTS,
                allColumnsTests, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Test newTest = cursorToTest(cursor);
        cursor.close();
        return newTest;
    }

    public Patient getPatientByName(String id) throws Exception{
        Patient storedPatient = dbHelper.getPatientById(String.valueOf(id));
        return storedPatient;
    }

    public List<Patient> getPatientByDepartment(String department) throws Exception{
        List<Patient> patients = dbHelper.getPatientByDepartment(department);
        return patients;
    }

    public void deletePatient(Patient patient) {
        long id = patient.getId();
        System.out.println("Comment deleted with id: " + id);
        // database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
        //         + " = " + id, null);

        database.delete(SQLiteHelper.TABLE_PATIENTS, SQLiteHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void deleteTest(Test test) {
        long id = test.getId();
        System.out.println("Comment deleted with id: " + id);
        // database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
        //         + " = " + id, null);

        database.delete(SQLiteHelper.TABLE_TESTS, SQLiteHelper.COLUMN_ID + " = ?",
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

    public List<Test> getAllTestsFromPatients(String patientID) {
        List<Test> tests = new ArrayList<Test>();

//        Cursor cursor = database.query(SQLiteHelper.TABLE_TESTS,
//                allColumnsTests, null, null, null, null, null);

        Cursor cursor = database.rawQuery(
                "select * from "+SQLiteHelper.TABLE_TESTS+" where "+SQLiteHelper.COLUMN_PATIENT_ID+" = '"+String.valueOf(patientID)+"';"
        ,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Test test = cursorToTest(cursor);
            tests.add(test);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tests;
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

    private Test cursorToTest(Cursor cursor) {
        Test test = new Test();

        test.setId(cursor.getLong(0));
        test.setPatient_id(cursor.getLong(1));
        test.setBloodPressure(cursor.getString(2));
        test.setCholesterol(cursor.getString(3));
        test.setTemperature(cursor.getString(4));
        test.setTestDate(cursor.getString(5));
        test.setHeartBeatRate(cursor.getString(6));
        test.setCovid(cursor.getString(7));

        return test;
    }
}
