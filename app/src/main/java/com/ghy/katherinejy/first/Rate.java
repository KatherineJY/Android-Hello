package com.ghy.katherinejy.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Rate extends AppCompatActivity implements Runnable{

    EditText need;
    TextView output;
    private final String tag = "RateActivity";
    double dollor_per = 0;
    double euro_per = 0;
    double won_per = 0;
    SharedPreferences huilv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Thread t = new Thread(this);
        t.run();

        huilv = getSharedPreferences("huiyu",Context.MODE_PRIVATE);
        dollor_per = huilv.getFloat("dollor_per",0.0f);
        euro_per = huilv.getFloat("euro_per",0.0f);
        won_per = huilv.getFloat("won_per",0.0f);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if( requestCode==1 && resultCode==2 ){
            Bundle bundle = data.getExtras();
            dollor_per = bundle.getDouble("new_dollor",0.1);
            euro_per = bundle.getDouble("new_euro",0.1);
            won_per = bundle.getDouble("new_won",0.1);

            SharedPreferences.Editor editor = huilv.edit();
            editor.putFloat("dllor_per", (float)dollor_per);
            editor.putFloat("euro", (float)euro_per);
            editor.putFloat("won", (float)won_per);
            editor.apply();
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if( item.getItemId()==R.id.menu_set ){
            Intent intent = new Intent(this,Config.class);
            intent.putExtra("dollor_per",dollor_per);
            intent.putExtra("euro_per",euro_per);
            intent.putExtra("won_per",won_per);

            Log.i(tag,"open_config:dollor_per"+dollor_per);
            Log.i(tag,"open_config:euro_per"+euro_per);
            Log.i(tag,"open_config:won_per"+won_per);

            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void toConfig(View btn){
        Intent intent = new Intent(this,Config.class);
        intent.putExtra("dollor_per",dollor_per);
        intent.putExtra("euro_per",euro_per);
        intent.putExtra("won_per",won_per);

        Log.i(tag,"open_config:dollor_per"+dollor_per);
        Log.i(tag,"open_config:euro_per"+euro_per);
        Log.i(tag,"open_config:won_per"+won_per);

        startActivityForResult(intent,1);
    }



    public void onclick( View btn ){
        need = (EditText) findViewById(R.id.need);
        output = (TextView) findViewById(R.id.output);

        double d = Double.parseDouble(need.getText().toString());
        if( d<0 ){
            Toast.makeText(this, "wrong input", Toast.LENGTH_SHORT).show();
        }
        else{
            if( btn.getId()==R.id.dollor ){
                output.setText(String.format("%#.2f",d*dollor_per));
            }
            else if( btn.getId()==R.id.euro ){
                output.setText(String.format("%#.2f",d*euro_per));
            }
            else if( btn.getId()==R.id.won ){
                output.setText(String.format("%#.2f",d*won_per));
            }
        }
    }

     @Override
     public void run() {
        String urlString = "http://www.boc.cn/sourcedb/whpj/";
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            Log.i(tag,"try to fetch");
            if (responseCode == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String temp = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp);
                }
                String result = stringBuilder.toString();
                Log.i(tag, result);
            }
            Log.i(tag,responseCode+"");
            Log.i(tag,"fetch finish");
        }
        catch(Exception e){
            Log.i(tag,"fetch fail"+e.toString());
        }
      }
}
