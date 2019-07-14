package com.viewpagermvvm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viewpagermvvm.R;
import com.viewpagermvvm.data.entities.VideoItem;
import com.viewpagermvvm.databinding.ContentVideoItemBinding;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    private List<VideoItem> mVideoItems;
    private OnVideoItemClickListener mVideoItemClickListenr;
    private int mSelectedIndex = -1;
    private Context mContext;

    public VideoAdapter(List<VideoItem> mVideoItems, OnVideoItemClickListener mVideoItemClickListenr) {
        this.mVideoItems = mVideoItems;
        this.mVideoItemClickListenr = mVideoItemClickListenr;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ContentVideoItemBinding itemBinding = ContentVideoItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new VideoHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        VideoItem videoItem = mVideoItems.get(position);
        holder.apply(mSelectedIndex, holder.itemView, holder.getAdapterPosition(), videoItem, mVideoItemClickListenr);
    }

    @Override
    public int getItemCount() {
        return mVideoItems == null ? 0 : mVideoItems.size();
    }

    public void setVideoItems(List<VideoItem> videoItems) {
        this.mVideoItems = videoItems;
        notifyDataSetChanged();
    }

    public List<VideoItem> getVideoItems() {
        return mVideoItems;
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private ContentVideoItemBinding mBinding;

        public VideoHolder(@NonNull ContentVideoItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }

        public void apply(int mSelectedIndex, final View itemView, final int adapterPosition, final VideoItem videoItem, final OnVideoItemClickListener mVideoItemClickListenr) {
            if (mSelectedIndex == adapterPosition) {
                mBinding.frameLayout.setForeground(mContext.getResources().getDrawable(R.drawable.bg_selected));
            } else {
                mBinding.frameLayout.setForeground(new ColorDrawable(Color.TRANSPARENT));
            }
            mBinding.setModel(videoItem);
            mBinding.setPosition(adapterPosition);
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVideoItemClickListenr.onVideoClick(itemView, adapterPosition, videoItem);
                }
            });
            mBinding.executePendingBindings();
        }
    }

    public interface OnVideoItemClickListener {
        public void onVideoClick(View mView, int position, VideoItem videoItem);
    }
    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public void setSelectedIndex(int mSelectedIndex) {
        this.mSelectedIndex = mSelectedIndex;
        notifyDataSetChanged();
    }
}
