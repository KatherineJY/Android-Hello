package com.ghy.katherinejy.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Rate extends AppCompatActivity {

    EditText need;
    TextView output;
    private String tag;
    double dollor_per = 0.1;
    double euro_per = 0.2;
    double won_per = 0.3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if( requestCode==1 && resultCode==2 ){
            Bundle bundle = data.getExtras();
            dollor_per = bundle.getDouble("new_dollor",0.1);
            euro_per = bundle.getDouble("new_euro",0.1);
            won_per = bundle.getDouble("new_won",0.1);

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
                output.setText(String.format("%#.2f",d*euro_per
                ));
            }
            else if( btn.getId()==R.id.won ){
                output.setText(String.format("%#.2f",d*won_per));
            }
        }


    }
}
