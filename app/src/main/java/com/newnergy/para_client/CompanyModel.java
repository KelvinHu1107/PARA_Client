package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/6/24.
 */
public class CompanyModel {
    private Integer Id ;
    private String LandLine ;
    private String CellPhone ;
    private Integer AddressId ;
    private String Website ;
    private String GST_Number ;
    private String Name ;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getLandLine() {
        return LandLine;
    }

    public void setLandLine(String landLine) {
        LandLine = landLine;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

    public Integer getAddressId() {
        return AddressId;
    }

    public void setAddressId(Integer addressId) {
        AddressId = addressId;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getGST_Number() {
        return GST_Number;
    }

    public void setGST_Number(String GST_Number) {
        this.GST_Number = GST_Number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
