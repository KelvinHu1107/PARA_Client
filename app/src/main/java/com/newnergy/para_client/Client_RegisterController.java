package com.newnergy.para_client;

import android.os.AsyncTask;

/**
 * Created by GaoxinHuang on 2016/6/2.
 */
public class Client_RegisterController extends AsyncTask<String, String, Boolean> {
    @Override
    protected Boolean doInBackground(String...  params) {
        return ServerConnection.validateDataToServer(params[0],params[1],params[2]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        onResponse(result);
    }

    public void onResponse(Boolean result) {
    }
}
