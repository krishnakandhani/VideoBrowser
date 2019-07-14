package com.viewpagermvvm.utils;

import android.app.Application;
import androidx.room.Room;

import com.viewpagermvvm.data.DbConstants;
import com.viewpagermvvm.data.VideoDatabase;

public class AppController extends Application {
//    private VideoDatabase mVideoDatabase;
    private static AppController mAppController = null;

    @Override
    public void onCreate() {
        super.onCreate();
//        mVideoDatabase = Room.databaseBuilder(getApplicationContext(), VideoDatabase.class, DbConstants.DB_NAME).build();
        mAppController = this;
    }

    public static AppController getAppController() {
        return mAppController;
    }

//    public VideoDatabase getVideoDatabase() {
//        return mVideoDatabase;
//    }


}
