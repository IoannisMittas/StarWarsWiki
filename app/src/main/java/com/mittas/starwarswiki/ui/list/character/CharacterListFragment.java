package com.mittas.starwarswiki.ui.list.character;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mittas.starwarswiki.R;
import com.mittas.starwarswiki.data.entity.Character;
import com.mittas.starwarswiki.ui.detail.CharacterDetailActivity;
import com.mittas.starwarswiki.ui.list.favourite.FavouriteCharacterListActivity;
import com.mittas.starwarswiki.viewmodel.CharacterListViewModel;

import java.util.ArrayList;


public class CharacterListFragment extends Fragment {
    public static final String EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID";
    public static final String TAG = "CHARACTER_LIST_FRAG";

    private boolean isSortDefault = true;

    private CharacterListViewModel viewModel;
    private CharacterListAdapter adapter;
    private RecyclerView recyclerView;

    private CharacterListAdapter.OnItemClickListener listItemClickListener = (view, characterId) -> {
        // Start CharacterDetailActivity
        Intent intent = new Intent(getActivity(), CharacterDetailActivity.class);
        intent.putExtra(EXTRA_CHARACTER_ID, characterId);
        startActivity(intent);
    };

    private CharacterListAdapter.OnItemClickListener favouriteToggleClickListener = ((view, charId) -> {
        viewModel.onFavouriteToggleClicked(charId);
    });

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_list, container, false);

        setHasOptionsMenu(true);

        setupRecyclerView(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);

        viewModel.loadData();

        subscribeUi();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourites:
                Intent intent = new Intent(getActivity(), FavouriteCharacterListActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_sort:
                // Change sorting with every click
                isSortDefault = !isSortDefault;
                viewModel.setDefaultSorting(isSortDefault);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new CharacterListAdapter(new ArrayList<Character>(), listItemClickListener, favouriteToggleClickListener);
        recyclerView.setAdapter(adapter);
    }

    private void subscribeUi() {
        viewModel.getAllCharacters().observe(this, characters -> adapter.setCharacters(characters));
    }
}
