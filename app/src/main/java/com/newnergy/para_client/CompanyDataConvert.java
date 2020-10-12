package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/6/24.
 */
public class CompanyDataConvert {
    public static String ModelToJson(CompanyModel model){
        StringBuilder json=new StringBuilder("");
        json.append("{");
        if(model.getId()!=null){
            json.append("\"Id\":\""+model.getId()+"\",");
        }else{
            System.out.println("not username");
            return "{}";
        }
        if(model.getCellPhone()!=null){
            json.append("\"CellPhone\":\""+model.getCellPhone()+"\",");
        }
        if(model.getName()!=null){
            json.append("\"Name\":\""+model.getName()+"\",");
        }
        json.deleteCharAt(json.length()-1);
        json.append("}");
        return json.toString();
    }
}
