package com.viewpagermvvm.networks;

import com.google.gson.JsonElement;

import io.reactivex.Observable;

public class ApiCallBack {
    private ApiService apiCallInterface;

    public ApiCallBack() {
        this.apiCallInterface = APIClient.getApiService();
    }

    /*
     * method to call login api
     * */
    public Observable<JsonElement> executeVideos() {
        return apiCallInterface.doGetVideos();
    }
}
