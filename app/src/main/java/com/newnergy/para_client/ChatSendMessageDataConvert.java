package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/29.
 */
public class ChatSendMessageDataConvert {
    public String ModelToJson(ChatSendMessageViewModel model) {
        StringBuilder json = new StringBuilder("");
        json.append("{");
        if (model.getFromUsername() != null) {
            json.append("\"FromUsername\":\"" + model.getFromUsername() + "\",");
        }
        if (model.getMessageContent() != null) {
            json.append("\"MessageContent\":\"" + model.getMessageContent() + "\",");
        }
        if (model.getContentType() != null) {
            json.append("\"ContentType\":\"" + model.getContentType() + "\",");
        }
        if (model.getToUsername() != null) {
            json.append("\"ToUsername\":\"" + model.getToUsername() + "\",");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}
