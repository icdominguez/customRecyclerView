package com.icdominguez.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomRecyclerView extends androidx.recyclerview.widget.RecyclerView.Adapter<CustomRecyclerView.ViewHolder> {

    ArrayList<Team> dataSet = new ArrayList<Team>();

    public CustomRecyclerView(ArrayList<Team> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public CustomRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerView.ViewHolder holder, int position) {
        holder.tvElement.setText(dataSet.get(position).getNombre());
        holder.ivElement.setImageDrawable(MyApp.getContext().getDrawable(dataSet.get(position).getImg()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvElement;
        private final ImageView ivElement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvElement = itemView.findViewById(R.id.textViewElement);
            ivElement = itemView.findViewById(R.id.imageView);

        }
    }
}
