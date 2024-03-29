package com.mittas.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mittas.starwarswiki.data.entity.Character;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM character WHERE id = :id")
    LiveData<Character> getCharacterById(int id);

    @Query("SELECT * FROM character ORDER BY character.name ASC")
    LiveData<List<Character>> getAllCharactersSortedByName();

    @Query("SELECT * FROM character ORDER BY character.birthYear ASC")
    LiveData<List<Character>> getAllCharactersSortedByYear();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCharacter(Character character);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCharacters(List<Character> characters);

    @Query("UPDATE character SET homeworldName = :name WHERE id = :id")
    void updateCharHomeworldNameById(int id, String name);

    @Query("SELECT isFavourite FROM character WHERE id = :id")
    int isCharacterFavourite(int id);

    @Query("UPDATE character SET isFavourite = 1 WHERE id = :id")
    void setCharAsFavourite(int id);

    @Query("UPDATE character SET isFavourite = 0 WHERE id = :id")
    void unFavouriteChar(int id);
}
