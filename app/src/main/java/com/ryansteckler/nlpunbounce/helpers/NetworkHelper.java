package com.ryansteckler.nlpunbounce.helpers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by rsteckler on 10/1/14.
 */
public class NetworkHelper{

    private NetworkHelper(){}
    public static void getFromServer(final String url, final Handler handler) {
        //Post the content to the URL

        new Thread()
        {
            @Override
            public void run() {

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(url);

                try {
                    // Execute HTTP Get Request
                    HttpResponse response = httpclient.execute(httpget);
                    if (response.getStatusLine().getStatusCode() < 300) {
                        Message msg = new Message();
                        msg.what = 1;
                        Bundle b = new Bundle();
                        String responseStr = EntityUtils.toString(response.getEntity());
                        b.putString("global_stats", responseStr);
                        msg.setData(b);
                        handler.sendMessage(msg);
                        return;
                    }

                } catch (SecurityException | IOException ignored) {
                }
                handler.sendEmptyMessage(0);

            }
        }.start();
    }

    public static void sendToServer(final String content, final String url, final Handler handler) {
        //Post the content to the URL

        new Thread()
        {
            @Override
            public void run() {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

                try {
                    // Add data
                    httppost.setEntity(new  StringEntity(content));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    if (response.getStatusLine().getStatusCode() < 300) {
                        Message msg = new Message();
                        msg.what = 1;
                        Bundle b = new Bundle();
                        String responseStr = EntityUtils.toString(response.getEntity());
                        b.putString("global_stats", responseStr);
                        msg.setData(b);
                        handler.sendMessage(msg);
                        return;
                    }

                } catch (SecurityException | IOException ignored) {
                }
                handler.sendEmptyMessage(0);

            }
        }.start();
    }
}
