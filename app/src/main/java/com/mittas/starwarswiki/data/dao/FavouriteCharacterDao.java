package com.mittas.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.mittas.starwarswiki.data.entity.FavouriteCharacter;
import com.mittas.starwarswiki.data.entity.Character;

import java.util.List;

@Dao
public interface FavouriteCharacterDao {
    @Insert
    long insert(FavouriteCharacter character);

    @Query("UPDATE favourite_character SET characterId = :charId WHERE  id = :id")
    void updateFavouriteCharacterId(int id, int charId);

    @Query("SELECT * FROM character INNER JOIN favourite_character "
            + "ON character.id = favourite_character.characterId")
    LiveData<List<Character>> getAllFavouriteCharacters();

    @Query("DELETE FROM favourite_character WHERE characterId = :id")
    void deleteByCharacterId(int id);
}
