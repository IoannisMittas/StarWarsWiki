package com.mittas.starwarswiki.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.mittas.starwarswiki.R;
import com.mittas.starwarswiki.ui.list.character.CharacterListFragment;

public class CharacterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if(intent.hasExtra(CharacterListFragment.EXTRA_CHARACTER_ID)) {
                int itemId = intent.getIntExtra(CharacterListFragment.EXTRA_CHARACTER_ID, -1);

                CharacterDetailFragment fragment = CharacterDetailFragment.newInstance(itemId);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, fragment, CharacterDetailFragment.TAG).commit();
            }
        }
    }
}
