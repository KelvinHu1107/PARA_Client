package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/7/13.
 */

public class ClientProfileViewModel {
    private int Id;
    private String Username;
   // private String Password;
    private String ClientAddressCity;
    private String ClientAddressStreet;
    private String ClientAddressSuburb;
    private Integer ClientAddressId;
    private String ProfilePicture;
    private String FirstName;
    private String LastName;
    private String CellPhone;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

//    public String getPassword() {
//        return Password;
//    }
//
//    public void setPassword(String password) {
//        Password = password;
//    }

    public String getClientAddressCity() {
        return ClientAddressCity;
    }

    public void setClientAddressCity(String clientAddressCity) {
        ClientAddressCity = clientAddressCity;
    }

    public String getClientAddressStreet() {
        return ClientAddressStreet;
    }

    public void setClientAddressStreet(String clientAddressStreet) {
        ClientAddressStreet = clientAddressStreet;
    }

    public String getClientAddressSuburb() {
        return ClientAddressSuburb;
    }

    public void setClientAddressSuburb(String clientAddressSuburb) {
        ClientAddressSuburb = clientAddressSuburb;
    }

    public Integer getClientAddressId() {
        return ClientAddressId;
    }

    public void setClientAddressId(Integer clientAddressId) {
        ClientAddressId = clientAddressId;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }
}
