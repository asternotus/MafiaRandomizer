package com.aleisterfly.mafiarandomsitting.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.aleisterfly.mafiarandomsitting.Roles;

@Entity
public class Player {

    @PrimaryKey
    private int id;

    private String name = "Ник";

    private int role = Roles.CITIZEN.ordinal();

    public Player(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
