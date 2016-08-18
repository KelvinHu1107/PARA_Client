package com.newnergy.para_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaoxinHuang on 2016/8/18.
 */
public class RatingListDataConvert {
    public List<RatingListViewModel> convertJsonToArrayList(String json) {
        List<RatingListViewModel> result = new ArrayList<RatingListViewModel>();
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject object = parentArray.getJSONObject(i);
                RatingListViewModel jsvm = new RatingListViewModel();
                jsvm.setRatingId(object.getInt("RatingId"));
                jsvm.setClientFirstname(object.getString("ClientFirstname"));
                jsvm.setClientLastname(object.getString("ClientLastname"));
                jsvm.setCreateDate(object.getString("CreateDate"));
                jsvm.setComment(object.getString("Comment"));
                jsvm.setRating(object.getDouble("Rating"));

                result.add(jsvm);
            }
        } catch (JSONException e) {
            System.out.println("fuckfuck");
        }
        return result;
    }

}
