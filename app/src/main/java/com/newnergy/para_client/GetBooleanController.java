package com.newnergy.para_client;

import android.os.AsyncTask;

/**
 * Created by GaoxinHuang on 2016/12/8.
 */

public class GetBooleanController  extends AsyncTask<String, Integer, Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        return ServerConnection.deleteImageFromServer(params[0],params[1],params[2]);
    }

    @Override
    protected void onPostExecute(Boolean bitmap) {
        super.onPostExecute(bitmap);
        onResponse(bitmap);
    }

    public void onResponse(Boolean bitmap) {
    }
}
