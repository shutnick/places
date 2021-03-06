package com.example.moreno.places.components.root.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.moreno.places.R;

/**
 * Created on 10.10.2015.
 */
public class PlaceViewHolder extends RecyclerView.ViewHolder{
    public final View root;
    public final NetworkImageView icon;
    public final TextView distance;
    public final TextView name;
    public final TextView address;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        root = itemView;
        icon = (NetworkImageView) itemView.findViewById(R.id.place_icon);
        distance = (TextView) itemView.findViewById(R.id.place_distance);
        name = (TextView) itemView.findViewById(R.id.place_name);
        address = (TextView) itemView.findViewById(R.id.place_address);
    }
}
