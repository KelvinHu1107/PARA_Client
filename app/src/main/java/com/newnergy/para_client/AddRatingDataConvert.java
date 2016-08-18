package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/18.
 */
public class AddRatingDataConvert {
    public String ModelToJson(AddRatingViewModel model){
        StringBuilder json=new StringBuilder("");
        json.append("{");
        if(model.getProviderUsername()!=null){
            json.append("\"ProviderUsername\":\""+model.getProviderUsername()+"\",");
        }else{

        }
        if(model.getClientUsername()!=null){
            json.append("\"ClientUsername\":\""+model.getClientUsername()+"\",");
        }
        if(model.getRating()!=null){
            json.append("\"Rating\":\""+model.getRating()+"\",");
        }
        if(model.getComment()!=null){
            json.append("\"Comment\":\""+model.getComment()+"\",");
        }

        json.deleteCharAt(json.length()-1);
        json.append("}");
        return json.toString();
    }
}
