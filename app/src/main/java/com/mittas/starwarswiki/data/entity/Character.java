package com.mittas.starwarswiki.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class Character {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @SerializedName("birth_year")
    private String birthYear;

    private String homeworldName;

    // isFavourite = 1 for true, isFavourite = 0 for false
    private int isFavourite;

    @Ignore
    @SerializedName("homeworld")
    private String homeworldUrl;

    @Ignore
    @SerializedName("films")
    private List<String> filmsUrls;

    @Ignore
    @SerializedName("vehicles")
    private List<String> vehiclesUrls;


    public Character() {}

    public Character(String name, String birthYear, String homeworldName) {
        this.name = name;
        this.birthYear = birthYear;
        this.homeworldName = homeworldName;
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

    public String getHomeworldName() {
        return homeworldName;
    }

    public void setHomeworldName(String homeworldName) {
        this.homeworldName = homeworldName;
    }

    public String getHomeworldUrl() {
        return homeworldUrl;
    }

    public void setHomeworldUrl(String homeworldUrl) {
        this.homeworldUrl = homeworldUrl;
    }

    public List<String> getFilmsUrls() {
        return filmsUrls;
    }

    public void setFilmsUrls(List<String> filmsUrls) {
        this.filmsUrls = filmsUrls;
    }

    public List<String> getVehiclesUrls() {
        return vehiclesUrls;
    }

    public void setVehiclesUrls(List<String> vehiclesUrls) {
        this.vehiclesUrls = vehiclesUrls;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }
}
