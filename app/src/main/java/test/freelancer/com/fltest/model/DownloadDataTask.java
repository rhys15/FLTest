package test.freelancer.com.fltest.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import test.freelancer.com.fltest.interfaces.JSONListener;

/**
 * Created by Android 17 on 5/20/2015.
 */
public class DownloadDataTask extends AsyncTask<Void, String, String>{


    private static final String TAG = DownloadDataTask.class.getSimpleName();
    private String url;
    private Context context;
    /*
    Interface
     */
    private JSONListener jsonListener;
    public DownloadDataTask(Context context, String url) {
        this.url = url;
        this.context = context;

    }

    @Override
    protected String doInBackground(Void... voids) {
        Log.i(TAG, "background download initialize");

        try {
            return HttpConnect.connect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        if(s != null)
            jsonListener.onDataFetch(s);
        else{
            jsonListener.onUnknownError("data is null");
        }

        super.onPostExecute(s);
    }

    public JSONListener getJsonListener() {
        return jsonListener;
    }

    public void setJsonListener(JSONListener jsonListener) {
        this.jsonListener = jsonListener;
    }
}
