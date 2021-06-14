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
import com.example.leal.constants.Constants;
import com.example.leal.domains.Exercise;
import com.example.leal.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {
    private List<Exercise> exerciseList;
    private Context context;

    public ExerciseListAdapter(List<Exercise> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ExerciseListAdapter.ExerciseListViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise,
                parent, false);
        return new ExerciseListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ExerciseListAdapter.ExerciseListViewHolder holder, int position) {
        holder.bind(exerciseList.get(position), context);
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
            itemExerciseIdTextView = itemView.findViewById(R.id.itemExerciseIdTextView);
            itemExerciseDescriptionTextView =
                    itemView.findViewById(R.id.itemExerciseDescriptionTextView);
        }

        public void bind(Exercise exercise, Context context) {
            itemExerciseEditImageView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditTrainingActivity.class);
                intent.putExtra(Constants.EXERCISE_OBSERVATION, exercise.getObservation());
                context.startActivity(intent);
            });
            itemExerciseDeleteImageView.setOnClickListener(v -> Utils.createErrorDialogWithNegativeButton(context.getString(R.string.item_exercise_delete_message), context));
            itemExerciseDescriptionTextView.setText(exercise.getObservation());
            itemExerciseIdTextView.setText(exercise.getId());
        }
    }
}
