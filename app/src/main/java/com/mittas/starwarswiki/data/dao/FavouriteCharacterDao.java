package com.mittas.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.mittas.starwarswiki.data.entity.FavouriteCharacter;

import java.util.List;

@Dao
public interface FavouriteCharacterDao {
    @Insert
    void insert(FavouriteCharacter character);

    @Query("SELECT * FROM character INNER JOIN favourite_character "
            + "ON character.id = favourite_character.characterId")
    LiveData<List<Character>> getAllFavouriteCharacters();
}
