package com.urqa.common;

import android.util.Log;

import com.urqa.Collector.ErrorReport;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SendErrorProcess extends Thread
{
    private ErrorReport report;
    private String url;

    public SendErrorProcess(ErrorReport report)
    {
        this(report, "client/send/exception");
    }

    public SendErrorProcess(ErrorReport report, String url) {
        this.report = report;
        this.url = url;
    }

    public void run()
    {
        try {
            HttpClient client = new DefaultHttpClient();
            setHttpParams(client.getParams());

            HttpPost post = new HttpPost(StateData.ServerAddress + this.url);
            post.setHeader("Content-Type", "application/json; charset=utf-8");
            post.setEntity(toEntity(this.report));

            HttpResponse response = client.execute(post);

            int code = response.getStatusLine().getStatusCode();
            Log.i("UrQA", String.format("UrQA Response Code :: %d", new Object[] { Integer.valueOf(code) }));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHttpParams(HttpParams params) {
        params.setParameter("http.protocol.expect-continue", Boolean.valueOf(false));
        params.setParameter("http.connection.timeout", Integer.valueOf(5000));
        params.setParameter("http.socket.timeout", Integer.valueOf(5000));
    }

    private StringEntity toEntity(ErrorReport data) throws JSONException, IOException
    {
        String DATA = setData(data);
        return new StringEntity(DATA, "UTF-8");
    }

    private String setData(ErrorReport data) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("console_log", getLog(data));
        object.put("exception", data.ErrorData.toJSONObject());
        object.put("instance", getId(data));
        object.put("version", data.mUrQAVersion);
        return object.toString();
    }

    private JSONObject getLog(ErrorReport data) throws JSONException
    {
        JSONObject map = new JSONObject();
        map.put("data", data.LogData);

        return map;
    }

    private JSONObject getId(ErrorReport data) throws JSONException {
        JSONObject map = new JSONObject();
        map.put("id", data.mId);

        return map;
    }
}