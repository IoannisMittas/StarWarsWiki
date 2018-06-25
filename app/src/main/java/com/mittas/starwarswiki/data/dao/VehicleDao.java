package com.mittas.starwarswiki.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.mittas.starwarswiki.data.entity.Vehicle;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface VehicleDao {
    @Insert
    void insertVehicles(List<Vehicle> vehicles);
}
