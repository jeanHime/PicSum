package com.exam.picsum.network;

import com.exam.picsum.model.PicSumModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PicSumInterface {

    @Headers("Cache-Control: max-age=640000")
    @GET("/v2/list")
    Call<ArrayList<PicSumModel>> fetchList(@Query("page") int pageNumber);

}
