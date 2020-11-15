package giang.nguyen.s301033256;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import giang.nguyen.s301033256.models.DataSource;
import giang.nguyen.s301033256.models.Patient;
import giang.nguyen.s301033256.models.Test;

public class NguyenAddTestActivity extends AppCompatActivity {
    private DataSource datasource;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        datasource = new DataSource(this);
        try{
            datasource.open();
            List<Test> values = datasource.getAllTests();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

        Button addTestBtn = (Button)findViewById(R.id.giangAddTestBtn);
        addTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test newTest = null;
                TextView cholesterolTv = (TextView)findViewById(R.id.giangCholesterolInput);
                TextView temperatureTv = (TextView)findViewById(R.id.giangTemperatureInput);
                TextView testDateTv = (TextView)findViewById(R.id.giangTestDate);
                RadioGroup bloodPressureRadio = (RadioGroup)findViewById(R.id.giangBloodPressureRadio);

                int selectedId = bloodPressureRadio.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(NguyenAddTestActivity.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();

                String cholesterol = cholesterolTv.getText().toString();
                String temperature = temperatureTv.getText().toString();
                String testDate = testDateTv.getText().toString();
                String bloodPressure = radioButton.getText().toString();

                String info = String.format("%s %s %s %s",bloodPressure,cholesterol,temperature,testDate);

                try{
                    newTest = datasource.createTest(99,bloodPressure,cholesterol,temperature,testDate);
                    Toast.makeText(getApplicationContext(),String.format("Create success, Test ID#%d",newTest.getId()), Toast.LENGTH_SHORT).show();
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
            List<Test> values = datasource.getAllTests();
            for (Test t : values){
                String testInfo = String.format("Test#%d Patient #%d %s",t.getId(),t.getPatient_id(),t.getCholesterol());
                Toast.makeText(getApplicationContext(),testInfo, Toast.LENGTH_SHORT).show();
            }
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