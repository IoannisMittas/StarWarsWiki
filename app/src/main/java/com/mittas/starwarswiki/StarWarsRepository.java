package com.mittas.starwarswiki;

import android.arch.lifecycle.MediatorLiveData;

import com.mittas.starwarswiki.data.LocalDatabase;

import java.util.List;

public class StarWarsRepository {
    private static StarWarsRepositoryINSTANCE;
    private final LocalDatabase localDb;
    private final AppExecutors executors;
    private MediatorLiveData<List<Character>> observableCharacters;

    private NoteRepository(final LocalDatabase localDb, final RemoteDatabase remoteDb, final AppExecutors executors) {
        this.localDb = localDb;
        this.remoteDb = remoteDb;
        this.executors = executors;

        observableNotes = new MediatorLiveData<>();
        observableNotes.addSource(this.localDb.noteDao().getAllNotes(),
                notes -> observableNotes.postValue(notes));
    }

    public static StarWarsRepository getInstance(final LocalDatabase localDb, final RemoteDatabase remoteDb, final AppExecutors executors) {
        if (INSTANCE == null) {
            INSTANCE = new NoteRepository(localDb, remoteDb, executors);
        }
        return INSTANCE;
    }

    /**
     * Get the list of notes from the localDb and get notified when the data changes.
     */
    public LiveData<List<Note>> getAllNotes() {
        return observableNotes;
    }

    public LiveData<Note> getNoteById(final int noteId) {
        return localDb.noteDao().getNoteById(noteId);
    }

    public void addNote(final Note note) {
        if (note != null) {
            executors.diskIO().execute(() -> {
                long noteId = localDb.noteDao().insertNote(note);

                remoteDb.addNote(note, noteId);
            });
        }
    }


    public void syncNotes() {
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
