package com.newnergy.para_client;

import android.os.AsyncTask;

/**
 * Created by GaoxinHuang on 2016/9/21.
 */
public class LoginController extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... params) {

        return  ServerConnection.GetTokenFromServer(params[0],params[1],params[2]);
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        onResponse(result);
    }

    public void onResponse(String s){
    }
}
