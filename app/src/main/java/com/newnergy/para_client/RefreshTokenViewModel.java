package com.newnergy.para_client;

/**
 * Created by GaoxinHuang on 2016/9/22.
 */
public class RefreshTokenViewModel {
    private String Username;
    private long ExpireInSecond;
    private String AccessToken;
    private String RefreshToken;
    private boolean isSuccess;
    private String ErrorMessage;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public long getExpireInSecond() {
        return ExpireInSecond;
    }

    public void setExpireInSecond(long expireInSecond) {
        ExpireInSecond = expireInSecond;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }
}