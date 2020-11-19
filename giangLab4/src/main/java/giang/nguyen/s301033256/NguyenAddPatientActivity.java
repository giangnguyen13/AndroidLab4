package giang.nguyen.s301033256;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import giang.nguyen.s301033256.models.DataSource;
import giang.nguyen.s301033256.models.Patient;
/**
 * Giang Nguyen
 * Student# 301033256
 * COMP304 002
 * Professor: Haki Sharifi
 * */
public class NguyenAddPatientActivity extends AppCompatActivity {
    private DataSource datasource;
    TextView firstNameTv;
    TextView lastNameTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, getResources().getStringArray(R.array.departments));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner departmentSpinner = (Spinner)findViewById(R.id.giangDepartmentSpinner);
        departmentSpinner.setAdapter(adapter);

        datasource = new DataSource(this);
        try{
            datasource.open();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }


        Button addPatient = (Button)findViewById(R.id.giangAddPatientBtn);

        firstNameTv = (TextView)findViewById(R.id.giangFirstNameInput);
        firstNameTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (firstNameTv.getText().toString().trim().length() < 3)
                        firstNameTv.setError("Please enter more than 2 characters");
                    else
                        firstNameTv.setError(null);
                }
            }
        });

        lastNameTv = (TextView)findViewById(R.id.giangLastNameInput);
        lastNameTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (lastNameTv.getText().toString().trim().length() < 3)
                        lastNameTv.setError("Please enter more than 2 characters");
                    else
                        lastNameTv.setError(null);
                }
            }
        });

        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Patient newPatient = null;
                    TextView text_sel = (TextView)departmentSpinner.getSelectedView();
                    RadioGroup genderRadioGroup = (RadioGroup)findViewById(R.id.giangGenderRadioGroup);
                    int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton)findViewById(selectedId);

                    String firstName = firstNameTv.getText().toString();
                    String lastName = lastNameTv.getText().toString();
                    String department = text_sel.getText().toString();
                    String gender = radioButton.getText().toString();

                    if (firstName.length() < 3 || lastName.length() < 3){
                        firstNameTv.setError("Please enter more than 2 characters");
                        lastNameTv.setError("Please enter more than 2 characters");
                        Toast.makeText(getApplicationContext(),"INVALID", Toast.LENGTH_SHORT).show();
                    }else{
                        try{
                            newPatient = datasource.createPatient(firstName,lastName,department,gender);
                            Intent startIntent = new Intent(getApplicationContext(), NguyenViewPatientsActivity.class);
                            startActivity(startIntent);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getApplicationContext(),"VALID", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    firstNameTv.setError("Please enter more than 2 characters");
                    lastNameTv.setError("Please enter more than 2 characters");
                }



            }
        });
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
}