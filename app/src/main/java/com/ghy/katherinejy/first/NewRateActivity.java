package com.ghy.katherinejy.first;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewRateActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private final String tag = "RateList";
    Handler handler;
    private  int what = 7;
    private ArrayList<HashMap<String,String>> retList;
    SimpleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_new_rate);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == what) {
                    Log.i(tag,"get msg");
                    retList = (ArrayList<HashMap<String, String>>) msg.obj;
                    adapter = new SimpleAdapter(NewRateActivity.this,retList,R.layout.mylist,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[] {R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(adapter);

                }
                super.handleMessage(msg);
            }
        };

        getListView().setOnItemClickListener(this);

        getListView().setOnItemLongClickListener(this);

        //getListView().setEmptyView((View) findViewById(R.layout.empty_view));
    }

    @Override
    public void run() {
        Log.i(tag,"get info from internet...");
        List<HashMap<String,String>> rateList = new ArrayList<HashMap<String, String>>();
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
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("ItemTitle",name);
                    map.put("ItemDetail",value);
                    rateList.add(map);
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(tag,"fail..");
        }

        Message msg = handler.obtainMessage(what);
        msg.obj = rateList;
        handler.sendMessage(msg);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        retList.remove(position);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if( onItemLongClick(parent,view,position,id) )
            return;
        HashMap<String,String> map = (HashMap<String,String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String valueStr = map.get("ItemDetail");

        Intent rateCalc = new Intent(NewRateActivity.this,RateCalActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(valueStr));
        NewRateActivity.this.startActivity(rateCalc);
    }
}
