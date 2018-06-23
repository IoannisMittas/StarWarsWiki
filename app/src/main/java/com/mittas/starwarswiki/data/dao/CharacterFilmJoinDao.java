package com.mittas.starwarswiki.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mittas.starwarswiki.data.entity.CharacterFilmJoin;
import com.mittas.starwarswiki.data.entity.Film;

import java.util.List;

@Dao
public interface CharacterFilmJoinDao {
    @Insert
    void insert(CharacterFilmJoin characterFilmJoin);

    @Query("SELECT * FROM character INNER JOIN character_film_join "
            + "ON character.id = character_film_join.characterId "
            + "WHERE character_film_join.filmId = :filmId")
    LiveData<List<Character>> getCharactersByFilmId(final int filmId);

    @Query("SELECT * FROM film INNER JOIN character_film_join "
            + "ON film.id = character_film_join.filmId "
            + "WHERE character_film_join.characterId = :characterId")
            LiveData<List<Film>> getFilmsByCharacterId(final int characterId);
}
