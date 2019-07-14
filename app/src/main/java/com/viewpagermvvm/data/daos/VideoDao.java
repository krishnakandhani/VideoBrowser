package com.viewpagermvvm.data.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.viewpagermvvm.data.entities.VideoItem;

import java.util.List;

@Dao
public interface VideoDao {

    @Query("SELECT * FROM videos")
    public List<VideoItem> getAllVideos();

    @Query("SELECT * FROM videos WHERE serverId = :serverId LIMIT 1")
    public VideoItem getVideo(int serverId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAllVideo(List<VideoItem> videos);

    @Insert
    public void insertVideo(VideoItem videoItem);
}
