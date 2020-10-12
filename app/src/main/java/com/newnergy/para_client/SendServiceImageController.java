package com.newnergy.para_client;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by GaoxinHuang on 2016/12/6.
 */

public class SendServiceImageController extends AsyncTask<String, Integer, String> {
    private Bitmap bitmap;

    @Override
    protected String doInBackground(String... params) {
        return ServerConnection.sendServiceImageToServer(params[0],bitmap,"POST");
    }

    @Override
    protected void onPostExecute(String bitmap) {
        super.onPostExecute(bitmap);
        onResponse(bitmap);
    }

    public void onResponse(String bitmap) {
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
