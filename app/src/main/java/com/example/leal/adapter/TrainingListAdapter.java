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
import com.example.leal.click.listener.OnTrainingDeleteClickListener;
import com.example.leal.click.listener.OnTrainingEditClickListener;
import com.example.leal.click.listener.OnTrainingItemClickListener;
import com.example.leal.domains.Training;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingListViewHolder> {
    private final List<Training> trainingList;
    private final Context context;
    private final OnTrainingDeleteClickListener onDeleteClickListener;
    private final OnTrainingEditClickListener onEditClickListener;
    private final OnTrainingItemClickListener onItemClickListener;

    public TrainingListAdapter(List<Training> trainingList, Context context,
                               OnTrainingDeleteClickListener onDeleteClickListener,
                               OnTrainingEditClickListener onEditClickListener,
                               OnTrainingItemClickListener onItemClickListener) {
        this.trainingList = trainingList;
        this.context = context;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onEditClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    @NotNull
    @Override
    public TrainingListAdapter.TrainingListViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training,
                parent, false
        );
        return new TrainingListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NotNull TrainingListAdapter.TrainingListViewHolder holder,
                                 int position) {
        holder.bind(trainingList.get(position), onDeleteClickListener,
                onEditClickListener, onItemClickListener
        );
    }

    @Override
    public int getItemCount() {
        return trainingList.size();
    }

    public static class TrainingListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemTrainingEditImageView;
        private final ImageView itemTrainingDeleteImageView;
        private final TextView itemTrainingIdTextView;
        private final TextView itemTrainingDescriptionTextView;
        private final TextView itemTrainingDateFormatTextView;

        public TrainingListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTrainingEditImageView = itemView.findViewById(R.id.itemTrainingEditImageView);
            itemTrainingDeleteImageView = itemView.findViewById(R.id.itemTrainingDeleteImageView);
            itemTrainingIdTextView = itemView.findViewById(R.id.itemTrainingIdTextView);
            itemTrainingDescriptionTextView =
                    itemView.findViewById(R.id.itemTrainingDescriptionTextView);
            itemTrainingDateFormatTextView =
                    itemView.findViewById(R.id.itemTrainingDateFormatTextView);
        }

        public void bind(Training training,
                         OnTrainingDeleteClickListener onDeleteClickListener,
                         OnTrainingEditClickListener onEditClickListener,
                         OnTrainingItemClickListener onItemClickListener) {
            itemTrainingIdTextView.setText(String.valueOf(training.getId()));
            itemTrainingDescriptionTextView.setText(training.getDescription());
            itemTrainingDeleteImageView.setOnClickListener(v -> onDeleteClickListener.onClick(training));
            itemTrainingEditImageView.setOnClickListener(v -> onEditClickListener.onClick(training.getDocumentId()));
            itemView.setOnClickListener(v -> onItemClickListener.onClick((String.valueOf(training.getId())), training.getDocumentId()));
            itemTrainingDateFormatTextView.setText(String.valueOf(training.getDate()));
        }
    }
}