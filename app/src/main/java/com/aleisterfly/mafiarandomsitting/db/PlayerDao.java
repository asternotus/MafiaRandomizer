package com.aleisterfly.mafiarandomsitting.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.aleisterfly.mafiarandomsitting.models.Player;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface PlayerDao {

    @Query("SELECT * FROM player")
    List<Player> getAll();

    @Query("SELECT * FROM player WHERE id = :id")
    Player getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Player player);

    @Update
    void update(Player player);

    @Delete
    void delete(Player player);
}
