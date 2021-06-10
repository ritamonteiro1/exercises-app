package com.example.leal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leal.R;
import com.example.leal.domains.Training;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingListViewHolder> {
    private List<Training> trainingList;
    private Context context;

    public TrainingListAdapter(List<Training> trainingList, Context context) {
        this.trainingList = trainingList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TrainingListAdapter.TrainingListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training,
                parent, false);
        return new TrainingListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrainingListAdapter.TrainingListViewHolder holder, int position) {
        holder.bind(trainingList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return trainingList.size();
    }

    public static class TrainingListViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemTrainingEditImageView, itemTrainingDeleteImageView;
        private TextView itemTrainingIdTextView, itemTrainingDescriptionTextView;

        public TrainingListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemTrainingEditImageView = itemView.findViewById(R.id.itemTrainingEditImageView);
            itemTrainingDeleteImageView = itemView.findViewById(R.id.itemTrainingDeleteImageView);
            itemTrainingIdTextView = itemView.findViewById(R.id.itemTrainingIdTextView);
            itemTrainingDescriptionTextView = itemView.findViewById(R.id.itemTrainingDescriptionTextView);
        }

        public void bind(Training training, Context context) {
        }
    }
}