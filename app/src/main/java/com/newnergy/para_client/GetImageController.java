package com.newnergy.para_client;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by GaoxinHuang on 2016/6/15.
 */
public class GetImageController extends AsyncTask<String, Integer, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... params) {
        return ServerConnection.getImageFromServer(params[0],params[1],params[2]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        onResponse(bitmap);
    }

    public void onResponse(Bitmap bitmap) {
    }


}
