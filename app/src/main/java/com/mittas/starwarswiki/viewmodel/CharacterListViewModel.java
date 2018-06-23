package com.mittas.starwarswiki.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.mittas.starwarswiki.BasicApp;
import com.mittas.starwarswiki.StarWarsRepository;

import java.util.List;

public class CharacterListViewModel extends AndroidViewModel{
    private final MediatorLiveData<List<Character>> observableCharacters;
    private final StarWarsRepository repository;

    public CharacterListViewModel(Application application) {
        super(application);

        repository = ((BasicApp) application).getRepository();

        observableCharacters = new MediatorLiveData<>();

        LiveData<List<Character>> charactersInput = repository.getAllCharacters();

        // observe the changes of the characters from the database and forward them
        observableCharacters.addSource(charactersInput, (characters) -> observableCharacters.setValue(characters));
    }

    /**
     * Expose the LiveData characters query so the UI can observe it.
     */
    public LiveData<List<Character>> getAllCharacters() {
        return observableCharacters;
    }

    public void syncCharacters() {
        repository.syncCharacters();
    }
}
