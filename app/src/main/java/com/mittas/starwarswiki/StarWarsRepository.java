package com.mittas.starwarswiki;

import android.arch.lifecycle.LiveData;

import com.mittas.starwarswiki.api.SwapiService;
import com.mittas.starwarswiki.api.model.CharactersPage;
import com.mittas.starwarswiki.data.LocalDatabase;
import com.mittas.starwarswiki.data.entity.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarWarsRepository {
    private static StarWarsRepository INSTANCE;
    private final LocalDatabase localDb;
    private final SwapiService service;
    private final AppExecutors executors;


    private StarWarsRepository(final LocalDatabase localDb, final SwapiService service, final AppExecutors executors) {
        this.localDb = localDb;
        this.service = service;
        this.executors = executors;
    }

    public static StarWarsRepository getInstance(final LocalDatabase localDb, final SwapiService service, final AppExecutors executors) {
        if (INSTANCE == null) {
            INSTANCE = new StarWarsRepository(localDb, service, executors);
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
        // Load the first page
        loadCharacterPage(1);
    }

    private void loadCharacterPage(int page) {
        service.getCharactersPage(page).enqueue(new Callback<CharactersPage>() {
            @Override
            public void onResponse(Call<CharactersPage> call, Response<CharactersPage> response) {
                List<Character> characters= response.body().results;

                executors.diskIO().execute(() -> localDb.characterDao().insertCharacters(characters));

                if(response.body().next != null) {
                    loadCharacterPage(page +1);
                }
            }

            @Override
            public void onFailure(Call<CharactersPage> call, Throwable t) {
                // do nothing
            }
        });
    }

}
