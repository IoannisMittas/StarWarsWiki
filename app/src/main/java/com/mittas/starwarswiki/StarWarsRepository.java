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

    public void fetchData() {
        // Reset db for new data are to be fetched
        executors.diskIO().execute(() -> localDb.clearAllTables());

        remoteDb.addOnSyncRequestListener(new RemoteDatabase.syncRequestListener() {
            @Override
            public void onSuccess(List<Note> allNotes) {
                if (allNotes != null) {
                    executors.diskIO().execute(() -> localDb.noteDao().insertNotes(allNotes));
                }
            }

            @Override
            public void onFailure(String message) {
                // do nothing
            }
        });
    }
}
