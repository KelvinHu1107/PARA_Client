package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaoxinHuang on 2016/6/21.
 */
public class PendingJobDataConvert {
    public static List<PendingJobViewModel> convertJsonToArrayList(String json) {
        List<PendingJobViewModel> result = new ArrayList<PendingJobViewModel>();
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject object = parentArray.getJSONObject(i);
                PendingJobViewModel jsvm = new PendingJobViewModel();
                jsvm.setClientId(object.getInt("ClientId"));
                jsvm.setClientName(object.getString("ClientName"));
                jsvm.setStartDate(object.getString("StartDate"));
                jsvm.setId(object.getInt("Id"));
                jsvm.setDueDate(object.getString("DueDate"));
                jsvm.setTitle(object.getString("Title"));
                jsvm.setProfilePhoto(object.getString("ProfilePhoto"));
                jsvm.setDescription(object.getString("Description"));
                jsvm.setAddressDetail(object.getString("AddressDetail"));
                jsvm.setStatus(object.getInt("Status"));
                jsvm.setCellPhone(object.getString("CellPhone"));
                jsvm.setHomePhone(object.getString("HomePhone"));
                jsvm.setClientEmail(object.getString("ClientEmail"));
                String[] servicePhotoUrls;
                JSONArray photoUrls = new JSONArray(object.getString("ServicePhotoUrl"));
                servicePhotoUrls = new String[photoUrls.length()];
                for (int j = 0; j < photoUrls.length(); j++) {
                    servicePhotoUrls[j] = (String) photoUrls.get(j);
                }
                jsvm.setServicePhotoUrl(servicePhotoUrls);
                result.add(jsvm);
            }
        } catch (JSONException e) {
            System.out.println("not array");
        }
        return result;
    }

    public static PendingJobViewModel convertJsonToModel(String json) {
        try {
//            JSONArray parentArray = new JSONArray(json);

            JSONObject object = new JSONObject(json);
            PendingJobViewModel jsvm = new PendingJobViewModel();
            jsvm.setClientId(object.getInt("ClientId"));
            jsvm.setClientName(object.getString("ClientName"));

            jsvm.setStartDate(object.getString("StartDate"));

            jsvm.setId(object.getInt("Id"));
            jsvm.setDueDate(object.getString("DueDate"));
            jsvm.setTitle(object.getString("Title"));
            jsvm.setProfilePhoto(object.getString("ProfilePhoto"));
            jsvm.setDescription(object.getString("Description"));
            jsvm.setAddressDetail(object.getString("AddressDetail"));
            jsvm.setStatus(object.getInt("Status"));
            jsvm.setCellPhone(object.getString("CellPhone"));
            jsvm.setHomePhone(object.getString("HomePhone"));
            jsvm.setClientEmail(object.getString("ClientEmail"));
            String[] servicePhotoUrls;
            JSONArray photoUrls = new JSONArray(object.getString("ServicePhotoUrl"));
            servicePhotoUrls = new String[photoUrls.length()];
            for (int j = 0; j < photoUrls.length(); j++) {
                servicePhotoUrls[j] = (String) photoUrls.get(j);
            }
            jsvm.setServicePhotoUrl(servicePhotoUrls);
//                result.add(jsvm);
            return jsvm;
        } catch (JSONException e) {
            System.out.println("not array");
        }
        return null;
    }
}
