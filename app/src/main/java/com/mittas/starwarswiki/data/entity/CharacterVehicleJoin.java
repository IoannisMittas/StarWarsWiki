package com.mittas.starwarswiki.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

// TODO: removed foreignKeys to stop db asynchronous population problems, need fix
//        foreignKeys = {
//                @ForeignKey(entity = Character.class,
//                        parentColumns = "id",
//                        childColumns = "characterId"),
//                @ForeignKey(entity = Film.class,
//                        parentColumns = "id",
//                        childColumns = "vehicleId")
//        }
@Entity(tableName = "character_vehicle_join",
        primaryKeys = {"characterId", "vehicleId"})
public class CharacterVehicleJoin {
    private int characterId;

    private int vehicleId;

    public CharacterVehicleJoin() {
    }

    public CharacterVehicleJoin(int characterId, int vehicleId) {
        this.characterId = characterId;
        this.vehicleId = vehicleId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}

