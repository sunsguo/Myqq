package com.example.gy.myqq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"deprecation"})
public class MainActivity extends AppCompatActivity {
    /* 定义账户和密码的控件变量，定义登陆按钮的控件变量 */
    private Button loginBt;
    private Button registerBt;
    private EditText qqNo, qqPsw;

    //进度对话框
    private ProgressDialog progressDialog;
    JSONParser jsonParser = new JSONParser();
    private String jsonData;    //服务器返回数据
    private String message; //服务器返回值
    private int success;    //服务器返回值
    public static String user_name; //qq账户名
    public static String BaseURL = "http://192.168.137.1:8081/myqq/";   //电脑ip

    //此处是服务器端的地址
    private static String url_register = BaseURL + "register.php";
    private static String url_login = BaseURL + "login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //账户和密码的控件变量赋值
        registerBt = (Button)findViewById(R.id.register_button);
        qqNo = (EditText)findViewById(R.id.login_edit_account);
        qqNo.requestFocus();
        qqPsw = (EditText)findViewById(R.id.login_edit_pwd);

        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qqNo.getText().toString().equals("") || qqPsw.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入账号密码", Toast.LENGTH_SHORT).show();
                }else{
                    new Register().execute();
                }
            }
        });

        loginBt = (Button)findViewById(R.id.login_button);
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qqNo.getText().toString().equals("") || qqPsw.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入账号密码", Toast.LENGTH_SHORT).show();
                }else{
                    new Login().execute();
                }
            }
        });
    }

    //qq注册的后台异步线程
    class Register extends AsyncTask<String, String, String>{
        String temp_qqNO = "";
        String temp_qqPWD = "";

        /**
         * 后台异步任务执行前显示一个progress Dialog
         */
        @Override
        protected void onPreExecute() {

            temp_qqNO = qqNo.getText().toString();
            temp_qqPWD = qqPsw.getText().toString();

            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("正在注册..");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        /**
         * 开始执行后台异步任务
         * @param args
         * @return
         */
        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("user_name", temp_qqNO));
            params.add(new BasicNameValuePair("user_password", temp_qqPWD));
            //将JSONparser的相应函数，以httppost 方式链接服务器
            try{
                jsonData = jsonParser.makeHttpRequest(url_register, "POST", params);
            }catch (Exception e){
                e.printStackTrace();
            }
            //获取服务器返回的JSON对象
            try{
                JSONObject jsonObject = new JSONObject(jsonData);
                message = jsonObject.getString("message");
                success = jsonObject.getInt("success");
            }catch (JSONException e){
                e.printStackTrace();
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
            return null;      //返回
        }

        /**
         * 异步任务完成后给出提示信息
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            String str = "" + success;
            Toast.makeText(getApplicationContext(), "返回码=" + str + " : " + message, Toast.LENGTH_SHORT).show();
        }
    }

    //qq登陆的后台异步线程
    class Login extends AsyncTask<String, String, String>{
        String temp_qqNO = "";
        String temp_qqPWD = "";

        /**
         * 后台异步任务执行前显示一个progress Dialog
         */
        @Override
        protected void onPreExecute() {
            temp_qqNO = qqNo.getText().toString();
            temp_qqPWD = qqPsw.getText().toString();

            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("正在登陆..");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        /**
         * 开始执行后台异步任务
         * @param args
         * @return
         */
        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("user_name", temp_qqNO));
            params.add(new BasicNameValuePair("user_password", temp_qqPWD));
            //将JSONparser的相应函数，以httppost 方式链接服务器
            try{
                jsonData = jsonParser.makeHttpRequest(url_login, "POST", params);
            }catch (Exception e){
                e.printStackTrace();
            }
            //获取服务器返回的JSON对象
            try{
                JSONObject jsonObject = new JSONObject(jsonData);
                message = jsonObject.getString("message");
                success = jsonObject.getInt("success");
            }catch (JSONException e){
                e.printStackTrace();
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
            return null;      //返回
        }

        /**
         * 异步任务完成后给出提示信息
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            String str = "" + success;
            Toast.makeText(getApplicationContext(), "返回码=" + str + " : " +  message, Toast.LENGTH_SHORT).show();
            if(success == 1){
                user_name = qqNo.getText().toString();
                Intent intent = new Intent(MainActivity.this, QQMainActivity.class);
                startActivity(intent);
            }
        }
    }
}
