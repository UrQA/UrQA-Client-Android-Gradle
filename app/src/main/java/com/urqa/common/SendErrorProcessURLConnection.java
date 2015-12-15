package com.urqa.common;

import android.util.Base64;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SendErrorProcessURLConnection extends Thread
{
    private ErrorReport report;
    private String url;
    private String filename;

    public SendErrorProcessURLConnection(ErrorReport report, String url, String FileName)
    {
        this.report = report;
        this.url = url;
        this.filename = FileName;
    }

    public void run()
    {
        try
        {
            FileInputStream fis = new FileInputStream(this.filename);
            File dmp_file = new File(this.filename);
            byte[] byteArr = new byte[(int)dmp_file.length()];

            fis.read(byteArr);
            fis.close();

            this.report.NativeData = Base64.encodeToString(byteArr, 2);
            dmp_file.delete();

            HttpClient client = new DefaultHttpClient();
            setHttpParams(client.getParams());

            client.getParams().setParameter("http.protocol.expect-continue", Boolean.valueOf(false));
            client.getParams().setParameter("http.connection.timeout", Integer.valueOf(5000));
            client.getParams().setParameter("http.socket.timeout", Integer.valueOf(5000));

            HttpPost post = new HttpPost(StateData.ServerAddress + this.url);

            post.setHeader("Content-Type", "application/json; charset=utf-8");
            post.setEntity(toEntity(this.report));

            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode(); Log.i("UrQA",
                String.format("UrQA Response Code[Native] :: %d", new Object[] { Integer.valueOf(code) }));
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

    private StringEntity toEntity(ErrorReport data) throws JSONException, IOException {
        String DATA = setData(data);
        return new StringEntity(DATA, "UTF-8");
    }

    private String setData(ErrorReport data) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("console_log", getLog(data));
        object.put("exception", data.ErrorData.toJSONObject());
        object.put("instance", getId(data));
        object.put("version", data.mUrQAVersion);
        object.put("dump_data", data.NativeData);
        return object.toString();
    }

    private JSONObject getLog(ErrorReport data) throws JSONException {
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