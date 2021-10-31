package com.codeinflow.timely.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinflow.timely.Model.ConstableModel;
import com.codeinflow.timely.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ConstableAdapter extends RecyclerView.Adapter<ConstableAdapter.ViewHolder> {

    private ArrayList<ConstableModel> modelArrayList;
    private Context context;


    public ConstableAdapter(ArrayList<ConstableModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_const, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConstableModel model = modelArrayList.get(position);

        Picasso.get().load(model.getConstImage()).into(holder.ConstImage);
        holder.ConstName.setText(model.getConstName());
        holder.date.setText(model.getConstDate());
        holder.place.setText(model.getConstPlace());

    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView ConstName, date, place;
        ImageView ConstImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ConstName = itemView.findViewById(R.id.Name);
            ConstImage = itemView.findViewById(R.id.img);
            date = itemView.findViewById(R.id.date);
            place = itemView.findViewById(R.id.place);
        }
    }
}