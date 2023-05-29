package com.if4a.projectpab.API;

import com.if4a.projectpab.Model.ModelResponses;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelResponses> ardRetrieve();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponses> ardCreate(
            @Field("nama") String nama,
            @Field("rasa") String rasa,
            @Field("rating") String rating,
            @Field("harga") String harga,
            @Field("deskripsi_singkat") String deskripsi_singkat
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ModelResponses> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("rasa") String rasa,
            @Field("rating") String rating,
            @Field("harga") String harga,
            @Field("deskripsi_singkat") String deskripsi_singkat
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelResponses> ardDelete(
            @Field("id") String id
    );
}

