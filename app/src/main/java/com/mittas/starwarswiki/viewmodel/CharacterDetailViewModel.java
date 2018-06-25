package com.mittas.starwarswiki.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.mittas.starwarswiki.BasicApp;
import com.mittas.starwarswiki.StarWarsRepository;
import com.mittas.starwarswiki.data.entity.Character;
import com.mittas.starwarswiki.data.entity.Film;
import com.mittas.starwarswiki.data.entity.Vehicle;

import java.util.List;

public class CharacterDetailViewModel extends AndroidViewModel {
    private final StarWarsRepository repository;
    private final LiveData<Character> observableCharacter;
    private final LiveData<List<Film>> observableFilms;
    private final LiveData<List<Vehicle>> observableVehicles;
    private final MutableLiveData<Integer> idInput;


    public CharacterDetailViewModel(@NonNull Application application) {
        super(application);

        repository = ((BasicApp) application).getRepository();

        idInput = new MutableLiveData<>();

        observableCharacter = Transformations.switchMap(idInput, id ->
                repository.getCharacterById(id));

        observableFilms = Transformations.switchMap(idInput, id ->
                repository.getFilmsByCharacterId(id));

        observableVehicles = Transformations.switchMap(idInput, id ->
                repository.getVehiclesByCharacterId(id));
    }

    public LiveData<Character> getCharacterById(int characterId) {
        setCharacterId(characterId);
        return observableCharacter;
    }

    public LiveData<List<Film>> getFilmsByCharacterId(int characterId) {
        setCharacterId(characterId);
        return observableFilms;
    }

    public LiveData<List<Vehicle>> getVehiclesByCharacterId(int characterId) {
        setCharacterId(characterId);
        return observableVehicles;
    }

    private void setCharacterId(int characterId) {
        this.idInput.setValue(characterId);
    }


}
