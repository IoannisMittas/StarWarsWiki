package com.mittas.starwarswiki.ui.list.favourite;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.mittas.starwarswiki.ui.list.character.CharacterListAdapter;
import com.mittas.starwarswiki.viewmodel.CharacterListViewModel;
import com.mittas.starwarswiki.viewmodel.FavouriteCharacterListViewModel;

import java.util.ArrayList;


public class FavouriteCharacterListFragment extends Fragment {
    public static final String TAG = "FAV_CHAR_LIST_FRAG";

    private FavouriteCharacterListViewModel viewModel;
    private FavouriteCharacterListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourite_character_list, container, false);

        setHasOptionsMenu(true);

        setupRecyclerView(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(FavouriteCharacterListViewModel.class);

        subscribeUi();
    }


    private void setupRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new FavouriteCharacterListAdapter(new ArrayList<Character>());
        recyclerView.setAdapter(adapter);
    }

    private void subscribeUi() {
        viewModel.getAllFavouriteCharacters().observe(this, characters -> adapter.setFavouriteCharacters(characters));
    }
}
