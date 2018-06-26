package com.mittas.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mittas.starwarswiki.data.entity.Character;
import com.mittas.starwarswiki.data.entity.Planet;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PlanetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlanets(List<Planet> planets);

    @Query("SELECT * FROM planet WHERE id = :id")
    Planet getPlanetById(int id);
}
