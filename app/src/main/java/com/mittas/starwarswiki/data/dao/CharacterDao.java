package com.mittas.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mittas.starwarswiki.data.entity.Character;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM character WHERE id = :id")
    LiveData<Character> getCharacterById(int id);

    @Query("SELECT * FROM character")
    LiveData<List<Character>> getAllCharacters();

    @Insert(onConflict = REPLACE)
    long insertCharacter(Character character);

    @Insert(onConflict = REPLACE)
    void insertCharacters(List<Character> characters);
}
