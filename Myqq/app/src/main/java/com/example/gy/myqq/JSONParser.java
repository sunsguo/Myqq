package com.example.gy.myqq;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by gy on 2017/1/16.
 */

@SuppressWarnings({"deprecation"})
public class JSONParser {
    static InputStream is = null;   //JSON输入流
    static String json = "";    //将JSON输入流转换为字符串
    public static String PHPSESSID = null;  //Session会话id

    public JSONParser(){}

    //通过HTTP POST方式链接指定url
    public String makeHttpRequest(String url, String method, List<NameValuePair> params){
        //进行Http请求
        try{
            //通过http post 的方式链接指定url
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            //第一次一般是还未赋值，若有值则将SessionId发发给服务器
            if(PHPSESSID != null){
                httpPost.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
            }
            //设置默认的http客户端
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //执行http链接
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();   //返回值
            CookieStore cookieStore = httpClient.getCookieStore();
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i=0; i<cookies.size(); i++){
                if("PHPSESSID".equals(cookies.get(i).getName())){
                    PHPSESSID = cookies.get(i).getValue();
                    break;
                }
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8")); //从JOSN输入流中读取信息
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line + "\n");
            }
            bufferedReader.close();
            if (is != null){
                is.close();
            }
            json = sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return json;    //返回json值
    }
}
