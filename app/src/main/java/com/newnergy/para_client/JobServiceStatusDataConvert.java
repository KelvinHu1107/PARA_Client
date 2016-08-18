package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/8.
 */
public class JobServiceStatusDataConvert {

    public String ModelToJson(JobServiceStatusViewModel model) {
        StringBuilder json = new StringBuilder("");
        json.append("{");
        if (model.getStatus() != null) {
            json.append("\"Status\":\"" + model.getStatus() + "\",");
        } else {

        }
        if (model.getProviderUsername() != null) {
            json.append("\"ProviderUsername\":\"" + model.getProviderUsername() + "\",");
        }
        if (model.getPrice() != null) {
            json.append("\"Price\":\"" + model.getPrice() + "\",");
        }

        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}
