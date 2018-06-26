package com.mittas.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.mittas.starwarswiki.api.SwapiService;
import com.mittas.starwarswiki.api.model.CharactersPage;
import com.mittas.starwarswiki.api.model.FilmsPage;
import com.mittas.starwarswiki.api.model.PlanetsPage;
import com.mittas.starwarswiki.api.model.VehiclesPage;
import com.mittas.starwarswiki.data.LocalDatabase;
import com.mittas.starwarswiki.data.entity.Character;
import com.mittas.starwarswiki.data.entity.CharacterFilmJoin;
import com.mittas.starwarswiki.data.entity.CharacterVehicleJoin;
import com.mittas.starwarswiki.data.entity.FavouriteCharacter;
import com.mittas.starwarswiki.data.entity.Film;
import com.mittas.starwarswiki.data.entity.Planet;
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

    public LiveData<List<Character>> getAllFavouriteCharacters() {
        return localDb.favouriteCharacterDao().getAllFavouriteCharacters();
    }

    public LiveData<Character> getCharacterById(final int characterId) {
        return localDb.characterDao().getCharacterById(characterId);
    }

    public LiveData<List<Film>> getFilmsByCharacterId(final int characterId) {
        return localDb.characterFilmJoinDao().getFilmsByCharacterId(characterId);
    }

    public LiveData<List<Vehicle>> getVehiclesByCharacterId(final int characterId) {
        return localDb.characterVehicleJoinDao().getVehiclesByCharacterId(characterId);
    }

    public void onFavouriteToggleClicked(final int characterId) {
        executors.diskIO().execute(() -> {
            int isFavourite = localDb.characterDao().isCharacterFavourite(characterId);

            // Favourite toggle has been clicked. So, if character was favourite, we
            // remove it from favourites. And vice versa.
            if (isFavourite == 1) {
                removeCharFromFavouriteCharacterTable(characterId);

                localDb.characterDao().unFavouriteChar(characterId);
            } else {
                insertCharIntoFavouriteCharacterTable(characterId);

                localDb.characterDao().setCharAsFavourite(characterId);
            }
        });
    }

    private void insertCharIntoFavouriteCharacterTable(final int characterId) {
        executors.diskIO().execute(() -> {
            FavouriteCharacter favouriteChar = new FavouriteCharacter();
            long favouriteCharId = localDb.favouriteCharacterDao().insert(favouriteChar);
            localDb.favouriteCharacterDao().updateFavouriteCharacterId((int) favouriteCharId, characterId);
        });
    }

    private void removeCharFromFavouriteCharacterTable(final int characterId) {
        executors.diskIO().execute(() -> {
            localDb.favouriteCharacterDao().deleteByCharacterId(characterId);
        });
    }

    public void loadData() {
        Log.d("Repo", "loadData called");

        // Load pages recursively
        int startingPage = 1;
        loadCharacters(startingPage);
        loadFilms(startingPage);
        loadVehicles(startingPage);
        loadPlanets(startingPage);
    }

    private void loadCharacters(int page) {

        Log.d("Repo", "loadCharacter called");

        service.getCharactersPage(page).enqueue(new Callback<CharactersPage>() {
            @Override
            public void onResponse(Call<CharactersPage> call, Response<CharactersPage> response) {
                List<Character> characters = response.body().results;

                if (characters != null) {
                    for (Character character : characters) {
                        executors.diskIO().execute(() -> {
                            long charId = localDb.characterDao().insertCharacter(character);
                            insertCharacterTableLinks(character, charId);
                            updateCharacterHomeworldName(character, charId);
                        });
                    }
                }

                if (response.body().next != null) {
                    loadCharacters(page + 1);
                }
            }

            @Override
            public void onFailure(Call<CharactersPage> call, Throwable t) {
                // do nothing
            }
        });
    }


    private void insertCharacterTableLinks(Character character, long charId) {
        // Create character-film link table
        List<String> filmsUrls = character.getFilmsUrls();
        if (filmsUrls != null) {
            for (String filmUrl : filmsUrls) {
                int filmId = SwapiHelperMethods.getIdFromUrl(filmUrl);
                CharacterFilmJoin charFilmJoin = new CharacterFilmJoin((int) charId, filmId);
                localDb.characterFilmJoinDao().insert(charFilmJoin);
            }

            // Create character-vehicle link table
            List<String> vehiclesUrls = character.getVehiclesUrls();
            if (vehiclesUrls != null) {
                for (String vehicleUrl : vehiclesUrls) {
                    int vehicleId = SwapiHelperMethods.getIdFromUrl(vehicleUrl);
                    CharacterVehicleJoin charVehicleJoin = new CharacterVehicleJoin((int) charId, vehicleId);
                    localDb.characterVehicleJoinDao().insert(charVehicleJoin);
                }
            }
        }
    }

    private void updateCharacterHomeworldName(Character character, long charId) {
        String homeworldUrl = character.getHomeworldUrl();
        if (homeworldUrl != null) {
            int planetId = SwapiHelperMethods.getIdFromUrl(homeworldUrl);

            // TODO: fix possible syncrhonization problems
            Planet planet = localDb.planetDao().getPlanetById(planetId);

            if (planet != null) {
                localDb.characterDao().updateCharHomeworldNameById((int) charId, planet.getName());
            }
        }
    }

    private void loadFilms(int page) {
        Log.d("Repo", "loadFilms called");


        service.getFilmsPage(page).enqueue(new Callback<FilmsPage>() {
            @Override
            public void onResponse(Call<FilmsPage> call, Response<FilmsPage> response) {
                List<Film> films = response.body().results;

                if (films != null) {
                    executors.diskIO().execute(() -> localDb.filmDao().insertFilms(films));
                }

                if (response.body().next != null) {
                    loadFilms(page + 1);
                }
            }

            @Override
            public void onFailure(Call<FilmsPage> call, Throwable t) {
                // do nothing
            }
        });
    }

    private void loadVehicles(int page) {
        service.getVehiclesPage(page).enqueue(new Callback<VehiclesPage>() {
            @Override
            public void onResponse(Call<VehiclesPage> call, Response<VehiclesPage> response) {
                List<Vehicle> vehicles = response.body().results;

                if (vehicles != null) {
                    executors.diskIO().execute(() -> localDb.vehicleDao().insertVehicles(vehicles));
                }

                if (response.body().next != null) {
                    loadVehicles(page + 1);
                }
            }

            @Override
            public void onFailure(Call<VehiclesPage> call, Throwable t) {
                // do nothing
            }
        });
    }

    private void loadPlanets(int page) {
        service.getPlanetsPage(page).enqueue(new Callback<PlanetsPage>() {
            @Override
            public void onResponse(Call<PlanetsPage> call, Response<PlanetsPage> response) {
                List<Planet> planets = response.body().results;

                if (planets != null) {
                    executors.diskIO().execute(() -> localDb.planetDao().insertPlanets(planets));
                }

                if (response.body().next != null) {
                    loadPlanets(page + 1);
                }
            }

            @Override
            public void onFailure(Call<PlanetsPage> call, Throwable t) {
                // do nothing
            }
        });
    }

}
