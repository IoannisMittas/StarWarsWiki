package com.mittas.starwarswiki.ui.list.character;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mittas.starwarswiki.R;

import com.mittas.starwarswiki.data.entity.Character;

import java.util.List;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder>{
    private List<Character> characterList;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int characterId);
    }

    public CharacterListAdapter(List<Character> characterList, OnItemClickListener clickListener) {
        this.characterList = characterList;
        this.clickListener = clickListener;
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
                clickListener.onItemClick(v, character.getId());
            }
        }));
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

        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.name_textview);
        }
    }

}
