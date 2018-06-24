package com.mittas.starwarswiki;

import android.arch.lifecycle.LiveData;

import com.mittas.starwarswiki.api.SwapiService;
import com.mittas.starwarswiki.api.model.CharactersPage;
import com.mittas.starwarswiki.api.model.FilmsPage;
import com.mittas.starwarswiki.api.model.VehiclesPage;
import com.mittas.starwarswiki.data.LocalDatabase;
import com.mittas.starwarswiki.data.entity.Character;
import com.mittas.starwarswiki.data.entity.CharacterFilmJoin;
import com.mittas.starwarswiki.data.entity.CharacterVehicleJoin;
import com.mittas.starwarswiki.data.entity.Film;
import com.mittas.starwarswiki.data.entity.Vehicle;
import com.mittas.starwarswiki.util.SwapiHelperMethods;

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

    public void loadData() {
        loadCharacterPage(1);
        loadFilmPage(1);
        loadVehiclePage(1);
    }

    private void loadCharacterPage(int page) {
        service.getCharactersPage(page).enqueue(new Callback<CharactersPage>() {
            @Override
            public void onResponse(Call<CharactersPage> call, Response<CharactersPage> response) {
                List<Character> characters = response.body().results;

                executors.diskIO().execute(() -> localDb.characterDao().insertCharacters(characters));

                insertCharacterTableLinks(characters);

                if (response.body().next != null) {
                    loadCharacterPage(page + 1);
                }
            }

            @Override
            public void onFailure(Call<CharactersPage> call, Throwable t) {
                // do nothing
            }
        });
    }

    private void insertCharacterTableLinks(List<Character> characters) {
        for (Character character : characters) {
            int charId = character.getId();

            // Create character-film link table
            for (String filmUrl : character.getFilmsSwapiUrls()) {
                int filmId = SwapiHelperMethods.getIdFromUrl(filmUrl);
                CharacterFilmJoin charFilmJoin = new CharacterFilmJoin(charId, filmId);
                executors.diskIO().execute(() -> localDb.characterFilmJoinDao().insert(charFilmJoin));
            }

            // Create character-vehicle link table
            for (String vehicleUrl : character.getVehiclesSwapiUrls()) {
                int vehicleId = SwapiHelperMethods.getIdFromUrl(vehicleUrl);
                CharacterVehicleJoin charVehicleJoin = new CharacterVehicleJoin(charId, vehicleId);
                executors.diskIO().execute(() -> localDb.characterVehicleJoinDao().insert(charVehicleJoin));
            }
        }
    }

    private void loadFilmPage(int page) {
        service.getFilmsPage(page).enqueue(new Callback<FilmsPage>() {
            @Override
            public void onResponse(Call<FilmsPage> call, Response<FilmsPage> response) {
                List<Film> films = response.body().results;

                executors.diskIO().execute(() -> localDb.filmDao().insertFilms(films));

                if (response.body().next != null) {
                    loadFilmPage(page + 1);
                }
            }

            @Override
            public void onFailure(Call<FilmsPage> call, Throwable t) {
                // do nothing
            }
        });
    }

    private void loadVehiclePage(int page) {
        service.getVehiclesPage(page).enqueue(new Callback<VehiclesPage>() {
            @Override
            public void onResponse(Call<VehiclesPage> call, Response<VehiclesPage> response) {
                List<Vehicle> vehicles = response.body().results;

                executors.diskIO().execute(() -> localDb.vehicleDao().insertVehicles(vehicles));

                if (response.body().next != null) {
                    loadVehiclePage(page + 1);
                }
            }

            @Override
            public void onFailure(Call<VehiclesPage> call, Throwable t) {
                // do nothing
            }
        });
    }

}
