package com.mittas.starwarswiki.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class Planet {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public Planet() {
    }

    public Planet(String name) {
        this.name = name;
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
}
