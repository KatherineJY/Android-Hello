package com.ghy.katherinejy.first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

import org.w3c.dom.Text;


public class Health extends AppCompatActivity {

    private Button toTest;
    private EditText height;
    private EditText weight;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        toTest = (Button) findViewById(R.id.totest);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);

        toTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = height.getText().toString();
                double heightN=300,weightN=300;
                try {
                    heightN = Double.parseDouble(str);
                }
                catch(Exception e){}

                str = weight.getText().toString();
                try{
                    weightN = Double.parseDouble(str);
                }
                catch(Exception e){

                }

                if(heightN>250 || weightN>200 || heightN<=0 || weightN<=0){
                    Toast.makeText(Health.this,R.string.incorrect,Toast.LENGTH_SHORT).show();
                }
                else{
                    double bmi = weightN/heightN/heightN;
                    DecimalFormat df = new DecimalFormat("#.00");
                    String s = df.format(bmi);
                    result.setText("您的BMI指数是"+s);
                }
            }
        });
    }


}
