package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/12.
 */
public class ServicePendingProviderViewModel {
    private Integer ProviderId ;
    private String ProviderUsername ;
    private String AcceptTime ;
    private Integer ServiceId ;
    private Integer Status ;
    private String ProviderProfilePhoto ;
    private Double ProviderRating ;
    private Double  Price ;
    private Double ProviderDeposit;
    private String ProviderFirstname;
    private String ProivderLastname;

    public Double getProviderDeposit() {
        return ProviderDeposit;
    }

    public void setProviderDeposit(Double providerDeposit) {
        ProviderDeposit = providerDeposit;
    }

    public String getProviderFirstname() {
        return ProviderFirstname;
    }

    public void setProviderFirstname(String providerFirstname) {
        ProviderFirstname = providerFirstname;
    }

    public String getProivderLastname() {
        return ProivderLastname;
    }

    public void setProivderLastname(String proivderLastname) {
        ProivderLastname = proivderLastname;
    }

    public Integer getProviderId() {
        return ProviderId;
    }

    public void setProviderId(Integer providerId) {
        ProviderId = providerId;
    }

    public String getProviderUsername() {
        return ProviderUsername;
    }

    public void setProviderUsername(String providerUsername) {
        ProviderUsername = providerUsername;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }

    public Integer getServiceId() {
        return ServiceId;
    }

    public void setServiceId(Integer serviceId) {
        ServiceId = serviceId;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getProviderProfilePhoto() {
        return ProviderProfilePhoto;
    }

    public void setProviderProfilePhoto(String providerProfilePhoto) {
        ProviderProfilePhoto = providerProfilePhoto;
    }

    public Double getProviderRating() {
        return ProviderRating;
    }

    public void setProviderRating(Double providerRating) {
        ProviderRating = providerRating;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
