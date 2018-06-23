package com.mittas.starwarswiki.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Character {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String birthYear;

    private String homeworld;

    public Character(String name, String birthYear, String homeworld) {
        this.name = name;
        this.birthYear = birthYear;
        this.homeworld = homeworld;
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

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }
}
