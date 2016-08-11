package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GaoxinHuang on 2016/8/9.
 */
public class ClientPendingDetailDataConvert {
    public static ClientPendingDetailViewModel convertJsonToModel(String json) {
        ClientPendingDetailViewModel jsvm = new ClientPendingDetailViewModel();
        try {
//            JSONArray parentArray = new JSONArray(json);

            JSONObject object = new JSONObject(json);


            jsvm.setProviderProfilePhoto(object.getString("ProviderProfilePhoto"));
            jsvm.setBudget(object.getDouble("Budget"));
            jsvm.setTitle(object.getString("Title"));
            jsvm.setStatus(object.getInt("Status"));


            jsvm.setServiceStreet(object.getString("ServiceStreet"));
            jsvm.setServiceSuburb(object.getString("ServiceSuburb"));
            jsvm.setServiceCity(object.getString("ServiceCity"));

            jsvm.setServiceAddressId(object.getInt("ServiceAddressId"));

            jsvm.setDescription(object.getString("Description"));
            jsvm.setType(object.getString("Type"));
            jsvm.setBudget(object.getDouble("Budget"));
            jsvm.setProviderId(object.getInt("ProviderId"));
            jsvm.setProviderAddressId(object.getInt("ProviderAddressId"));
            jsvm.setProviderUsername(object.getString("ProviderUsername"));
            jsvm.setProviderStreet(object.getString("ProviderStreet"));
            jsvm.setProviderSuburb(object.getString("ProviderSuburb"));
            jsvm.setProviderCity(object.getString("ProviderCity"));
            jsvm.setProviderPhone(object.getString("ProviderPhone"));
            String[] servicePhotoUrls;
            JSONArray photoUrls = new JSONArray(object.getString("ServicePhotoUrl"));
            servicePhotoUrls = new String[photoUrls.length()];
            for (int j = 0; j < photoUrls.length(); j++) {
                servicePhotoUrls[j] = (String) photoUrls.get(j);
            }
            jsvm.setServicePhotoUrl(servicePhotoUrls);

            return jsvm;
        } catch (JSONException e) {

        }
        return jsvm;
    }
}
