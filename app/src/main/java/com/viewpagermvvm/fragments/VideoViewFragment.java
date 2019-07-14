package com.viewpagermvvm.fragments;


import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.viewpagermvvm.R;
import com.viewpagermvvm.data.entities.VideoItem;
import com.viewpagermvvm.databinding.FragmentVideoViewBinding;
import com.viewpagermvvm.utils.Utils;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoViewFragment extends Fragment {


    private static final String TAG = VideoViewFragment.class.getSimpleName();

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
        FragmentVideoViewBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_video_view,container,false);

        //here data must be an instance of the class MarsDataProvider
        Bundle bundle = getArguments();
        VideoItem model = (VideoItem) bundle.getSerializable("Video");
        binding.setModel(model);
//        binding.txtTitle.setText(model.ge);
        binding.bigVideoView.setLooping(true);
        binding.bigVideoView.setAutoplay(true);
        binding.frameLayout.setLayoutParams(new LinearLayout.LayoutParams(Utils.getDisplayWidth(getActivity()), Utils.getDisplayWidth(getActivity())));
        Log.e(TAG, "onViewCreated: " + model.getId());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
