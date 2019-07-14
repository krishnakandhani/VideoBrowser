package com.viewpagermvvm.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.viewpagermvvm.BR;

import java.io.Serializable;

@Entity
public class VideoResponseModel extends BaseObservable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    private static final String TAG = VideoResponseModel.class.getSimpleName();

    @ColumnInfo(name = "videoUrl")
    private String videoUrl;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @ColumnInfo(name = "serverId")
    private int id;

    @Bindable
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        notifyPropertyChanged(BR.videoUrl);

    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Override
    public String toString() {
        return "ClassPojo [videoUrl = " + videoUrl + ", imageUrl = " + imageUrl + ", id = " + id + "]";
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
