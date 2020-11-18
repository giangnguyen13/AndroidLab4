package giang.nguyen.s301033256;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import giang.nguyen.s301033256.models.DataSource;
import giang.nguyen.s301033256.models.Patient;

public class GiangActivity extends AppCompatActivity {
    private DataSource datasource;
    FloatingActionButton fab;
    FloatingActionButton addTestFab;
    FloatingActionButton addPatientFab;
    boolean visible_flag = false;
    List<Patient> values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new DataSource(this);
        datasource.open();

        fab = findViewById(R.id.giangFab);
        addTestFab = findViewById(R.id.giangAddTestFab);
        addPatientFab = findViewById(R.id.giangAddPatientFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFab(visible_flag);
            }
        });

        addTestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), NguyenAddPatientActivity.class);
                startActivity(startIntent);
            }
        });

        addPatientFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), NguyenAddTestActivity.class);
                startActivity(startIntent);
            }
        });
        values = datasource.getAllPatients();
        loadPatients(values);
    }
    protected void onResume() {
        datasource.open();
        super.onResume();
        toggleFab(true);
        //values = datasource.getAllPatients();
        //loadPatients(values);
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
        toggleFab(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);//back btn
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

    private void toggleFab(boolean visible){
        if (visible){
            addPatientFab.hide();
            addTestFab.hide();
            visible_flag = false;
        }else{
            addPatientFab.show();
            addTestFab.show();
            visible_flag = true;
        }
    }

    private void loadPatients(List<Patient> patients){
        for(Patient p : patients){
            addPatientToLayout(p);
        }
    }

    private void addPatientToLayout(Patient patient) {
        LinearLayout linearLayout = findViewById(R.id.giangPatientListLinearLayout);
        TextView name = new TextView(this);
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
    }
}