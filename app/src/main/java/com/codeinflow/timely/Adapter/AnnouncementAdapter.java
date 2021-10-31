package com.codeinflow.timely.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinflow.timely.Model.AnnouncementModel;
import com.codeinflow.timely.Model.ConstableModel;
import com.codeinflow.timely.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private ArrayList<AnnouncementModel> modelArrayList;
    private Context context;

    public AnnouncementAdapter(ArrayList<AnnouncementModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_announcement, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AnnouncementModel model = modelArrayList.get(position);

        holder.name.setText(model.getSenderName());
        holder.msg.setText(model.getSenderMsg());

    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sendername);
            msg = itemView.findViewById(R.id.sendermsg);
        }
    }
}