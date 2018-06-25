package com.mittas.starwarswiki.ui.list.character;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.mittas.starwarswiki.R;

public class CharacterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            CharacterListFragment fragment = new CharacterListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, CharacterListFragment.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.character_list_activity_menu, menu);
        return true;
    }
}
