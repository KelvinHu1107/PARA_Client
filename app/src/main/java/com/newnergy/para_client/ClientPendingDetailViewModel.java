package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/9.
 */
public class ClientPendingDetailViewModel {

    private Integer ProviderId;
    private Integer ServiceId;
    private String ProviderProfilePhoto;
    private Double Budget;
    private String Title;
    private Integer Status;
    private String ProviderPhone;
    private String ServiceStreet;
    private String ServiceSuburb;
    private String ServiceCity;
    private String ProviderStreet;
    private String ProviderSuburb;
    private String ProviderCity;
    private Integer ServiceAddressId;
    private Integer ProviderAddressId;
    private String Description;
    private String Type;
    private String[] ServicePhotoUrl;
    private String ProviderUsername;
    private String CreateDate;
    private Double deposit;
    private Double Rating;
    private String ProviderFirstname;
    private String ProviderLastname;
    private String DueDate;
    private boolean IsSecure;

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String text) {
        DueDate = text;
    }

    public boolean getIsSecure() {
        return IsSecure;
    }

    public boolean setIsSecure(boolean rating) {
        IsSecure = rating;
        return IsSecure;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double rating) {
        deposit = rating;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }

    public String getProviderFirstname() {
        return ProviderFirstname;
    }

    public void setProviderFirstname(String providerFirstname) {
        ProviderFirstname = providerFirstname;
    }

    public String getProviderLastname() {
        return ProviderLastname;
    }

    public void setProviderLastname(String providerLastname) {
        ProviderLastname = providerLastname;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getProviderUsername() {
        return ProviderUsername;
    }

    public void setProviderUsername(String providerUsername) {
        ProviderUsername = providerUsername;
    }

    public Integer getProviderId() {
        return ProviderId;
    }

    public void setProviderId(Integer providerId) {
        ProviderId = providerId;
    }

    public Integer getServiceId() {
        return ServiceId;
    }

    public void setServiceId(Integer serviceId) {
        ServiceId = serviceId;
    }

    public String getProviderProfilePhoto() {
        return ProviderProfilePhoto;
    }

    public void setProviderProfilePhoto(String providerProfilePhoto) {
        ProviderProfilePhoto = providerProfilePhoto;
    }

    public Double getBudget() {
        return Budget;
    }

    public void setBudget(Double budget) {
        Budget = budget;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getProviderPhone() {
        return ProviderPhone;
    }

    public void setProviderPhone(String providerPhone) {
        ProviderPhone = providerPhone;
    }

    public String getServiceStreet() {
        return ServiceStreet;
    }

    public void setServiceStreet(String serviceStreet) {
        ServiceStreet = serviceStreet;
    }

    public String getServiceSuburb() {
        return ServiceSuburb;
    }

    public void setServiceSuburb(String serviceSuburb) {
        ServiceSuburb = serviceSuburb;
    }

    public String getServiceCity() {
        return ServiceCity;
    }

    public void setServiceCity(String serviceCity) {
        ServiceCity = serviceCity;
    }

    public String getProviderStreet() {
        return ProviderStreet;
    }

    public void setProviderStreet(String providerStreet) {
        ProviderStreet = providerStreet;
    }

    public String getProviderSuburb() {
        return ProviderSuburb;
    }

    public void setProviderSuburb(String providerSuburb) {
        ProviderSuburb = providerSuburb;
    }

    public String getProviderCity() {
        return ProviderCity;
    }

    public void setProviderCity(String providerCity) {
        ProviderCity = providerCity;
    }

    public Integer getServiceAddressId() {
        return ServiceAddressId;
    }

    public void setServiceAddressId(Integer serviceAddressId) {
        ServiceAddressId = serviceAddressId;
    }

    public Integer getProviderAddressId() {
        return ProviderAddressId;
    }

    public void setProviderAddressId(Integer providerAddressId) {
        ProviderAddressId = providerAddressId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String[] getServicePhotoUrl() {
        return ServicePhotoUrl;
    }

    public void setServicePhotoUrl(String[] servicePhotoUrl) {
        ServicePhotoUrl = servicePhotoUrl;
    }
}
