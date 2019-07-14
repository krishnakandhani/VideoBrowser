package com.viewpagermvvm.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.viewpagermvvm.data.daos.VideoDao;
import com.viewpagermvvm.data.entities.VideoItem;

@Database(entities = {VideoItem.class}, version = 1, exportSchema = false)
public abstract class VideoDatabase extends RoomDatabase {
    private static VideoDatabase instance = null;

    public abstract VideoDao videoDao();

    public static VideoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static VideoDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context, VideoDatabase.class, DbConstants.DB_NAME)
                .allowMainThreadQueries()
                .build();
    }
}
