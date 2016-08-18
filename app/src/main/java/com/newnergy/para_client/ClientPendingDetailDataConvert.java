package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GaoxinHuang on 2016/8/9.
 */
public class ClientPendingDetailDataConvert {
    public static ClientPendingDetailViewModel convertJsonToModel(String json) {

        try {
//            JSONArray parentArray = new JSONArray(json);

            JSONObject object = new JSONObject(json);
            ClientPendingDetailViewModel jsvm = new ClientPendingDetailViewModel();
            jsvm.setProviderId(object.getInt("ProviderId"));
            jsvm.setProviderProfilePhoto(object.getString("ProviderProfilePhoto"));
            jsvm.setBudget(object.getDouble("Budget"));
            jsvm.setTitle(object.getString("Title"));
            jsvm.setStatus(object.getInt("Status"));
            jsvm.setProviderPhone(object.getString("ProviderPhone"));
            jsvm.setServiceId(object.getInt("ServiceId"));
            jsvm.setServiceStreet(object.getString("ServiceStreet"));
            jsvm.setServiceSuburb(object.getString("ServiceSuburb"));
            jsvm.setServiceCity(object.getString("ServiceCity"));
            jsvm.setProviderStreet(object.getString("ProviderStreet"));
            jsvm.setProviderSuburb(object.getString("ProviderSuburb"));
            jsvm.setProviderCity(object.getString("ProviderCity"));
            jsvm.setServiceAddressId(object.getInt("ServiceAddressId"));
            jsvm.setProviderAddressId(object.getInt("ProviderAddressId"));
            jsvm.setDescription(object.getString("Description"));
            jsvm.setType(object.getString("Type"));
            jsvm.setBudget(object.getDouble("Budget"));
            jsvm.setProviderUsername(object.getString("ProviderUsername"));
            jsvm.setCreateDate(object.getString("CreateDate"));
            jsvm.setProviderFirstname(object.getString("ProviderFirstname"));
            jsvm.setProviderLastname(object.getString("ProviderLastname"));
            jsvm.setRating(object.getDouble("Rating"));
            String[] servicePhotoUrls;
            JSONArray photoUrls = new JSONArray(object.getString("ServicePhotoUrl"));
            servicePhotoUrls = new String[photoUrls.length()];
            for (int j = 0; j < photoUrls.length(); j++) {
                servicePhotoUrls[j] = (String) photoUrls.get(j);
            }
            jsvm.setServicePhotoUrl(servicePhotoUrls);

            return jsvm;
        } catch (JSONException e) {
            System.out.println("not array");
        }
        return null;
    }
}
