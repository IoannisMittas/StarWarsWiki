package com.mittas.starwarswiki.ui.list.favourite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mittas.starwarswiki.R;
import com.mittas.starwarswiki.data.entity.Character;

import java.util.List;

public class FavouriteCharacterListAdapter extends RecyclerView.Adapter<FavouriteCharacterListAdapter.ViewHolder> {
    private List<Character> favouriteCharList;

    public FavouriteCharacterListAdapter(List<Character> favouriteCharList) {
        this.favouriteCharList = favouriteCharList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteCharacterListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = favouriteCharList.get(position);
                holder.nameTextView.setText(character.getName());
    }

    @Override
    public int getItemCount() {
        return favouriteCharList.size();
    }

    public void setFavouriteCharacters(List<Character> charList) {
        this.favouriteCharList = charList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.item_textview);
        }
    }
}
