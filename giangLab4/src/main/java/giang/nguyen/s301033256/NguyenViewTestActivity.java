package giang.nguyen.s301033256;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import giang.nguyen.s301033256.models.DataSource;
import giang.nguyen.s301033256.models.Patient;
import giang.nguyen.s301033256.models.Test;
/**
 * Giang Nguyen
 * Student# 301033256
 * COMP304 002
 * Professor: Haki Sharifi
 * */
public class NguyenViewTestActivity extends AppCompatActivity {
    private DataSource datasource;
    List<Test> tests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tests);
        datasource = new DataSource(this);
        datasource.open();

        if (getIntent().hasExtra("patientID")){
            String str = getIntent().getExtras().getString("patientID");
            try{
                tests = datasource.getAllTestsFromPatients(str);
                for (Test t : tests){
                    addElementToLayout(t);
                }
            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder;
        //Toast.makeText(getApplicationContext(),"BACK BTN CLICK",Toast.LENGTH_SHORT).show();
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit.?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Quit Application");
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search_patient:
                Intent startIntent = new Intent(getApplicationContext(), NguyenPatientSearchActivity.class);
                startActivity(startIntent);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addElementToLayout(final Test t){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.giangPatientTestListLinearLayout);
        Button name = new Button(this);
        name.setText(String.format("View Test id# %d",t.getId()));
        name.setTextColor(getResources().getColor(R.color.main_text_color));
        name.setTextSize(getResources().getDimension(R.dimen.very_small_text));

        linearLayout.addView(name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSingleTest(t);
            }
        });
    }

    private void viewSingleTest(final Test test){
        setContentView(R.layout.activity_add_test);
        Patient patient = null;
        try {
            patient = datasource.getPatientByName(String.format("%d",test.getPatient_id()));
        }catch (Exception e){

        }
        List<Patient> patients = new ArrayList<Patient>(){};
        patients.add(patient);
        ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(this, R.layout.spinner_item, patients);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner selectPatient = (Spinner)findViewById(R.id.giangSelectPatientDropdown);
        selectPatient.setAdapter(adapter);

        final EditText datePicker = (EditText) findViewById(R.id.giangTestDate);
        final EditText cholesterolInput = (EditText) findViewById(R.id.giangCholesterolInput);
        final EditText temperatureInput = (EditText) findViewById(R.id.giangTemperatureInput);
        final EditText heartRateInput = (EditText) findViewById(R.id.giangHeartBeatRate);

        datePicker.setText(test.getTestDate());
        cholesterolInput.setText(test.getCholesterol());
        temperatureInput.setText(test.getTemperature());
        heartRateInput.setText(test.getHeartBeatRate());

        //disable input type
        datePicker.setInputType(InputType.TYPE_NULL);
        cholesterolInput.setInputType(InputType.TYPE_NULL);
        temperatureInput.setInputType(InputType.TYPE_NULL);
        heartRateInput.setInputType(InputType.TYPE_NULL);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.giangBloodPressureRadio);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
            if ("Positive".equals(test.getBloodPressure())){
                ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
            }else {
                ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
            }

        }

        RadioGroup radioGroupCovid = (RadioGroup)findViewById(R.id.giangCovidRadio);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroupCovid.getChildAt(i).setEnabled(false);
            if ("Positive".equals(test.getCovid())){
                ((RadioButton)radioGroupCovid.getChildAt(0)).setChecked(true);
            }else {
                ((RadioButton)radioGroupCovid.getChildAt(1)).setChecked(true);
            }

        }

        Button addTestBtn = (Button)findViewById(R.id.giangAddTestBtn);
        addTestBtn.setText("Delete this record");
        addTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    datasource.open();
                    datasource.deleteTest(test);
                    Intent startIntent = new Intent(getApplicationContext(), NguyenPatientSearchActivity.class);
                    startActivity(startIntent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}