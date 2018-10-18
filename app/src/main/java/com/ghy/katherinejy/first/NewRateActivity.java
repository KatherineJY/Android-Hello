package com.ghy.katherinejy.first;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class NewRateActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private final String tag = "RateList";
    Handler handler;
    private  int what = 7;
    private ArrayList<RateItem> data;
    private ArrayList<HashMap<String,String>> retList;
    SimpleAdapter adapter;
    RateManager manager = null;
    SharedPreferences huilv = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = new RateManager(getApplicationContext());
        data = manager.listAll();
        retList = rateItem2HashMap(data);
        adapter = new SimpleAdapter(NewRateActivity.this,retList,R.layout.mylist,
                new String[]{"ItemTitle","ItemDetail"},
                new int[] {R.id.itemTitle,R.id.itemDetail});
        setListAdapter(adapter);

        huilv = getSharedPreferences("huilv", Context.MODE_PRIVATE);
        String updateTime = huilv.getString("updateTime","");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = format.format(Calendar.getInstance().getTime());
        if( updateTime.equals("") || !currentTime.equals(updateTime) ) {
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

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String updateTime = format.format(Calendar.getInstance().getTime());
                        SharedPreferences.Editor editor = huilv.edit();
                        editor.putString("updateTime",updateTime);

                        ArrayList<RateItem> new_data = hashMap2rateItem(retList);
                        manager.deleteAll();
                        manager.addAll(new_data);
                    }
                    super.handleMessage(msg);
                }
            };
        }

        getListView().setOnItemClickListener(this);

        getListView().setOnItemLongClickListener(this);

        getListView().setEmptyView(findViewById(R.id.nodata));
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
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(tag,"fail..");
        }
        Log.i(tag,"thread finish..");
        Message msg = handler.obtainMessage(what);
        msg.obj = rateList;
        handler.sendMessage(msg);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final int pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("notice")
                .setMessage("Delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        retList.remove(pos);
                        manager.delete(pos);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No",null);
        builder.create().show();
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

    private ArrayList<HashMap<String,String>> rateItem2HashMap(ArrayList<RateItem> data){
        ArrayList<HashMap<String,String>> res = new ArrayList<HashMap<String,String>>();
        for(RateItem item : data){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("ItemTitle",item.getCurName());
            map.put("ItemDetail",item.getCurRate());
        }
        return res;
    }

    private ArrayList<RateItem> hashMap2rateItem(ArrayList<HashMap<String,String>>res){
        ArrayList<RateItem> data = new ArrayList<RateItem>();
        for( HashMap<String,String> map : res){
            RateItem item = new RateItem(map.get("ItemTitle"),map.get("ItemDetail"));
            data.add(item);
        }
        return data;
    }
}
