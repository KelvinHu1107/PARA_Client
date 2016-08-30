package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/29.
 */
public class ChatGetMessageViewModel {
    private String FromUsername ;
    private String FromUserFirstname ;
    private String FromUserLastname ;
    private String FromUserProfilePhoto ;
    private String MessageContent ;
    private String CreateDate ;

    public String getFromUsername() {
        return FromUsername;
    }

    public void setFromUsername(String fromUsername) {
        FromUsername = fromUsername;
    }

    public String getFromUserFirstname() {
        return FromUserFirstname;
    }

    public void setFromUserFirstname(String fromUserFirstname) {
        FromUserFirstname = fromUserFirstname;
    }

    public String getFromUserLastname() {
        return FromUserLastname;
    }

    public void setFromUserLastname(String fromUserLastname) {
        FromUserLastname = fromUserLastname;
    }

    public String getFromUserProfilePhoto() {
        return FromUserProfilePhoto;
    }

    public void setFromUserProfilePhoto(String fromUserProfilePhoto) {
        FromUserProfilePhoto = fromUserProfilePhoto;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }
}
