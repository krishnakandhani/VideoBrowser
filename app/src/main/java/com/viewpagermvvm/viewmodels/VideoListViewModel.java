package com.viewpagermvvm.viewmodels;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.viewpagermvvm.data.entities.VideoItem;
import com.viewpagermvvm.data.repository.VideoRepository;
import com.viewpagermvvm.networks.ApiCallBack;
import com.viewpagermvvm.networks.ApiResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VideoListViewModel extends ViewModel {
    public MutableLiveData<List<VideoItem>> mVideoItems;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private ApiCallBack mApiCallBack;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private VideoRepository mVideoRepository;

    public VideoListViewModel(VideoRepository mVideoRepository) {
        mApiCallBack = new ApiCallBack();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        this.mVideoRepository = mVideoRepository;
        mVideoItems=new MutableLiveData<>();
    }

    public void fetchList() {
        disposables.add(mApiCallBack.executeVideos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable d) throws Exception {
                        responseLiveData.setValue(ApiResponse.loading());
                    }
                })
                .subscribe(new Consumer<JsonElement>() {
                               @Override
                               public void accept(JsonElement result) throws Exception {
                                   responseLiveData.setValue(ApiResponse.success(result));
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                responseLiveData.setValue(ApiResponse.error(throwable));
                            }
                        }));
    }

    public MutableLiveData<ApiResponse> videoResponse() {
        return responseLiveData;
    }

    public void insertVideos(List<VideoItem> videoItems) {
        mVideoRepository.insertVideos(videoItems);
    }

    public void insertVideo(VideoItem videoItem) {
        mVideoRepository.insertVideo(videoItem);
    }

    public void setVideoItems(List<VideoItem> videoItems){
        mVideoItems.setValue(videoItems);
    }
    public VideoItem getVideoItem(int serverId) {
        return mVideoRepository.getVideoItem(serverId);
    }
    public List<VideoItem> getVideos() {
        return mVideoRepository.getVideos();
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageViewDrawable(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }
}
