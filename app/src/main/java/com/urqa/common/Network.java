package com.urqa.common;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Network extends Thread {

    public enum Method {
        GET, POST
    }

    private String url;
    private String data;
    private Method method;
    private boolean isEncrypt;
    private Handler handler;

    public void setNetworkOption(String url, String data, Method method, boolean isEncrypt) {
        this.url = url;
        this.data = data;
        this.method = method;
        this.isEncrypt = isEncrypt;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        switch (method) {
        case GET:
            sendGetMethod();
            break;
        case POST:
            sendPostMethod();
            break;
        }
    }

    private void sendGetMethod() {
        try {
            checkAssert();
            HttpClient client = new DefaultHttpClient();
            setHttpParams(client.getParams());

            HttpGet get = new HttpGet(url);
            HttpResponse responseGet = client.execute(get);

            if (handler != null) {
                String response = convertResponseToString(responseGet);
                Message msg = new Message();
                msg.obj = response;
                handler.sendMessage(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAssert() {
        if (url == null || data == null || method == null)
            throw new IllegalStateException("you might miss setNetworkOption method");
    }

    private void sendPostMethod() {
        try {
            checkAssert();
            HttpClient client = new DefaultHttpClient();
            setHttpParams(client.getParams());

            HttpPost post = new HttpPost(url);

            post.setHeader("Content-Type", "application/json; charset=utf-8");
            post.addHeader("version", "1.0.0");

            if (isEncrypt && Encryptor.baseKey != null && Encryptor.token != null) {
                post.addHeader("Urqa-Encrypt-Opt", "aes-256-cbc-pkcs5padding+base64");
                data = Encryptor.encrypt(data);
                Log.e(StateData.URQA_SDK_LOG, data);
            }

            StringEntity input = new StringEntity(data, "UTF-8");
            //Log.e("data in network", input.toString());
            post.setEntity(input);
            HttpResponse responsePOST = client.execute(post);

            if (handler != null) {
                String responseResult = convertResponseToString(responsePOST);
                Message msg = new Message();
                msg.obj = responseResult;
                handler.sendMessage(msg);
            }

            int code = responsePOST.getStatusLine().getStatusCode();

            Log.e(StateData.URQA_SDK_LOG, String.format("UrQA Response Code : %d", code));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertResponseToString(HttpResponse response) throws IllegalStateException,
            IOException {

        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void setHttpParams(HttpParams params) {
        params.setParameter("http.protocol.expect-continue", false);
        params.setParameter("http.connection.timeout", 5000);
        params.setParameter("http.socket.timeout", 5000);
    }

}
