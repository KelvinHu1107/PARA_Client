package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/10.
 */
public class ClientUpdateServiceViewModel {
    private Integer ServiceId;
    private Double Budget;
    private String Title;
    private Integer Status;
    private String Type;
    private String Description;
    private String DueDate;
    private Double Price, deposit;
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

    public boolean setIsSecure(boolean price) {
        IsSecure = price;
        return IsSecure;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double price) {
        deposit = price;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
