package com.newnergy.para_client;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GaoxinHuang on 2016/6/24.
 */
public class ClientProfileDataConvert {
//    public static List<ClientProfileViewModel> convertJsonToArrayList(String json) {
//        List<ClientProfileViewModel> result = new ArrayList<ClientProfileViewModel>();
//        try {
//            JSONArray parentArray = new JSONArray(json);
//            for (int i = 0; i < parentArray.length(); i++) {
//                JSONObject object = parentArray.getJSONObject(i);
//                ClientProfileViewModel jsvm = new ClientProfileViewModel();
//                jsvm.setId(object.getInt("Id"));
//                jsvm.setUsername(object.getString("Username"));
//                jsvm.setFirstName(object.getString("FirstName"));
//                jsvm.setLastName(object.getString("LastName"));
//                jsvm.setCompanyName(object.getString("CompanyName"));
//                jsvm.setCompanyAddressCity(object.getString("CompanyAddressCity"));
//                jsvm.setCompanyPhone(object.getString("CompanyPhone"));
//                jsvm.setProviderAddressCity(object.getString("ProviderAddressCity"));
//                jsvm.setProviderAddressStreet(object.getString("ProviderAddressStreet"));
//                jsvm.setProviderAddressSuburb(object.getString("ProviderAddressSuburb"));
//                jsvm.setCellPhone(object.getString("CellPhone"));
//                jsvm.setHomePhone(object.getString("HomePhone"));
//                jsvm.setCompanyAddressStreet(object.getString("CompanyAddressStreet"));
//                jsvm.setCompanyAddressSuburb(object.getString("CompanyAddressSuburb"));
//                jsvm.setProfilePicture(object.getString("ProfilePicture"));
//                jsvm.setPassword(object.getString("Password"));
//                jsvm.setIntroduction(object.getString("Introduction"));
//                jsvm.setLicenceNum(object.getString("LicenceNum"));
//                jsvm.setProviderAddressId(object.getInt("ProviderAddressId"));
//                jsvm.setCompanyAddressId(object.getInt("CompanyAddressId"));
//                jsvm.setProviderAddressId(object.getInt("ProviderAddressId"));
//                jsvm.setCompanyAddressId(object.getInt("CompanyAddressId"));
//    jsvm.setLicenceNum(object.getString("LicenceNum"));
//    jsvm.setLicenceId(object.getInt("LicenceId"));
//                result.add(jsvm);
//            }
//        } catch (JSONException e) {
//            System.out.println("not array");
//        }
//        return result;
//    }

    public static ClientProfileViewModel convertJsonToModel(String json) {
        try {
//            JSONArray parentArray = new JSONArray(json);
            JSONObject object = new JSONObject(json);
            ClientProfileViewModel jsvm = new ClientProfileViewModel();
            jsvm.setId(object.getInt("Id"));
            jsvm.setUsername(object.getString("Username"));
            jsvm.setFirstName(object.getString("FirstName"));
            jsvm.setLastName(object.getString("LastName"));
            jsvm.setClientAddressCity(object.getString("ClientAddressCity"));
            jsvm.setClientAddressStreet(object.getString("ProviderAddressStreet"));
            jsvm.setClientAddressSuburb(object.getString("ProviderAddressSuburb"));
            jsvm.setCellPhone(object.getString("CellPhone"));
            jsvm.setProfilePicture(object.getString("ProfilePicture"));
            //jsvm.setPassword(object.getString("Password"));
            jsvm.setClientAddressId(object.getInt("ProviderAddressId"));
            jsvm.setClientAddressId(object.getInt("CompanyAddressId"));

            return jsvm;
        } catch (JSONException e) {
            System.out.println("not array");
        }
        return null;
    }

    public static String ModelToJson(ClientProfileViewModel model) {
        StringBuilder json = new StringBuilder("");
        json.append("{");
        if (model.getUsername() != null) {
            json.append("\"Username\":\"" + model.getUsername() + "\",");
        } else {
            System.out.println("not username");
            return "{}";
        }
//        if (model.getPassword() != null) {
//            json.append("\"Password\":\"" + model.getPassword() + "\",");
//        }
        if (model.getFirstName() != null) {
            json.append("\"FirstName\":\"" + model.getFirstName() + "\",");
        }
        if (model.getLastName() != null) {
            json.append("\"LastName\":\"" + model.getLastName() + "\",");
        }
        if (model.getCellPhone() != null) {
            json.append("\"CellPhone\":\"" + model.getCellPhone() + "\",");
        }

        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}
