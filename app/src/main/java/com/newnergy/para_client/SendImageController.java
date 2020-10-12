package com.newnergy.para_client;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 *
 */
public class SendImageController extends AsyncTask<String, Integer, Boolean> {
    private Bitmap bitmap;

    @Override
    protected Boolean doInBackground(String... params) {
        return ServerConnection.sendImageToServer(params[0],bitmap,"POST");
    }

    @Override
    protected void onPostExecute(Boolean bitmap) {
        super.onPostExecute(bitmap);
        onResponse(bitmap);
    }

    public void onResponse(Boolean bitmap) {
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}