package com.newnergy.para_client;

import static java.lang.System.currentTimeMillis;

/**
 * Created by GaoxinHuang on 2016/11/17.
 */

public class RefreshTokenController {
    public void refreshToken(String username, String refreshToken){
        LoginController c = new LoginController() {
            @Override
            public void onResponse(String s) {
                boolean result=false;
                RefreshTokenDataConvert convert=new RefreshTokenDataConvert();
                RefreshTokenViewModel newRefreshModel=convert.convertJsonToModel(s);
                if(newRefreshModel.getRefreshToken()!=null){
                    //save ...token, refresh token, update expired time

                    ValueMessager.accessToken = newRefreshModel.getAccessToken();
                    ValueMessager.tokenDueTime = newRefreshModel.getExpireInSecond() + currentTimeMillis();
                    ValueMessager.refreshToken = newRefreshModel.getRefreshToken();

                    result=true;
                }
                response(result);
            }
        };
        RefreshTokenViewModel model=new RefreshTokenViewModel();
        model.setUsername(username);
        model.setRefreshToken(refreshToken);
        RefreshTokenDataConvert convert=new RefreshTokenDataConvert();
        String data=convert.ModelToJson(model);
        c.execute("http://para.co.nz/api/clientaccount/RefreshToken",data,"POST");
    }

    public void response(boolean result) {

    }
}
