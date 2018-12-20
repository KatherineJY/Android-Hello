package com.ghy.katherinejy.first;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnApprove = (Button)findViewById(R.id.btn_approve);
        Button btnObject = (Button)findViewById(R.id.btn_object);
        Button btnAbstain = (Button)findViewById(R.id.btn_abstain);

        btnApprove.setOnClickListener(this);
        btnObject.setOnClickListener(this);
        btnAbstain.setOnClickListener(this);

    }
/*
    private String doVote(String voteStr){
        String retStr = "";
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("r=").append(URLEncoder.encode(voteStr,"utf-8"));

            byte[] data = stringBuffer.toString().getBytes();
            String urlPath = "http://192.168.32.1:8080/vote/GetVote";
            URL url = new URL(urlPath);

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length",String.valueOf(data.length));

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();
            Log.i("ii",response+"");
            if( response == HttpURLConnection.HTTP_OK ) {
                InputStream inputStream = httpURLConnection.getInputStream();
                retStr = inputStreamToString(inputStream);
                Log.i("ii","HTTP_OK");
            }
        }
        catch(Exception e){
            Log.i("ii","Exception:" +e.toString());
        }
        Log.i("ii",retStr);
        return retStr;
    }
*/

    private String doVote(String voteStr){
        String retStr = "";
        Log.i("vote", "doVote() voteStr:" + voteStr);

        try {

            StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
            stringBuffer.append("r=").append(URLEncoder.encode(voteStr, "utf-8"));

            byte[] data = stringBuffer.toString().getBytes();
            String urlPath = "http://192.168.32.1:8080/vote/GetVote";
            URL url = new URL(urlPath);

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);     //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            Log.i("vote", response+"");
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                retStr = inputStreamToString(inputStream);                     //处理服务器的响应结果
                Log.i("vote", "retStr:" + retStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        String resultData = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;

        while((len=inputStream.read(data))!=-1){
            byteArrayOutputStream.write(data,0,len);
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    @Override
    public void onClick(View btn) {
        switch (btn.getId()){
            case R.id.btn_approve:
                new VoteTask().execute("赞成");
                break;
            case R.id.btn_object:
                new VoteTask().execute("反对");
                break;
            case R.id.btn_abstain:
                new VoteTask().execute("弃权");
                break;
        }
    }

    private class VoteTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String ret = doVote(params[0]);
            return ret;
        }

        @Override
        protected void onPostExecute(String s){
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }
}

