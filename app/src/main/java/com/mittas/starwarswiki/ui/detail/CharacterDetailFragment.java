package com.mittas.starwarswiki.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mittas.starwarswiki.R;
import com.mittas.starwarswiki.data.entity.Film;
import com.mittas.starwarswiki.data.entity.Vehicle;
import com.mittas.starwarswiki.viewmodel.CharacterDetailViewModel;

import java.util.ArrayList;


public class CharacterDetailFragment extends Fragment {
    public static final String TAG = "CHAR_DETAIL_FRAGMENT";
    private static final String ARG_CHAR_ID = "CHAR_ID";
    private int characterId;

    private CharacterDetailViewModel viewModel;

    private TextView nameTextView;
    private TextView birthYearTextView;
    private TextView homeworldTextView;

    private FilmListAdapter filmListAdapter;
    private RecyclerView filmListRecyclerView;

    private VehicleListAdapter vehicleListAdapter;
    private RecyclerView vehicleListRecyclerView;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.characterId = args.getInt(ARG_CHAR_ID, -1);
    }

    public static CharacterDetailFragment newInstance(int characterId) {
        CharacterDetailFragment fragment = new CharacterDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CHAR_ID, characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_detail, container, false);

        setViews(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CharacterDetailViewModel.class);

        subscribeUi();
    }

    private void subscribeUi() {
        viewModel.getCharacterById(characterId).observe(this, character -> {
            if (character != null) {
                nameTextView.setText(character.getName());
                birthYearTextView.setText(character.getBirthYear());
                homeworldTextView.setText(character.getHomeworld());
            }
        });

        viewModel.getFilmsByCharacterId(characterId).observe(this, films -> filmListAdapter.setFilms(films));

        viewModel.getVehiclesByCharacterId(characterId).observe(this, vehicles -> vehicleListAdapter.setVehicles(vehicles));

    }


    private void setViews(View rootView) {
        nameTextView = rootView.findViewById(R.id.name_textview);
        birthYearTextView = rootView.findViewById(R.id.birth_year_textview);
        homeworldTextView = rootView.findViewById(R.id.homeworld_textview);

        // Setup RecyclerViews
        filmListRecyclerView = rootView.findViewById(R.id.films_recyclerview);
        LinearLayoutManager filmLayoutManager = new LinearLayoutManager(getActivity());
        filmListRecyclerView.setLayoutManager(filmLayoutManager);
//        DividerItemDecoration filmDividerItemDecoration = new DividerItemDecoration(filmListRecyclerView.getContext(),
//                filmLayoutManager.getOrientation());
//        filmListRecyclerView.addItemDecoration(filmDividerItemDecoration);
        filmListAdapter = new FilmListAdapter(new ArrayList<>());
        filmListRecyclerView.setAdapter(filmListAdapter);

        vehicleListRecyclerView = rootView.findViewById(R.id.vehicles_recyclerview);
        LinearLayoutManager vehicleLayoutManager = new LinearLayoutManager(getActivity());
        vehicleListRecyclerView.setLayoutManager(vehicleLayoutManager);
//        DividerItemDecoration vehicleDividerItemDecoration = new DividerItemDecoration(vehicleListRecyclerView.getContext(),
//                vehicleLayoutManager.getOrientation());
//        vehicleListRecyclerView.addItemDecoration(vehicleDividerItemDecoration);
        vehicleListAdapter = new VehicleListAdapter(new ArrayList<>());
        vehicleListRecyclerView.setAdapter(vehicleListAdapter);
    }
}