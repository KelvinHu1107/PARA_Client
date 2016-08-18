package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/8/18.
 */
public class RatingListViewModel {

    private String CreateDate ;
    private Integer RatingId ;
    private String ClientFirstname ;
    private String ClientLastname ;
    private Double Rating ;
    private String Comment ;

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getRatingId() {
        return RatingId;
    }

    public void setRatingId(int ratingId) {
        RatingId = ratingId;
    }

    public String getClientFirstname() {
        return ClientFirstname;
    }

    public void setClientFirstname(String clientFirstname) {
        ClientFirstname = clientFirstname;
    }

    public String getClientLastname() {
        return ClientLastname;
    }

    public void setClientLastname(String clientLastname) {
        ClientLastname = clientLastname;
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
