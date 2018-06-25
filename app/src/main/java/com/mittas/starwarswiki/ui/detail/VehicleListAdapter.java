package com.mittas.starwarswiki.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mittas.starwarswiki.R;
import com.mittas.starwarswiki.data.entity.Vehicle;

import java.util.List;

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder>{
    private List<Vehicle> vehicleList;

    public VehicleListAdapter(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VehicleListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.simple_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);
        holder.nameTextView.setText(vehicle.getName());
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public void setVehicles (List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
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
