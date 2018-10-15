package com.ghy.katherinejy.first;

import android.app.ListActivity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RateListActivity extends ListActivity implements  Runnable {

    private final String tag = "RateList";
    Handler handler;
    private  int what = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);

        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == what) {
                    List<String> list_data = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list_data);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        Log.i(tag,"get info from internet...");
        List<String> list_data = new ArrayList<String>();
        String urlString = "http://www.usd-cny.com/bankofchina.htm";
        try {
            Document doc = Jsoup.connect(urlString).get();
            Elements trs = doc.select("table").select("tr");
            int i;
            for(i=0;i<trs.size();i++){
                Elements tds = trs.get(i).select("td");
                if(tds.size()!=0){
                    String name = tds.get(0).select("a").text();
                    String value = tds.get(5).text();
                    list_data.add(name+":"+value);
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(what);
        msg.obj = list_data;
        handler.sendMessage(msg);
    }

}
