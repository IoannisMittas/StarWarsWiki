package com.mittas.starwarswiki.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.mittas.starwarswiki.data.entity.Film;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFilms(List<Film> films);
}
