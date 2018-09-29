package com.ghy.katherinejy.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Config extends AppCompatActivity {

    private final String TAG = "ConfigActivity";

    EditText dollorText;
    EditText euroText;
    EditText wonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent intent = getIntent();
        double dollor = intent.getDoubleExtra("dollor_per",0.0);
        double euro = intent.getDoubleExtra("euro_per",0.0);
        double won = intent.getDoubleExtra("won_per",0.0);

        Log.i(TAG,"onCreat dollor"+dollor);
        Log.i(TAG,"onCreat euro"+euro);
        Log.i(TAG,"onCreat won"+won);

        dollorText = (EditText)findViewById(R.id.configDollor);
        euroText = (EditText)findViewById(R.id.configEuro);
        wonText = (EditText)findViewById(R.id.configWon);

        dollorText.setText(String.valueOf(dollor));
        euroText.setText(String.valueOf(euro));
        wonText.setText(String.valueOf(won));

    }

    public  void save(View Button){
        Log.i(TAG,"save:");

        double newD = Double.parseDouble(dollorText.getText().toString());
        double newE = Double.parseDouble(euroText.getText().toString());
        double newW = Double.parseDouble(wonText.getText().toString());

        Log.i(TAG,"new value:"+newD+" "+newE+" "+newW);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putDouble("new_dollor",newD);
        bundle.putDouble("new_euro",newE);
        bundle.putDouble("new_won",newW);
        intent.putExtras(bundle);
        setResult(2,intent);

        finish();
    }
}
