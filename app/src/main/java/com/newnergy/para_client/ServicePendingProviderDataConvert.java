package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaoxinHuang on 2016/8/12.
 */
public class ServicePendingProviderDataConvert {
    public List<ServicePendingProviderViewModel> convertJsonToArrayList(String json) {
        List<ServicePendingProviderViewModel> result = new ArrayList<ServicePendingProviderViewModel>();
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject object = parentArray.getJSONObject(i);
                ServicePendingProviderViewModel jsvm = new ServicePendingProviderViewModel();
                jsvm.setServiceId(object.getInt("ServiceId"));
                jsvm.setProviderId(object.getInt("ProviderId"));
                jsvm.setStatus(object.getInt("Status"));
                jsvm.setProviderUsername(object.getString("ProviderUsername"));
                jsvm.setAcceptTime(object.getString("AcceptTime"));
                jsvm.setPrice(object.getDouble("Price"));
                jsvm.setProviderProfilePhoto(object.getString("ProviderProfilePhoto"));
                jsvm.setProviderRating(object.getDouble("ProviderRating"));
                jsvm.setProviderDeposit(object.getDouble("ProviderDeposit"));
                jsvm.setProviderFirstname(object.getString("ProviderFirstname"));
                jsvm.setProivderLastname(object.getString("ProivderLastname"));
                result.add(jsvm);
            }
        } catch (JSONException e) {
            System.out.println("ffffff");
        }
        return result;
    }
}
