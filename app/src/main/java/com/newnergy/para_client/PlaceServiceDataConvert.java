package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/3.
 */
public class PlaceServiceDataConvert {
    public String convertModelToJson(PlaceOrderServiceViewModel model) {
        StringBuilder json = new StringBuilder("");
        json.append("{");
        if (model.getClientEmail() != null) {
            json.append("\"ClientEmail\":\"" + model.getClientEmail() + "\",");
        } else {
            System.out.println("not username");
            return "{}";
        }
        if (model.getTitle() != null) {
            json.append("\"Title\":\"" + model.getTitle() + "\",");
        }
        if (model.getDescription() != null) {
            json.append("\"Description\":\"" + model.getDescription() + "\",");
        }
        if (model.getStreet() != null) {
            json.append("\"Street\":\"" + model.getStreet() + "\",");
        }
        if (model.getSuburb() != null) {
            json.append("\"Suburb\":\"" + model.getSuburb() + "\",");
        }
        if (model.getCity() != null) {
            json.append("\"City\":\"" + model.getCity() + "\",");
        }
        if (model.getBudget() != null) {
            json.append("\"Budget\":\"" + model.getBudget() + "\",");
        }
        if (model.getType() != null) {
            json.append("\"Type\":\"" + model.getType() + "\",");
        }
            json.append("\"IsSecure\":\"" + model.getIsSecure() + "\",");

        if (model.getDueDate() != null) {
            json.append("\"DueDate\":\"" + model.getDueDate() + "\",");
        }


        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}
