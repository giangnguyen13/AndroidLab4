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
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient newPatient = null;
                TextView firstNameTv = (TextView)findViewById(R.id.giangFirstNameInput);
                TextView lastNameTv = (TextView)findViewById(R.id.giangLastNameInput);
                TextView text_sel = (TextView)departmentSpinner.getSelectedView();
                RadioGroup genderRadioGroup = (RadioGroup)findViewById(R.id.giangGenderRadioGroup);
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)findViewById(selectedId);

                String firstName = firstNameTv.getText().toString();
                String lastName = lastNameTv.getText().toString();
                String department = text_sel.getText().toString();
                String gender = radioButton.getText().toString();

                String info = String.format("%s %s %s",firstName,lastName,department);


                try{
                    newPatient = datasource.createPatient(firstName,lastName,department,gender);
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
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}