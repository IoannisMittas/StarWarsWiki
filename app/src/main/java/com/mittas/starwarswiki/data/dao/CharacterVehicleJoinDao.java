package com.mittas.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mittas.starwarswiki.data.entity.CharacterVehicleJoin;
import com.mittas.starwarswiki.data.entity.Vehicle;
import com.mittas.starwarswiki.data.entity.Character;


import java.util.List;

@Dao
public interface CharacterVehicleJoinDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CharacterVehicleJoin characterVehicleJoin);

    @Query("SELECT * FROM character INNER JOIN character_vehicle_join "
            + "ON character.id = character_vehicle_join.characterId "
            + "WHERE character_vehicle_join.vehicleId = :vehicleId")
   LiveData<List<Character>> getCharactersByVehicleId(final int vehicleId);

    @Query("SELECT * FROM vehicle INNER JOIN character_vehicle_join "
            + "ON vehicle.id = character_vehicle_join.vehicleId "
            + "WHERE character_vehicle_join.characterId = :characterId")
    LiveData<List<Vehicle>> getVehiclesByCharacterId(final int characterId);
}
