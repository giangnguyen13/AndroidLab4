package giang.nguyen.s301033256;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import giang.nguyen.s301033256.models.DataSource;
import giang.nguyen.s301033256.models.Patient;

public class NguyenPatientSearchActivity extends AppCompatActivity {
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);
        try {
            datasource = new DataSource(this);
            datasource.open();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }


        final Spinner departmentSpinner = (Spinner)findViewById(R.id.giangDepartmentSpinnerFilter);

        Button searchByIDBtn = (Button)findViewById(R.id.giangSearchByIdBtn);
        Button filterByDepartmentBtn = (Button)findViewById(R.id.giangFilterDepartmentBtn);
        searchByIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text_sel = (TextView)findViewById(R.id.giangPatientIdInput);
                Patient storedPatient = null;
                try {
                    storedPatient = datasource.getPatientByName(text_sel.getText().toString());
                    Toast.makeText(getApplicationContext(),storedPatient.getFullName(),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });

        filterByDepartmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text_sel = (TextView)departmentSpinner.getSelectedView();
                String department = text_sel.getText().toString();
                Toast.makeText(getApplicationContext(),department,Toast.LENGTH_SHORT).show();
                List<Patient> patients = new ArrayList<Patient>();
                try {
                    patients = datasource.getPatientByDepartment(text_sel.getText().toString());
                    setContentView(R.layout.activity_search_patient_result);
                    for(Patient p : patients){
                        addPatientToLayout(p);
                        //Toast.makeText(getApplicationContext(),String.format(p.getFullName()),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addPatientToLayout(final Patient patient) {
        LinearLayout linearLayout = findViewById(R.id.giangPatientResultLinearLayout);
        Button name = new Button(this);
        name.setText(patient.getFullName());
        name.setTextColor(getResources().getColor(R.color.main_text_color));
        name.setTextSize(getResources().getDimension(R.dimen.very_small_text));
        TextView department = new TextView(this);
        department.setText(patient.getDepartment());
        department.setTextColor(getResources().getColor(R.color.white));
        department.setTextSize(getResources().getDimension(R.dimen.x_small_text));
        department.setBackgroundColor(getResources().getColor(R.color.success));
        department.setGravity(Gravity.CENTER);
        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                5
        ));
        v.setBackgroundColor(getResources().getColor(R.color.black));

        linearLayout.addView(name);
        linearLayout.addView(department);
        linearLayout.addView(v);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent startIntent = new Intent(getApplicationContext(), GiangViewTestActivity.class);
//                startIntent.putExtra("patientID", patient.getId());
//                startActivity(startIntent);
                Intent startIntent = new Intent(getApplicationContext(), GiangViewTestActivity.class);
                startIntent.putExtra("patientID",String.format("%d",patient.getId()));
                startActivity(startIntent);
                //Toast.makeText(getApplicationContext(),String.format("id#%d",patient.getId()),Toast.LENGTH_LONG).show();
            }
        });
    }
}