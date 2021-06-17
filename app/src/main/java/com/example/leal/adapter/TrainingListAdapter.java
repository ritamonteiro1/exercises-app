package com.example.leal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leal.R;
import com.example.leal.click.listener.OnTrainingDeleteClickListener;
import com.example.leal.click.listener.OnTrainingEditClickListener;
import com.example.leal.click.listener.OnTrainingItemClickListener;
import com.example.leal.domains.TrainingResponse;
import com.example.leal.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingListViewHolder> {
    private final List<TrainingResponse> trainingResponseList;
    private final Context context;
    private final OnTrainingDeleteClickListener onDeleteClickListener;
    private final OnTrainingEditClickListener onEditClickListener;
    private final OnTrainingItemClickListener onItemClickListener;

    public TrainingListAdapter(List<TrainingResponse> trainingResponseList, Context context,
                               OnTrainingDeleteClickListener onDeleteClickListener,
                               OnTrainingEditClickListener onEditClickListener,
                               OnTrainingItemClickListener onItemClickListener) {
        this.trainingResponseList = trainingResponseList;
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
        holder.bind(
                trainingResponseList.get(position), onDeleteClickListener,
                onEditClickListener, onItemClickListener
        );
    }

    @Override
    public int getItemCount() {
        return trainingResponseList.size();
    }

    public static class TrainingListViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton itemTrainingEditImageButton;
        private final ImageButton itemTrainingDeleteImageButton;
        private final TextView itemTrainingIdTextView;
        private final TextView itemTrainingDescriptionTextView;
        private final TextView itemTrainingDateFormatTextView;

        public TrainingListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTrainingEditImageButton = itemView.findViewById(R.id.itemTrainingEditImageButton);
            itemTrainingDeleteImageButton = itemView.findViewById(R.id.itemTrainingDeleteImageButton);
            itemTrainingIdTextView = itemView.findViewById(R.id.itemTrainingIdTextView);
            itemTrainingDescriptionTextView =
                    itemView.findViewById(R.id.itemTrainingDescriptionTextView);
            itemTrainingDateFormatTextView =
                    itemView.findViewById(R.id.itemTrainingDateFormatTextView);
        }

        public void bind(TrainingResponse trainingResponse,
                         OnTrainingDeleteClickListener onDeleteClickListener,
                         OnTrainingEditClickListener onEditClickListener,
                         OnTrainingItemClickListener onItemClickListener) {
            itemTrainingIdTextView.setText(String.valueOf(trainingResponse.getId()));
            itemTrainingDescriptionTextView.setText(trainingResponse.getDescription());
            itemTrainingDeleteImageButton.setOnClickListener(v -> onDeleteClickListener.onClick(trainingResponse));
            itemTrainingEditImageButton.setOnClickListener(v -> onEditClickListener.onClick(trainingResponse.getDocumentId()));
            itemView.setOnClickListener(v -> onItemClickListener.onClick((String.valueOf(trainingResponse.getId())), trainingResponse.getDocumentId()));
           // itemTrainingDateFormatTextView.setText(Utils.convertTimestampToString(trainingResponse.getDate()));
        }
    }
}