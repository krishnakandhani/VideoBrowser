package com.viewpagermvvm.utils;

import android.content.Context;

import com.viewpagermvvm.data.VideoDatabase;
import com.viewpagermvvm.data.repository.VideoRepository;
import com.viewpagermvvm.viewmodels.VideoListViewModelFactory;

public class InjectorUtils {
    public static VideoListViewModelFactory provideVideoViewModelFactory(Context mContext) {
        VideoRepository mRepository = getVideoRepository(mContext);
        return new VideoListViewModelFactory(mRepository);
    }

    private static VideoRepository getVideoRepository(Context mContext) {
        return VideoRepository.getInstance(VideoDatabase.getInstance(mContext).videoDao());
    }
}
