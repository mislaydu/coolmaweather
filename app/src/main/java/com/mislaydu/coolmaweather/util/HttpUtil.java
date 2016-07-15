package com.mislaydu.coolmaweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP连接工具类HttpUtil
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address,final HttpCallbackListenner listenner){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url = new URL(address);
                    connection =(HttpURLConnection)url.openConnection(); // 实例化connection对象
                    connection.setRequestMethod("GET"); // 设置HTTP请求方式
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream(); // 获得输入流
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if(listenner!=null){
                        // 回调onFinish方法
                        listenner.onFinish(response.toString());
                    }
                }catch (Exception e){
                    // 回调onError方法
                    if(listenner!=null){
                        listenner.onError(e);
                    }
                }finally {
                    if(connection!=null){
                        connection.disconnect(); // 关闭Http连接
                    }
                }
            }
        }).start();
    }
}
