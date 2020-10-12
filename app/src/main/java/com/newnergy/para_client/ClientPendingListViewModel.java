package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/9.
 */
public class ClientPendingListViewModel {
    private String ProviderProfilePhoto ;
    private Integer ServiceId ;
    private Double Budget ;
    private String Title ;
    private Integer Status ;
    private Integer ProviderId ;
    private String CreateDate;
    private String CompleteDate;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String addressText) {
        address = addressText;
    }

    public String getCompleteDate() {
        return CompleteDate;
    }

    public void setCompleteDate(String completeDate) {
        CompleteDate = completeDate;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getProviderProfilePhoto() {
        return ProviderProfilePhoto;
    }

    public void setProviderProfilePhoto(String providerProfilePhoto) {
        ProviderProfilePhoto = providerProfilePhoto;
    }

    public Integer getServiceId() {
        return ServiceId;
    }

    public void setServiceId(Integer serviceId) {
        ServiceId = serviceId;
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

    public Integer getProviderId() {
        return ProviderId;
    }

    public void setProviderId(Integer providerId) {
        ProviderId = providerId;
    }
}
