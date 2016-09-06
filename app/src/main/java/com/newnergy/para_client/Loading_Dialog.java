package com.newnergy.para_client;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by G7 on 6/09/2016.
 */
public class Loading_Dialog {

    //public Context context;
    private ProgressDialog  progress ;//= new ProgressDialog(context,R.style.MyTheme);
    public void getContext(Context c)
    {
        this.progress = new ProgressDialog(c,R.style.MyTheme);
        this.progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.setCanceledOnTouchOutside(false);
    }


    public void ShowLoadingDialog()
    {
        progress.show();

    }
    public void CloseLoadingDialog()
    {
        progress.dismiss();
    }

}
