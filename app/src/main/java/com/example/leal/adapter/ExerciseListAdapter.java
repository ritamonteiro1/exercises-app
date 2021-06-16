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
import com.example.leal.activity.ExerciseListActivity;
import com.example.leal.click.listener.OnExerciseDeleteClickListener;
import com.example.leal.constants.Constants;
import com.example.leal.domains.Exercise;
import com.example.leal.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {
    private List<Exercise> exerciseList;
    private Context context;
    private final OnExerciseDeleteClickListener onDeleteClickListener;

    public ExerciseListAdapter(List<Exercise> exerciseList, Context context,
                               OnExerciseDeleteClickListener onDeleteClickListener) {
        this.exerciseList = exerciseList;
        this.context = context;
        this.onDeleteClickListener = onDeleteClickListener;
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
        holder.bind(exerciseList.get(position), context, onDeleteClickListener);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseListViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemExerciseImageView, itemExerciseEditImageView,
                itemExerciseDeleteImageView;
        private TextView itemExerciseIdTextView, itemExerciseDescriptionTextView;

        public ExerciseListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemExerciseImageView = itemView.findViewById(R.id.itemExerciseImageView);
            itemExerciseEditImageView = itemView.findViewById(R.id.itemExerciseEditImageView);
            itemExerciseDeleteImageView = itemView.findViewById(R.id.itemExerciseDeleteImageView);
            itemExerciseIdTextView = itemView.findViewById(R.id.itemTrainingIdTextView);
            itemExerciseDescriptionTextView =
                    itemView.findViewById(R.id.itemTrainingDescriptionTextView);
        }

        public void bind(Exercise exercise, Context context,
                         OnExerciseDeleteClickListener onDeleteClickListener) {
            itemExerciseEditImageView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditTrainingActivity.class);
                intent.putExtra(Constants.EXERCISE_OBSERVATION, exercise);
                context.startActivity(intent);
            });
            itemExerciseDeleteImageView.setOnClickListener(v ->
                    onDeleteClickListener.onClick(exercise)
            );
            itemExerciseDescriptionTextView.setText(exercise.getObservation());
            itemExerciseIdTextView.setText(exercise.getId());
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ExerciseListActivity.class);
                intent.putExtra(Constants.EXERCISE_DETAILS, String.valueOf(exercise.getId()));
                context.startActivity(intent);
            });
        }
    }
}