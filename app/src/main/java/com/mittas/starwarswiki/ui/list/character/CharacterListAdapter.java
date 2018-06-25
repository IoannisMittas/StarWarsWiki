package com.mittas.starwarswiki.ui.list.character;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mittas.starwarswiki.R;

import com.mittas.starwarswiki.data.entity.Character;

import java.util.List;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {
    private List<Character> characterList;
    private OnItemClickListener listItemClickListener;
    private OnItemClickListener favouriteToggleClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int characterId);
    }

    public CharacterListAdapter(List<Character> characterList, OnItemClickListener listItemClickListener,
                                OnItemClickListener favouriteToggleClickListener) {
        this.characterList = characterList;
        this.listItemClickListener = listItemClickListener;
        this.favouriteToggleClickListener = favouriteToggleClickListener;
    }

    @NonNull
    @Override
    public CharacterListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacterListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterListAdapter.ViewHolder holder, int position) {
        Character character = characterList.get(position);

        holder.nameTextView.setText(character.getName());

        holder.itemView.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemClickListener.onItemClick(v, character.getId());
            }
        }));

        int isFavourite = character.getIsFavourite();
        if(isFavourite == 1) {
            holder.favouriteToggle.setImageResource(R.drawable.favourite_toggle_on);
        } else {
            holder.favouriteToggle.setImageResource(R.drawable.favourite_toggle_off);
        }

        holder.favouriteToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteToggleClickListener.onItemClick(v, character.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public void setCharacters(List<Character> characterList) {
        this.characterList = characterList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private ImageButton favouriteToggle;

        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.name_textview);
            favouriteToggle = view.findViewById(R.id.favouriteToggle);
        }
    }
}
