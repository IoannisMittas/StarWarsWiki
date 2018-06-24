package com.mittas.starwarswiki.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mittas.starwarswiki.R;
import com.mittas.starwarswiki.data.entity.Film;

import java.util.List;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder>{
    private List<Film> filmList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilmListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Film film = filmList.get(position);
        holder.nameTextView.setText(film.getName());
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public void setFilms (List<Film> filmList) {
        this.filmList = filmList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.item_textview);
        }
    }
}
