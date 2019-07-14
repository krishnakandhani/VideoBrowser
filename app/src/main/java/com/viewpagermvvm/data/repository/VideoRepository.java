package com.viewpagermvvm.data.repository;

import com.viewpagermvvm.data.daos.VideoDao;
import com.viewpagermvvm.data.entities.VideoItem;


import java.util.List;

public class VideoRepository {
    private VideoDao mVideoDao;
    private static VideoRepository instance;

    public VideoRepository(VideoDao mVideoDao) {
        this.mVideoDao = mVideoDao;
    }

    public static VideoRepository getInstance(VideoDao mVideoDao) {
        if (instance == null) {
            instance = new VideoRepository(mVideoDao);
        }
        return instance;
    }

    public List<VideoItem> getVideos() {
        return mVideoDao.getAllVideos();
    }

    public void insertVideos(List<VideoItem> videoItems) {
        mVideoDao.insertAllVideo(videoItems);
    }

    public VideoItem getVideo(int serverId) {
        return mVideoDao.getVideo(serverId);
    }

    public void insertVideo(VideoItem videoItem) {
        mVideoDao.insertVideo(videoItem);
    }

    public VideoItem getVideoItem(int serverId) {
        return mVideoDao.getVideo(serverId);
    }
}
