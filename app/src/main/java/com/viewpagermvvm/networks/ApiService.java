package com.viewpagermvvm.networks;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET(ApiConstants.Urls.pictures)
    Observable<JsonElement> doGetVideos();
}
