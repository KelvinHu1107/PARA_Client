package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/3.
 */
public class PlaceOrderServiceViewModel {
    private String Description ;
    private String Title;
    private String ClientEmail;
    private String Street ;
    private String Suburb ;
    private String City ;
    private Double Budget ;
    private String Type;
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

    public boolean setIsSecure(boolean description) {
        IsSecure = description;

        return IsSecure;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getClientEmail() {
        return ClientEmail;
    }

    public void setClientEmail(String clientEmail) {
        ClientEmail = clientEmail;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getSuburb() {
        return Suburb;
    }

    public void setSuburb(String suburb) {
        Suburb = suburb;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Double getBudget() {
        return Budget;
    }

    public void setBudget(Double budget) {
        Budget = budget;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
