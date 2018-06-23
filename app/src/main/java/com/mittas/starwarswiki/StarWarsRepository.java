package com.mittas.starwarswiki;

import android.arch.lifecycle.LiveData;

import android.provider.ContactsContract;

import com.mittas.starwarswiki.data.LocalDatabase;
import com.mittas.starwarswiki.data.entity.Character;

import java.util.List;

public class StarWarsRepository {
    private static StarWarsRepository INSTANCE;
    private final LocalDatabase localDb;
    private final AppExecutors executors;

    private StarWarsRepository(final LocalDatabase localDb, final AppExecutors executors) {
        this.localDb = localDb;
        this.executors = executors;
    }

    public static StarWarsRepository getInstance(final LocalDatabase localDb, final AppExecutors executors) {
        if (INSTANCE == null) {
            INSTANCE = new StarWarsRepository(localDb, executors);
        }
        return INSTANCE;
    }

    public LiveData<List<Character>> getAllCharacters() {
        return localDb.characterDao().getAllCharacters();
    }

    public LiveData<Character> getCharacterById(final int characterId) {
        return localDb.characterDao().getCharacterById(characterId);
    }

    public void loadCharacters() {

    }
}
