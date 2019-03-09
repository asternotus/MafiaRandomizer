package com.aleisterfly.mafiarandomsitting.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.aleisterfly.mafiarandomsitting.models.Player;

@Database(entities = {Player.class}, version = 1)
public abstract class PlayerDB extends RoomDatabase {
    public abstract PlayerDao playerDao();
}
