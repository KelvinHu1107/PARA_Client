package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/10.
 */
public class ClientUpdateServiceDataConvert {
    public String ModelToJson(ClientUpdateServiceViewModel model) {
        StringBuilder json = new StringBuilder("");
        json.append("{");
        if (model.getServiceId() != null) {
            json.append("\"ServiceId\":\"" + model.getServiceId() + "\",");
        } else {
        }
        if (model.getTitle() != null) {
            json.append("\"Title\":\"" + model.getTitle() + "\",");
        }
        if (model.getDescription() != null) {
            json.append("\"Description\":\"" + model.getDescription() + "\",");
        }
        if (model.getStatus() != null) {
            json.append("\"Status\":\"" + model.getStatus() + "\",");
        }
        if (model.getBudget() != null) {
            json.append("\"Budget\":\"" + model.getBudget() + "\",");
        }
        if (model.getType() != null) {
            json.append("\"Type\":\"" + model.getType() + "\",");
        }
        if (model.getPrice() != null) {
            json.append("\"Price\":\"" + model.getPrice() + "\",");
        }

            json.append("\"IsSecure\":\"" + model.getIsSecure() + "\",");

        if (model.getDeposit() != null) {
            json.append("\"ClientExtraDeposit\":\"" + model.getDeposit() + "\",");
        }

        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}
