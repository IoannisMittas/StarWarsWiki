package com.mittas.starwarswiki.ui.list.favourite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.mittas.starwarswiki.R;
import com.mittas.starwarswiki.ui.list.character.CharacterListFragment;

public class FavouriteCharacterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_character_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            FavouriteCharacterListFragment fragment = new FavouriteCharacterListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, FavouriteCharacterListFragment.TAG).commit();
        }
    }
}
