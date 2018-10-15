package com.ghy.katherinejy.first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RateCalActivity extends AppCompatActivity {
    float rate = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_cal);

        String title = getIntent().getStringExtra("title");
        rate = getIntent().getFloatExtra("rate",0f);

        ((TextView)findViewById(R.id.name)).setText(title);
        ((EditText)findViewById(R.id.inp2)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float v = Float.parseFloat(s.toString());
                ((TextView)findViewById(R.id.show2)).setText((v*rate/100.0)+"");
            }
        });
    }
}
