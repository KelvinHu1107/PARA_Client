package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/29.
 */
public class ChatSendMessageViewModel {
    private String FromUsername ;
    private String ToUsername ;
    private String MessageContent ;
    private String ContentType ;

    public String getFromUsername() {
        return FromUsername;
    }

    public void setFromUsername(String fromUsername) {
        FromUsername = fromUsername;
    }

    public String getToUsername() {
        return ToUsername;
    }

    public void setToUsername(String toUsername) {
        ToUsername = toUsername;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }
}
