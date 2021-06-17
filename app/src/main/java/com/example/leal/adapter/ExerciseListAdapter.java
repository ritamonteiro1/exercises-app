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
import com.example.leal.click.listener.OnExerciseDeleteClickListener;
import com.example.leal.click.listener.OnExerciseEditClickListener;
import com.example.leal.domains.Exercise;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {
    private final List<Exercise> exerciseList;
    private final Context context;
    private final OnExerciseDeleteClickListener onDeleteClickListener;
    private final OnExerciseEditClickListener onEditClickListener;

    public ExerciseListAdapter(List<Exercise> exerciseList, Context context,
                               OnExerciseDeleteClickListener onDeleteClickListener,
                               OnExerciseEditClickListener onEditClickListener) {
        this.exerciseList = exerciseList;
        this.context = context;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onEditClickListener;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ExerciseListAdapter.ExerciseListViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise,
                parent, false
        );
        return new ExerciseListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ExerciseListAdapter.ExerciseListViewHolder holder, int position) {
        holder.bind(exerciseList.get(position), onDeleteClickListener,
                onEditClickListener
        );
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemExerciseImageView;
        private final ImageView itemExerciseEditImageView;
        private final ImageView itemExerciseDeleteImageView;
        private final TextView itemExerciseIdTextView;
        private final TextView itemExerciseDescriptionTextView;

        public ExerciseListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemExerciseImageView = itemView.findViewById(R.id.itemExerciseImageView);
            itemExerciseEditImageView = itemView.findViewById(R.id.itemExerciseEditImageView);
            itemExerciseDeleteImageView = itemView.findViewById(R.id.itemExerciseDeleteImageView);
            itemExerciseIdTextView = itemView.findViewById(R.id.itemTrainingIdTextView);
            itemExerciseDescriptionTextView =
                    itemView.findViewById(R.id.itemTrainingDescriptionTextView);
        }

        public void bind(Exercise exercise,
                         OnExerciseDeleteClickListener onDeleteClickListener,
                         OnExerciseEditClickListener onEditClickListener) {
            itemExerciseEditImageView.setOnClickListener(v -> onEditClickListener.onClick(exercise.getDocumentId()));
            itemExerciseDeleteImageView.setOnClickListener(v -> onDeleteClickListener.onClick(exercise));
            itemExerciseDescriptionTextView.setText(exercise.getObservation());
            itemExerciseIdTextView.setText(String.valueOf(exercise.getId()));
        }
    }
}