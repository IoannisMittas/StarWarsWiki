package com.mittas.starwarswiki;

import android.arch.lifecycle.LiveData;

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

    public void setCharAsFavourite(final int characterId) {
        executors.diskIO().execute(() -> {
            FavouriteCharacter favouriteChar = new FavouriteCharacter();
            long favouriteCharId = localDb.favouriteCharacterDao().insert(favouriteChar);
            localDb.favouriteCharacterDao().updateFavouriteCharacterId((int) favouriteCharId, characterId);
        });
    }

    public void removeCharFromFavourites(final int characterId) {
        executors.diskIO().execute(() -> {
            localDb.favouriteCharacterDao().deleteByCharacterId(characterId);
        });
    }

    public void onFavouriteToggleClicked(final int characterId) {

    }

    public void loadData() {
        loadCharactersPage(1);
        loadFilmsPage(1);
        loadVehiclesPage(1);
        loadPlanetsPage(1);
    }

    private void loadCharactersPage(int page) {
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
                    loadCharactersPage(page + 1);
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

    private void loadFilmsPage(int page) {
        service.getFilmsPage(page).enqueue(new Callback<FilmsPage>() {
            @Override
            public void onResponse(Call<FilmsPage> call, Response<FilmsPage> response) {
                List<Film> films = response.body().results;

                if (films != null) {
                    executors.diskIO().execute(() -> localDb.filmDao().insertFilms(films));
                }

                if (response.body().next != null) {
                    loadFilmsPage(page + 1);
                }
            }

            @Override
            public void onFailure(Call<FilmsPage> call, Throwable t) {
                // do nothing
            }
        });
    }

    private void loadVehiclesPage(int page) {
        service.getVehiclesPage(page).enqueue(new Callback<VehiclesPage>() {
            @Override
            public void onResponse(Call<VehiclesPage> call, Response<VehiclesPage> response) {
                List<Vehicle> vehicles = response.body().results;

                if (vehicles != null) {
                    executors.diskIO().execute(() -> localDb.vehicleDao().insertVehicles(vehicles));
                }

                if (response.body().next != null) {
                    loadVehiclesPage(page + 1);
                }
            }

            @Override
            public void onFailure(Call<VehiclesPage> call, Throwable t) {
                // do nothing
            }
        });
    }

    private void loadPlanetsPage(int page) {
        service.getPlanetsPage(page).enqueue(new Callback<PlanetsPage>() {
            @Override
            public void onResponse(Call<PlanetsPage> call, Response<PlanetsPage> response) {
                List<Planet> planets = response.body().results;

                if (planets != null) {
                    executors.diskIO().execute(() -> localDb.planetDao().insertPlanets(planets));
                }

                if (response.body().next != null) {
                    loadPlanetsPage(page + 1);
                }
            }

            @Override
            public void onFailure(Call<PlanetsPage> call, Throwable t) {
                // do nothing
            }
        });
    }

}
