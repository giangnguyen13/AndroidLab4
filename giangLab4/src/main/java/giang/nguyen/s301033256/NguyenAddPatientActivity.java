package giang.nguyen.s301033256;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
                            Intent startIntent = new Intent(getApplicationContext(), GiangActivity.class);
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
}