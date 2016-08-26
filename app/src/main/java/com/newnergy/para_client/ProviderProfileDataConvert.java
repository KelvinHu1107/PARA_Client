package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaoxinHuang on 2016/6/24.
 */
public class ProviderProfileDataConvert {
    public static List<ProviderProfileViewModel> convertJsonToArrayList(String json) {
        List<ProviderProfileViewModel> result = new ArrayList<ProviderProfileViewModel>();
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject object = parentArray.getJSONObject(i);
                ProviderProfileViewModel jsvm = new ProviderProfileViewModel();
                jsvm.setId(object.getInt("Id"));
                jsvm.setUsername(object.getString("Username"));
                jsvm.setFirstName(object.getString("FirstName"));
                jsvm.setLastName(object.getString("LastName"));
                jsvm.setCompanyName(object.getString("CompanyName"));
                jsvm.setCompanyAddressCity(object.getString("CompanyAddressCity"));
                jsvm.setCompanyPhone(object.getString("CompanyPhone"));
                jsvm.setProviderAddressCity(object.getString("ProviderAddressCity"));
                jsvm.setProviderAddressStreet(object.getString("ProviderAddressStreet"));
                jsvm.setProviderAddressSuburb(object.getString("ProviderAddressSuburb"));
                jsvm.setCellPhone(object.getString("CellPhone"));
                jsvm.setHomePhone(object.getString("HomePhone"));
                jsvm.setCompanyAddressStreet(object.getString("CompanyAddressStreet"));
                jsvm.setCompanyAddressSuburb(object.getString("CompanyAddressSuburb"));
                jsvm.setProfilePicture(object.getString("ProfilePicture"));
                jsvm.setPassword(object.getString("Password"));
                jsvm.setIntroduction(object.getString("Introduction"));
				jsvm.setRating(object.getDouble("Rating"));
//                jsvm.setLicenceNum(object.getString("LicenceNum"));
//                jsvm.setProviderAddressId(object.getInt("ProviderAddressId"));
//                jsvm.setCompanyAddressId(object.getInt("CompanyAddressId"));
  //              jsvm.setProviderAddressId(object.getInt("ProviderAddressId"));
   //             jsvm.setCompanyAddressId(object.getInt("CompanyAddressId"));
    //             jsvm.setLicenceNum(object.getString("LicenceNum"));
//                 jsvm.setLicenceId(object.getInt("LicenceId"));
                result.add(jsvm);
            }
        } catch (JSONException e) {
            System.out.println("not array");
            e.printStackTrace();
        }
        return result;
    }

    public static ProviderProfileViewModel convertJsonToModel(String json) {
        try {
//            JSONArray parentArray = new JSONArray(json);
            JSONObject object = new JSONObject(json);
            ProviderProfileViewModel jsvm = new ProviderProfileViewModel();
            jsvm.setId(object.getInt("Id"));
            jsvm.setCompanyId(object.getInt("CompanyId"));
            jsvm.setUsername(object.getString("Username"));
            jsvm.setFirstName(object.getString("FirstName"));
            jsvm.setLastName(object.getString("LastName"));
            jsvm.setCompanyName(object.getString("CompanyName"));
            jsvm.setCompanyAddressCity(object.getString("CompanyAddressCity"));
            jsvm.setCompanyPhone(object.getString("CompanyPhone"));
            jsvm.setProviderAddressCity(object.getString("ProviderAddressCity"));
            jsvm.setProviderAddressStreet(object.getString("ProviderAddressStreet"));
            jsvm.setProviderAddressSuburb(object.getString("ProviderAddressSuburb"));
            jsvm.setCellPhone(object.getString("CellPhone"));
            jsvm.setHomePhone(object.getString("HomePhone"));
            jsvm.setCompanyAddressStreet(object.getString("CompanyAddressStreet"));
            jsvm.setCompanyAddressSuburb(object.getString("CompanyAddressSuburb"));
            jsvm.setProfilePicture(object.getString("ProfilePicture"));
            jsvm.setPassword(object.getString("Password"));
            jsvm.setProviderAddressId(object.getInt("ProviderAddressId"));
            jsvm.setCompanyAddressId(object.getInt("CompanyAddressId"));
            jsvm.setIntroduction(object.getString("Introduction"));
            jsvm.setLicenceNum(object.getString("LicenceNum"));
            jsvm.setLicenceId(object.getInt("LicenceId"));
            return jsvm;
        } catch (JSONException e) {
            System.out.println("not array");
        }
        return null;
    }

    public static String ModelToJson(ProviderProfileViewModel model) {
        StringBuilder json = new StringBuilder("");
        json.append("{");
        if (model.getUsername() != null) {
            json.append("\"Username\":\"" + model.getUsername() + "\",");
        } else {
            System.out.println("not username");
            return "{}";
        }
        if (model.getPassword() != null) {
            json.append("\"Password\":\"" + model.getPassword() + "\",");
        }
        if (model.getFirstName() != null) {
            json.append("\"FirstName\":\"" + model.getFirstName() + "\",");
        }
        if (model.getLastName() != null) {
            json.append("\"LastName\":\"" + model.getLastName() + "\",");
        }
        if (model.getCellPhone() != null) {
            json.append("\"CellPhone\":\"" + model.getCellPhone() + "\",");
        }
        if (model.getHomePhone() != null) {
            json.append("\"HomePhone\":\"" + model.getHomePhone() + "\",");
        }
        if (model.getIntroduction() != null) {
            json.append("\"Introduction\":\"" + model.getIntroduction() + "\",");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}
