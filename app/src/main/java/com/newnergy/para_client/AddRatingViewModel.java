package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/18.
 */
public class AddRatingViewModel {
    private String ProviderUsername ;
    private String ClientUsername ;
    private Double Rating ;
    private String Comment ;

    public String getProviderUsername() {
        return ProviderUsername;
    }

    public void setProviderUsername(String providerUsername) {
        ProviderUsername = providerUsername;
    }

    public String getClientUsername() {
        return ClientUsername;
    }

    public void setClientUsername(String clientUsername) {
        ClientUsername = clientUsername;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
