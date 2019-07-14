package com.viewpagermvvm.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.viewpagermvvm.data.repository.VideoRepository;

public class VideoListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private VideoRepository mRepository;

    public VideoListViewModelFactory(VideoRepository mRepository) {
        this.mRepository = mRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new VideoListViewModel(mRepository);
    }
}
