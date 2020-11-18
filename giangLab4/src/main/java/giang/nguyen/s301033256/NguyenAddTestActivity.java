package giang.nguyen.s301033256;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
//import android.icu.util.Calendar;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import giang.nguyen.s301033256.models.DataSource;
import giang.nguyen.s301033256.models.Patient;
import giang.nguyen.s301033256.models.Test;

public class NguyenAddTestActivity extends AppCompatActivity {
    private DataSource datasource;
    private RadioButton radioButton;
    DatePickerDialog picker;
    EditText datePicker;
    EditText cholesterolInput;
    EditText temperatureInput;
    List<Patient> patients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        datasource = new DataSource(this);
        try{
            datasource.open();
            patients = datasource.getAllPatients();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(this, R.layout.spinner_item, patients);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner selectPatient = (Spinner)findViewById(R.id.giangSelectPatientDropdown);
        selectPatient.setAdapter(adapter);

        datePicker = (EditText) findViewById(R.id.giangTestDate);
        cholesterolInput = (EditText) findViewById(R.id.giangCholesterolInput);
        temperatureInput = (EditText) findViewById(R.id.giangTemperatureInput);

        datePicker.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                datePicker.setInputType(InputType.TYPE_NULL);
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(NguyenAddTestActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datePicker.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        Button addTestBtn = (Button)findViewById(R.id.giangAddTestBtn);
        addTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test newTest = null;
                RadioGroup bloodPressureRadio = (RadioGroup)findViewById(R.id.giangBloodPressureRadio);

                int selectedId = bloodPressureRadio.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton)findViewById(selectedId);

                Toast.makeText(NguyenAddTestActivity.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();

                String cholesterol = cholesterolInput.getText().toString();
                String temperature = temperatureInput.getText().toString();
                String testDate = datePicker.getText().toString();
                String bloodPressure = radioButton.getText().toString();

                String info = String.format("%s %s %s %s",bloodPressure,cholesterol,temperature,testDate);
                Patient patient = (Patient)selectPatient.getSelectedItem();


                try{
                    newTest = datasource.createTest(patient.getId(),bloodPressure,cholesterol,temperature,testDate);
                    Toast.makeText(getApplicationContext(),String.format("Create success, Test ID#%d, Patient ID#%d",newTest.getId(),patient.getId()), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(),info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
        try{
//            List<Test> values = datasource.getAllTests();
//            for (Test t : values){
//                String testInfo = String.format("Test#%d Patient #%d %s",t.getId(),t.getPatient_id(),t.getCholesterol());
//                //Toast.makeText(getApplicationContext(),testInfo, Toast.LENGTH_SHORT).show();
//            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}