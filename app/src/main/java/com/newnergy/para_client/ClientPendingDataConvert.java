package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaoxinHuang on 2016/8/9.
 */
public class ClientPendingDataConvert {
    public List<ClientPendingListViewModel> convertJsonToArrayList(String json) {
        List<ClientPendingListViewModel> result = new ArrayList<ClientPendingListViewModel>();
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject object = parentArray.getJSONObject(i);
                ClientPendingListViewModel jsvm = new ClientPendingListViewModel();
                jsvm.setServiceId(object.getInt("ServiceId"));
                jsvm.setProviderId(object.getInt("ProviderId"));
                jsvm.setStatus(object.getInt("Status"));
                jsvm.setTitle(object.getString("Title"));
                jsvm.setBudget(object.getDouble("Budget"));
                jsvm.setProviderProfilePhoto(object.getString("ProviderProfilePhoto"));
                jsvm.setCreateDate(object.getString("CreatedDate"));
                jsvm.setCompleteDate(object.getString("CompleteDate"));

                result.add(jsvm);
            }
        } catch (JSONException e) {

        }
        return result;
    }
}
