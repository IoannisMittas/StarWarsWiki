package com.mittas.starwarswiki.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.mittas.starwarswiki.data.entity.Character;

// TODO: removed foreignKeys to stop db asynchronous population problems, need fix
//        foreignKeys = {
//                @ForeignKey(entity = Character.class,
//                        parentColumns = "id",
//                        childColumns = "characterId"),
//                @ForeignKey(entity = Film.class,
//                        parentColumns = "id",
//                        childColumns = "filmId")
//        }
@Entity(tableName = "character_film_join",
        primaryKeys = {"characterId", "filmId"})
public class CharacterFilmJoin {
    private int characterId;

    private int filmId;

    public CharacterFilmJoin(){}

    public CharacterFilmJoin(int characterId, int filmId) {
        this.characterId = characterId;
        this.filmId = filmId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
}
