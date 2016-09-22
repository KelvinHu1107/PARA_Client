package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GaoxinHuang on 2016/9/19.
 */
public class UpdateProviderSkillDataConvert {
    public String ModelToJson(UpdateProviderSkillViewModel model) {
        StringBuilder json = new StringBuilder("");
        json.append("{");

        if (model.getUsername() != null) {
            json.append("\"Username\":\"" + model.getUsername() + "\",");
        }

        String[] skillsList = model.getSkillName();
        if (skillsList.length > 0) {
            json.append("\"SkillName\":[");
            for (String skill : skillsList) {
                json.append("\"" + model.getUsername() + "\",");
            }
            json.deleteCharAt(json.length() - 1);
            json.append("],");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }

    public UpdateProviderSkillViewModel convertJsonToModel(String json) {
        try {
            JSONObject object = new JSONObject(json);
            UpdateProviderSkillViewModel jsvm = new UpdateProviderSkillViewModel();
            String[] SkillList;
            JSONArray photoUrls = new JSONArray(object.getString("SkillName"));
            SkillList = new String[photoUrls.length()];
            for (int j = 0; j < photoUrls.length(); j++) {
                SkillList[j] = (String) photoUrls.get(j);
            }
            jsvm.setSkillName(SkillList);
            return jsvm;
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("not array");
        }

        return null;
    }

}
