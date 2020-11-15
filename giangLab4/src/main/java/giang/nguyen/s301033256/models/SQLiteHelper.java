package giang.nguyen.s301033256.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{
    //-- PATIENTS TABLE --//
    public static final String TABLE_PATIENTS = "patients";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_DEPARTMENT = "department";

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
                    "    %s TEXT\n" +
                    ");",
            TABLE_PATIENTS,
            COLUMN_ID,
            COLUMN_FIRSTNAME,
            COLUMN_LASTNAME,
            COLUMN_DEPARTMENT
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
}
