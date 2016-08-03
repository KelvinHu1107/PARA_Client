package com.newnergy.para_client;

import android.os.AsyncTask;

/**
 * Created by GaoxinHuang on 2016/8/3.
 */
public class DataGetIntController extends AsyncTask<String,String,Integer> {
    @Override
    protected Integer doInBackground(String... params) {
        return ServerConnection.getIdFromServer(params[0], params[1], params[2]);
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        onResponse(result);
    }

    public void onResponse(Integer result) {
    }
}
