package com.exam.picsum.network;

import android.util.Log;

import com.exam.picsum.model.PicSumModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PicSumEndpoint {

    public interface PicListener{
        void failed();
        void success(ArrayList<PicSumModel> body);
    }

    public static final String BASE_URL= "https://picsum.photos";

    PicSumInterface mApiInterface;
    PicListener listener;

    public PicSumEndpoint(PicListener listener){
        this.listener = listener;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiInterface = retrofit.create(PicSumInterface.class);
    }

    public void getList(int page_number){

        mApiInterface.fetchList(page_number)
                .enqueue(new Callback<ArrayList<PicSumModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PicSumModel>> call, Response<ArrayList<PicSumModel>> response) {
                        if(response.isSuccessful()){
                            listener.success(response.body());

                            Log.e("response", response.body().toString());
//                            call.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PicSumModel>> call, Throwable t) {
                        listener.failed();
                        Log.e("response", t.toString());
//                        call.cancel();
                    }
                });


    }

}
