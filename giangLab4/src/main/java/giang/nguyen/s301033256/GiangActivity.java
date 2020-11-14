package giang.nguyen.s301033256;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import giang.nguyen.s301033256.models.DataSource;
import giang.nguyen.s301033256.models.Patient;

public class GiangActivity extends AppCompatActivity {
    private DataSource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new DataSource(this);
        datasource.open();

        List<Patient> values = datasource.getAllPatients();
        Toast.makeText(getApplicationContext(),String.format("size is %d",values.size()), Toast.LENGTH_SHORT).show();

        Button goBtn = (Button)findViewById(R.id.button);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), NguyenAddPatientActivity.class);
                startActivity(startIntent);
            }
        });
    }
    protected void onResume() {
        datasource.open();
        super.onResume();
        List<Patient> values = datasource.getAllPatients();
        //Toast.makeText(getApplicationContext(),String.format("size is %d",values.size()), Toast.LENGTH_SHORT).show();
        for (Patient p : values){
            Toast.makeText(getApplicationContext(),p.getFullName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}