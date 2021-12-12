package com.adndiginet.terbb.Api;

import com.adndiginet.terbb.ApiModel.Review.ReviewResponses;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("applicationStatus")
    Call<ReviewResponses> postByReview(
            @Header("Authorization") String token
    );
}
