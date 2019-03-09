package com.aleisterfly.mafiarandomsitting;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.aleisterfly.mafiarandomsitting.db.PlayerDB;

public class MafiaApp extends Application {

    public static MafiaApp instance;

    private PlayerDB playerDB;

    public static final int PLAYERS_NUMBER = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        playerDB = Room.databaseBuilder(this, PlayerDB.class, "playerdb")
                .allowMainThreadQueries()
                .build();
    }

    public static MafiaApp getInstance(){
        return instance;
    }

    public PlayerDB getPlayerDB(){
        return playerDB;
    }
}
