package com.newnergy.para_client;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GaoxinHuang on 2016/9/22.
 */
public class RefreshTokenDataConvert {
    public RefreshTokenViewModel convertJsonToModel(String json) {
        try {
            JSONObject allObject = new JSONObject(json);
            RefreshTokenViewModel jsvm = new RefreshTokenViewModel();
            jsvm.setSuccess(allObject.getBoolean("success"));
            JSONObject object = new JSONObject(allObject.getString("data"));
            if (jsvm.isSuccess()) {
                jsvm.setExpireInSecond(object.getLong("expires_in"));
                jsvm.setAccessToken(object.getString("access_token"));
                jsvm.setRefreshToken(object.getString("refresh_token"));
            }
            return jsvm;
        } catch (JSONException e) {
            System.out.println("convert fail");
        }
        return new RefreshTokenViewModel();
    }
    public String ModelToJson(RefreshTokenViewModel model) {
        StringBuilder json = new StringBuilder("");
        json.append("{");

        if (model.getUsername() != null) {
            json.append("\"Username\":\"" + model.getUsername() + "\",");
        }
        if (model.getRefreshToken() != null) {
            json.append("\"refreshToken\":\"" + model.getRefreshToken() + "\",");
        }

        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}