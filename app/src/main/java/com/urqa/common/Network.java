package com.urqa.common;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.urqa.externallibrary.okhttp.MediaType;
import com.urqa.externallibrary.okhttp.OkHttpClient;
import com.urqa.externallibrary.okhttp.Request;
import com.urqa.externallibrary.okhttp.RequestBody;
import com.urqa.externallibrary.okhttp.Response;

import java.util.concurrent.TimeUnit;


public class Network extends Thread {

    public enum Method {
        GET, POST
    }

    /**
     * Media type for HTTP header
     */
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

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

            OkHttpClient client = new OkHttpClient();
            setTimeout(client);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            if (handler != null) {
                Message msg = new Message();
                msg.obj = response.body().string();
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
            OkHttpClient client = new OkHttpClient();
            setTimeout(client);
            RequestBody body = RequestBody.create(JSON, data);
            Request.Builder r = new Request.Builder()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .addHeader("version", "1.0.0")
                    .url(url)
                    .post(body);
            if (isEncrypt && Encryptor.baseKey != null && Encryptor.token != null) {
                r.addHeader("Urqa-Encrypt-Opt", "aes-256-cbc-pkcs5padding+base64");
                data = Encryptor.encrypt(data);
                Log.e(StateData.URQA_SDK_LOG, data);
            }
            Response response = client.newCall(r.build()).execute();
            if (handler != null) {
                Message msg = new Message();
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
            int statusCode = response.code();
            Log.e(StateData.URQA_SDK_LOG, String.format("UrQA Response Code : %d", statusCode));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTimeout(OkHttpClient client) {
        client.setConnectTimeout(5, TimeUnit.SECONDS);
        client.setReadTimeout(5, TimeUnit.SECONDS);
    }

}
