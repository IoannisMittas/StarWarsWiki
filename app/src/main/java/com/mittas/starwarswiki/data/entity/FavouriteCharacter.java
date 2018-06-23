package com.mittas.starwarswiki.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (foreignKeys = @ForeignKey(entity = Character.class,
        parentColumns = "id",
        childColumns = "characterId",
        onDelete = CASCADE))
public class FavouriteCharacter {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int characterId;

    public FavouriteCharacter(int peopleId) {
        this.characterId = peopleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
}