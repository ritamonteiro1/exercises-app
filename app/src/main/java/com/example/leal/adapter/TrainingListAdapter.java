package com.example.leal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leal.R;
import com.example.leal.activity.EditTrainingActivity;
import com.example.leal.click.listener.OnTrainingDeleteClickListener;
import com.example.leal.constants.Constants;
import com.example.leal.domains.Training;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingListViewHolder> {
    private List<Training> trainingList;
    private Context context;
    private OnTrainingDeleteClickListener onDeleteClickListener;

    public TrainingListAdapter(List<Training> trainingList, Context context,
                               OnTrainingDeleteClickListener onDeleteClickListener) {
        this.trainingList = trainingList;
        this.context = context;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public TrainingListAdapter.TrainingListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training,
                parent, false
        );
        return new TrainingListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrainingListAdapter.TrainingListViewHolder holder, int position) {
        holder.bind(trainingList.get(position), context, onDeleteClickListener);
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
            itemTrainingIdTextView = itemView.findViewById(R.id.itemExerciseIdTextView);
            itemTrainingDescriptionTextView =
                    itemView.findViewById(R.id.itemExerciseDescriptionTextView);
        }

        public void bind(Training training, Context context,
                         OnTrainingDeleteClickListener onDeleteClickListener) {
            itemTrainingIdTextView.setText(training.getId().toString());
            itemTrainingDescriptionTextView.setText(training.getDescription());
            itemTrainingDeleteImageView.setOnClickListener(v ->
                    onDeleteClickListener.onClick(training)
            );
            itemTrainingEditImageView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditTrainingActivity.class);
                intent.putExtra(Constants.TRAINING_DETAILS, training.getDescription());
                context.startActivity(intent);
            });
        }
    }
}