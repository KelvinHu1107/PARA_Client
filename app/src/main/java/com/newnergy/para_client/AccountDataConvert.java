package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GaoxinHuang on 2016/12/9.
 */

public class AccountDataConvert {
    public static AccountViewModel convertJsonToModel(String json) {
        try {
//            JSONArray parentArray = new JSONArray(json);

            JSONObject dataObject = new JSONObject(json);
            AccountViewModel jsvm = new AccountViewModel();
            jsvm.setSuccess(dataObject.getBoolean("success"));
            if (jsvm.getSuccess()) {
                String data = dataObject.getString("data");
                JSONObject object = new JSONObject(data);
                jsvm.setExpiresIn(object.getLong("expires_in"));
                jsvm.setAccessToken(object.getString("access_token"));
                jsvm.setRefreshToken(object.getString("refresh_token"));
            }

            return jsvm;
        } catch (JSONException e) {
            System.out.println("not array");
        }
        return null;
    }
}
