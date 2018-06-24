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

public class CharacterDetailViewModel extends AndroidViewModel {
    private final StarWarsRepository repository;
    private final LiveData<Character> observableCharacter;
    private final MutableLiveData<Integer> idInput;


    public CharacterDetailViewModel(@NonNull Application application) {
        super(application);

        repository = ((BasicApp) application).getRepository();

        idInput = new MutableLiveData<>();

        observableCharacter = Transformations.switchMap(idInput, id ->
                repository.getCharacterById(id));
    }

    public LiveData<Character> getCharacterById(int characterId) {
        setCharacterId(characterId);
        return observableCharacter;
    }

    private void setCharacterId(int characterId) {
        this.idInput.setValue(characterId);
    }
}
