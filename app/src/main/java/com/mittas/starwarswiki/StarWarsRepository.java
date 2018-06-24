package com.mittas.starwarswiki;

import android.arch.lifecycle.LiveData;

import com.mittas.starwarswiki.api.SwapiService;
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
        int page = 1;
        boolean hasNextPage = true;
        while (hasNextPage) {
            service.getAllCharacters(page).enqueue(new Callback<List<Character>>() {
                @Override
                public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                    if(response.next )
                }

                @Override
                public void onFailure(Call<List<Character>> call, Throwable t) {

                }
            });
        }
    }

}
