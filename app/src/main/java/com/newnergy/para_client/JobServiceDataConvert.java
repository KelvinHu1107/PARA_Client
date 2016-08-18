package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by GaoxinHuang on 2016/6/20.
 */
public class JobServiceDataConvert {
    public static List<JobServiceViewModel> convertJsonToArrayList(String json) {
        List<JobServiceViewModel> result = new ArrayList<JobServiceViewModel>();
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject object = parentArray.getJSONObject(i);
                JobServiceViewModel jsvm = new JobServiceViewModel();
                jsvm.setClientId(object.getInt("ClientId"));
                jsvm.setClientName(object.getString("ClientName"));
                jsvm.setCreatedDate(object.getString("CreatedDate"));
                jsvm.setId(object.getInt("Id"));
                jsvm.setDueDate(object.getString("DueDate"));
                jsvm.setTitle(object.getString("Title"));
                jsvm.setProfilePhoto(object.getString("ProfilePhoto"));
                jsvm.setJobSuburb(object.getString("JobSuburb"));
                jsvm.setDescription(object.getString("Description"));
                jsvm.setAddressDetail(object.getString("AddressDetail"));
                jsvm.setStatus(object.getInt("Status"));
                jsvm.setCellPhone(object.getString("CellPhone"));
                jsvm.setHomePhone(object.getString("HomePhone"));
                jsvm.setClientEmail(object.getString("ClientEmail"));
                jsvm.setType(object.getString("Type"));
                jsvm.setBudget(object.getDouble("Budget"));
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

    public static JobServiceViewModel convertJsonToModel(String json) {

        try {
//            JSONArray parentArray = new JSONArray(json);

            JSONObject object = new JSONObject(json);
            JobServiceViewModel jsvm = new JobServiceViewModel();
            jsvm.setClientId(object.getInt("ClientId"));
            jsvm.setClientName(object.getString("ClientName"));
            jsvm.setCreatedDate(object.getString("CreatedDate"));
            jsvm.setId(object.getInt("Id"));
            jsvm.setDueDate(object.getString("DueDate"));
            jsvm.setTitle(object.getString("Title"));
            jsvm.setProfilePhoto(object.getString("ProfilePhoto"));
            jsvm.setJobSuburb(object.getString("JobSuburb"));
            jsvm.setDescription(object.getString("Description"));
            jsvm.setAddressDetail(object.getString("AddressDetail"));
            jsvm.setStatus(object.getInt("Status"));
            jsvm.setCellPhone(object.getString("CellPhone"));
            jsvm.setHomePhone(object.getString("HomePhone"));
            jsvm.setClientEmail(object.getString("ClientEmail"));
            jsvm.setType(object.getString("Type"));
            jsvm.setBudget(object.getDouble("Budget"));

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
