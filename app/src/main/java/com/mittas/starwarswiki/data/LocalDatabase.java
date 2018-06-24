package com.mittas.starwarswiki.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mittas.starwarswiki.data.entity.Character;
import com.mittas.starwarswiki.data.dao.CharacterDao;
import com.mittas.starwarswiki.data.dao.CharacterFilmJoinDao;
import com.mittas.starwarswiki.data.dao.CharacterVehicleJoinDao;
import com.mittas.starwarswiki.data.dao.FavouriteCharacterDao;
import com.mittas.starwarswiki.data.dao.FilmDao;
import com.mittas.starwarswiki.data.dao.VehicleDao;
import com.mittas.starwarswiki.data.entity.CharacterFilmJoin;
import com.mittas.starwarswiki.data.entity.CharacterVehicleJoin;
import com.mittas.starwarswiki.data.entity.FavouriteCharacter;
import com.mittas.starwarswiki.data.entity.Film;
import com.mittas.starwarswiki.data.entity.Vehicle;

@Database(entities = {Character.class, CharacterFilmJoin.class, CharacterVehicleJoin.class,
        FavouriteCharacter.class, Film.class, Vehicle.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase{
    private static LocalDatabase INSTANCE;

    public static final String DATABASE_NAME = "star_wars_local_database";

    public abstract CharacterDao characterDao();
    public abstract CharacterFilmJoinDao characterFilmJoinDao();
    public abstract CharacterVehicleJoinDao characterVehicleJoinDao();
    public abstract FavouriteCharacterDao favouriteCharacterDao();
    public abstract FilmDao filmDao();
    public abstract VehicleDao vehicleDao();

    public static LocalDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static LocalDatabase buildDatabase(final Context appContext) {
        return  Room.databaseBuilder(appContext, LocalDatabase.class, DATABASE_NAME)
                .build();
    }
}
