package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/6/24.
 */
public class AddressDataConvert {
    public static String ModelToJson(AddressModel model){
        StringBuilder json=new StringBuilder("");
        json.append("{");
        if(model.getId()!=null){
            json.append("\"Id\":\""+model.getId()+"\",");
        }else{
            System.out.println("not username");
            return "{}";
        }
        if(model.getStreet()!=null){
            json.append("\"Street\":\""+model.getStreet()+"\",");
        }
        if(model.getSuburb()!=null){
            json.append("\"Suburb\":\""+model.getSuburb()+"\",");
        }
        if(model.getCity()!=null){
            json.append("\"City\":\""+model.getCity()+"\",");
        }

        json.deleteCharAt(json.length()-1);
        json.append("}");
        return json.toString();
    }
}
