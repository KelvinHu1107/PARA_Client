package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/6/21.
 */
public class PendingJobViewModel {
    private int ClientId ;
    private String DueDate ;
    private String StartDate ;
    private String ClientName ;
    private String Title ;
    private String ProfilePhoto ;
    private int Id ;
    private String Description ;
    private int Status ;
    private String[] ServicePhotoUrl ;
    private String AddressDetail ;
    private String CellPhone ;
    private String HomePhone ;
    private String ClientEmail;

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String[] getServicePhotoUrl() {
        return ServicePhotoUrl;
    }

    public void setServicePhotoUrl(String[] servicePhotoUrl) {
        ServicePhotoUrl = servicePhotoUrl;
    }

    public String getAddressDetail() {
        return AddressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        AddressDetail = addressDetail;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

    public String getHomePhone() {
        return HomePhone;
    }

    public void setHomePhone(String homePhone) {
        HomePhone = homePhone;
    }

    public String getClientEmail() {
        return ClientEmail;
    }

    public void setClientEmail(String clientEmail) {
        ClientEmail = clientEmail;
    }
}
