package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/8.
 */
public class JobServiceStatusViewModel {
    private String ProviderUsername ;
    private Integer Status ;
    private int ServiceId ;

    public String getProviderUsername() {
        return ProviderUsername;
    }

    public void setProviderUsername(String providerUsername) {
        ProviderUsername = providerUsername;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }
}
