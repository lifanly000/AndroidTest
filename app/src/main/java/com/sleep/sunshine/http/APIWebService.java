package com.sleep.sunshine.http;

import android.content.Context;

import com.sleep.sunshine.http.model.Author;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lifan on 2017/9/5.
 */

public class APIWebService {

    public static final String API_URL = "https://zhuanlan.zhihu.com";

    private ServiceAPI excuteApi;

    private static  APIWebService instance;

    private Context mContext;

    public static APIWebService getInstance(Context context){
        if(instance ==null){
            instance = new APIWebService(context.getApplicationContext());
        }
        return instance;
    }

    public ServiceAPI getExcuteApi(){
        return excuteApi;
    }

    private APIWebService(Context context) {
        mContext = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        excuteApi = retrofit.create(ServiceAPI.class);
    }


}
