package com.newnergy.para_client;

import android.os.AsyncTask;

/**
 * Created by GaoxinHuang on 2016/6/17.
 */
public class DataTransmitController extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
        return ServerConnection.getStringFromServer(params[0], params[1], params[2]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        onResponse(result);
    }

    public void onResponse(String result) {
    }

}