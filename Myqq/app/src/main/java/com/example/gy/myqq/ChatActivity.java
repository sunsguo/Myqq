package com.example.gy.myqq;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy on 2017/1/17.
 */

public class ChatActivity extends Activity implements View.OnClickListener{
    private Button btnSend;
    private Button btnBack;
    private TextView textView;
    private EditText editTextContent;
    private ListView listView;
    private ChatMsgViewAdapter adapter;
    private List<ChatMsgEntity> dataArrays = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();

    private String jsonData;
    private String message;
    private String tempMessage = "";
    private int success;
    private String far_user_name;
    private String send_content;

    //发送信息地址
    private static String url_sendmsg = MainActivity.BaseURL + "sendmsg.php";
    private static String url_getmsg = MainActivity.BaseURL + "getmsg.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.chat_laomi);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.initView();

        initData();

        new Thread(runnable).start();

    }

    private void initView(){
        textView = (TextView)findViewById(R.id.textView1);
        textView.setText(MainActivity.user_name);
        listView = (ListView)findViewById(R.id.listView1);
        btnSend = (Button)findViewById(R.id.button2);
        btnSend.setOnClickListener(this);
        btnBack = (Button)findViewById(R.id.button1);
        btnBack.setOnClickListener(this);

        editTextContent = (EditText)findViewById(R.id.editText1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                sendMsg();
                break;
            case R.id.button1:
                finish();   //返回上一页
                break;
        }
    }

    //发送消息
    private void sendMsg(){
        String contString = editTextContent.getText().toString();
        if(contString.length() > 0){
            send_content = contString;
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName(MainActivity.user_name);
            entity.setMsgType(false);
            entity.setText(contString);

            dataArrays.add(entity);
            adapter.notifyDataSetChanged();
            editTextContent.setText("");
            listView.setSelection(listView.getCount() - 1);

            new SendMsg().execute();
        }
    }

    private void getMsg(){
        new GetMsg().execute();
    }

    private String[] msgArray = new String[]{"安卓很有前途哦！"};

    public void initData(){
        ChatMsgEntity entity = new ChatMsgEntity();
        entity.setText("大米群");
        entity.setMsgType(true);

        entity.setText(msgArray[0]);
        dataArrays.add(entity);
        adapter = new ChatMsgViewAdapter(this, dataArrays);
        listView.setAdapter(adapter);
    }

    /**
     * qq发送信息的后台异步任务
     */
    @SuppressWarnings({"deprecation"})
    class SendMsg extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("send_content", send_content));
            try{
                jsonData = jsonParser.makeHttpRequest(url_sendmsg, "POST", pairs);
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                JSONObject jsonObject = new JSONObject(jsonData);
                message = jsonObject.getString("message");
                success = jsonObject.getInt("success");
            }catch (JSONException e){
                Log.e("log_tag", "Error parsing data " + e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            String str = "" + success;
            Toast.makeText(getApplicationContext(), "返回码=" + str + " : " + message, Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }

    @SuppressWarnings({"deprecation"})
    class GetMsg extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("user_name", MainActivity.user_name));
            try{
                jsonData = jsonParser.makeHttpRequest(url_getmsg, "POST", pairs);
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                JSONObject jsonObject = new JSONObject(jsonData);
                message = jsonObject.getString("message");
                success = jsonObject.getInt("success");
                far_user_name = jsonObject.getString("user_name");
            }catch (JSONException e){
                Log.e("log_tag", "Error parsing data " + e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            String str = "" + success;
            Toast.makeText(getApplicationContext(), "返回码=" + str + " : " + message, Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);

            Log.d("tempMessage=", tempMessage);
            Log.d("message", message);
            if(success==1 && !tempMessage.equals(message)){
                tempMessage = message;
                String contString = message;
                if(contString.length() > 0){
                    ChatMsgEntity entity = new ChatMsgEntity();
                    entity.setName(far_user_name);
                    entity.setMsgType(true);
                    entity.setText(contString);
                    //本地显示
                    dataArrays.add(entity);
                    adapter.notifyDataSetChanged();
                    editTextContent.setText("");
                    listView.setSelection(listView.getCount() - 1);
                }
            }
        }
    }

    //一个异步线程
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                new GetMsg().execute();
                try{
                    Thread.sleep(1500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    };
}
