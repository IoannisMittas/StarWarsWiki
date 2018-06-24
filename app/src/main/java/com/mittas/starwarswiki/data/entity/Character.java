package com.mittas.starwarswiki.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class Character {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @ColumnInfo(name = "birth_year")
    private String birthYear;

    private String homeworld;

    @Ignore
    private List<String> filmsSwapiUrls;

    @Ignore
    private List<String> vehiclesSwapiUrls;

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

    public List<String> getFilmsSwapiUrls() {
        return filmsSwapiUrls;
    }

    public List<String> getVehiclesSwapiUrls() {
        return vehiclesSwapiUrls;
    }
}
