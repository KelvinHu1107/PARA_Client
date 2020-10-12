package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaoxinHuang on 2016/7/13.
 */

public class ClientDataConvert {
    public static List<ClientProfileViewModel> convertJsonToArrayList(String json) {
        List<ClientProfileViewModel> result = new ArrayList<ClientProfileViewModel>();
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject object = new JSONObject(json);
                ClientProfileViewModel jsvm = new ClientProfileViewModel();
                jsvm.setId(object.getInt("Id"));
                jsvm.setClientAddressId(object.getInt("ClientAddressId"));
                jsvm.setCellPhone(object.getString("CellPhone"));
                jsvm.setUsername(object.getString("Username"));
                //jsvm.setPassword(object.getString("Password"));
                jsvm.setClientAddressCity(object.getString("ClientAddressCity"));
                jsvm.setClientAddressStreet(object.getString("ClientAddressStreet"));
                jsvm.setClientAddressSuburb(object.getString("ClientAddressSuburb"));
                jsvm.setProfilePicture(object.getString("ProfilePicture"));
                jsvm.setFirstName(object.getString("FirstName"));
                jsvm.setFirstName(object.getString("LastName"));
                result.add(jsvm);
            }
        } catch (JSONException e) {
            System.out.println("not array??????????");
        }
        return result;
    }


    public static ClientProfileViewModel convertJsonToModel(String json) {
        try {
//            JSONArray parentArray = new JSONArray(json);
            JSONObject object = new JSONObject(json);
            ClientProfileViewModel jsvm = new ClientProfileViewModel();

            jsvm.setId(object.getInt("Id"));

            jsvm.setClientAddressId(object.getInt("ClientAddressId"));

            jsvm.setCellPhone(object.getString("CellPhone"));

            jsvm.setUsername(object.getString("Username"));

            //jsvm.setPassword(object.getString("Password"));

            jsvm.setClientAddressCity(object.getString("ClientAddressCity"));

            jsvm.setClientAddressStreet(object.getString("ClientAddressStreet"));

            jsvm.setClientAddressSuburb(object.getString("ClientAddressSuburb"));

            jsvm.setProfilePicture(object.getString("ProfilePicture"));

            jsvm.setFirstName(object.getString("FirstName"));

            jsvm.setLastName(object.getString("LastName"));

            return jsvm;
        } catch (JSONException e) {
            System.out.println("not arrayfffffffffffffff");
        }
        return null;
    }
}
