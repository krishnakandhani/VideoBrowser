package com.viewpagermvvm.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.viewpagermvvm.R;
import com.viewpagermvvm.adapters.PagerAdapter;
import com.viewpagermvvm.adapters.VideoAdapter;
import com.viewpagermvvm.data.entities.VideoItem;
import com.viewpagermvvm.databinding.ActivityVideoListBinding;
import com.viewpagermvvm.fragments.VideoViewFragment;
import com.viewpagermvvm.models.VideoResponseModel;
import com.viewpagermvvm.networks.ApiResponse;
import com.viewpagermvvm.utils.InjectorUtils;
import com.viewpagermvvm.viewmodels.VideoListViewModel;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.layoutmanager.gallery.CenterLayoutManager;
import github.hellocsl.layoutmanager.gallery.ScaleTransformer;

public class VideoListActivity extends AppCompatActivity implements VideoAdapter.OnVideoItemClickListener, Observer<ApiResponse> {

    private ActivityVideoListBinding mBinding;
    private VideoListViewModel mVideoListViewModel;
    private VideoAdapter mVideoAdapter;
    private PagerAdapter mPagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBinding();
        setUpAdapter();
        setupViewPager();
        subscribeUi(mVideoAdapter, mBinding);
    }

    private void setupViewPager() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
    }

    private void subscribeUi(final VideoAdapter mVideoAdapter, ActivityVideoListBinding mBinding) {
        mVideoListViewModel.loading.set(View.VISIBLE);
        mVideoListViewModel.mVideoItems.observe(this, new Observer<List<VideoItem>>() {
            @Override
            public void onChanged(List<VideoItem> videoItems) {
                mVideoListViewModel.loading.set(View.GONE);
                if (videoItems.size() == 0) {
                    mVideoListViewModel.showEmpty.set(View.VISIBLE);
                } else {
                    mVideoListViewModel.showEmpty.set(View.GONE);
                    mVideoAdapter.setVideoItems(videoItems);
                    mVideoAdapter.notifyDataSetChanged();
                    for (VideoItem model : videoItems) {
                        fragments.add(VideoViewFragment.getInstance(model));
                    }
                    initViewPager(fragments);
                }
            }
        });
    }

    private void setUpAdapter() {
        mVideoAdapter = new VideoAdapter(null, this);
        CenterLayoutManager manager = new CenterLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        manager.setItemTransformer(new ScaleTransformer());
        manager.attach(mBinding.recyclerViewData);
        manager.setOnItemSelectedListener(new CenterLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, final int position) {
                Log.e("onItemSelected: ", item.toString() + "pos: " + position);
                mBinding.viewpager.setCurrentItem(position,true);

            }
        });
        mBinding.recyclerViewData.setLayoutManager(manager);
        mBinding.recyclerViewData.setAdapter(mVideoAdapter);
    }

    private void setupBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_list);
        mVideoListViewModel = ViewModelProviders.of(this, InjectorUtils.provideVideoViewModelFactory(this)).get(VideoListViewModel.class);
        mBinding.setModel(mVideoListViewModel);
        mVideoListViewModel.fetchList();
        mVideoListViewModel.videoResponse().observe(this, this);
    }

    private void initViewPager(ArrayList<Fragment> fragment) {
        mPagerAdapter.addFragments(fragment);
        mBinding.viewpager.setOffscreenPageLimit(1);
        mBinding.viewpager.setAdapter(mPagerAdapter);
        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
//                mBinding.viewpager.setCurrentItem(position);
//                mVideoAdapter.setSelectedIndex(position);
                mBinding.recyclerViewData.smoothScrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onVideoClick(View mView, int position, VideoItem videoItem) {
        mBinding.viewpager.setCurrentItem(position);
        mVideoAdapter.setSelectedIndex(position);
        mBinding.recyclerViewData.smoothScrollToPosition(position);
    }

    @Override
    public void onChanged(ApiResponse apiResponse) {
        switch (apiResponse.status) {

            case LOADING:
                mVideoListViewModel.loading.set(View.VISIBLE);
                break;

            case SUCCESS:
                mVideoListViewModel.loading.set(View.GONE);
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                mVideoListViewModel.loading.set(View.GONE);
                break;

            default:
                break;
        }

    }

    private void renderSuccessResponse(JsonElement body) {
        JsonArray dataArray = body.getAsJsonArray();
        ArrayList<VideoItem> videoItems = new ArrayList<>();
        for (int i = 0; i < dataArray.size(); i++) {
            JsonElement element = dataArray.get(i);
            VideoResponseModel videoResponseModel = new Gson().fromJson(element, VideoResponseModel.class);
            VideoItem videoItem = new VideoItem();
            videoItem.setServerId(videoResponseModel.getId());
            videoItem.setVideoUrl(videoResponseModel.getVideoUrl());
            videoItem.setImageUrl(videoResponseModel.getImageUrl());
            videoItems.add(videoItem);
            if (mVideoListViewModel.getVideoItem(videoItem.getServerId()) == null) {
                mVideoListViewModel.insertVideo(videoItem);
            }

        }
        mVideoListViewModel.setVideoItems(mVideoListViewModel.getVideos());
    }
}
