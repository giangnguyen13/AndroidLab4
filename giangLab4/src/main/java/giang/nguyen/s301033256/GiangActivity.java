package giang.nguyen.s301033256;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GiangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_before);

        Button viewAllPatients = (Button)findViewById(R.id.giangViewAllPatientsBtn);
        viewAllPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), NguyenViewPatientsActivity.class);
                startActivity(startIntent);
            }
        });

        Button addTestFab = findViewById(R.id.giangCreateNewTestBtn);
        Button addPatientFab = findViewById(R.id.giangCreateNewPatientBtn);

        addTestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), NguyenAddTestActivity.class);
                startActivity(startIntent);
            }
        });

        addPatientFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), NguyenAddPatientActivity.class);
                startActivity(startIntent);
            }
        });
    }
}