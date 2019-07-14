package com.viewpagermvvm.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.viewpagermvvm.R;
import com.viewpagermvvm.data.entities.VideoItem;
import com.viewpagermvvm.databinding.FragmentVideoViewBinding;
import com.viewpagermvvm.utils.AppController;
import com.viewpagermvvm.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoViewFragment extends Fragment implements CacheListener {


    private static final String TAG = VideoViewFragment.class.getSimpleName();
    private String url;
    private FragmentVideoViewBinding binding;

    public static VideoViewFragment getInstance(VideoItem model) {
        // Required empty public constructor
        Bundle bundle = new Bundle();
        bundle.putSerializable("Video", model);
        VideoViewFragment mViewFragment = new VideoViewFragment();
        mViewFragment.setArguments(bundle);
        return mViewFragment;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_view, container, false);

        //here data must be an instance of the class MarsDataProvider
        Bundle bundle = getArguments();
        VideoItem model = (VideoItem) bundle.getSerializable("Video");
        binding.setModel(model);
//        binding.txtTitle.setText(model.ge);
        binding.bigVideoView.setLooping(true);
        binding.bigVideoView.setAutoplay(true);
        binding.frameLayout.setLayoutParams(new LinearLayout.LayoutParams(Utils.getDisplayWidth(getActivity()), Utils.getDisplayWidth(getActivity())));
        url = model.getVideoUrl();
        Log.e(TAG, "onViewCreated: " + model.getId());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkCachedState();
        startVideo();
    }

    private void checkCachedState() {
        HttpProxyCacheServer proxy = AppController.getProxy(getActivity());
        boolean fullyCached = proxy.isCached(url);
//        setCachedState(fullyCached);

    }

    private void startVideo() {
        HttpProxyCacheServer proxy = AppController.getProxy(getActivity());
        proxy.registerCacheListener(this, url);
        String proxyUrl = proxy.getProxyUrl(url);
        binding.bigVideoView.setVideoUrl(proxyUrl);
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
//        setCachedState(percentsAvailable == 100);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        AppController.getProxy(getActivity()).unregisterCacheListener(this);
    }
}
