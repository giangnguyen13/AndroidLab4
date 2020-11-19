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
import android.widget.Button;
/**
 * Giang Nguyen
 * Student# 301033256
 * COMP304 002
 * Professor: Haki Sharifi
 * */
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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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