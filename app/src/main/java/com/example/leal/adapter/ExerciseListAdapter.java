package com.example.leal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.leal.R;
import com.example.leal.click.listener.OnExerciseDeleteClickListener;
import com.example.leal.click.listener.OnExerciseEditClickListener;
import com.example.leal.constants.Constants;
import com.example.leal.domains.ExerciseRequest;
import com.example.leal.domains.ExerciseType;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {
    private final List<ExerciseRequest> exerciseRequestList;
    private final Context context;
    private final OnExerciseDeleteClickListener onDeleteClickListener;
    private final OnExerciseEditClickListener onEditClickListener;

    public ExerciseListAdapter(List<ExerciseRequest> exerciseRequestList, Context context,
                               OnExerciseDeleteClickListener onDeleteClickListener,
                               OnExerciseEditClickListener onEditClickListener) {
        this.exerciseRequestList = exerciseRequestList;
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
        holder.bind(
                exerciseRequestList.get(position), context, onDeleteClickListener,
                onEditClickListener
        );
    }

    @Override
    public int getItemCount() {
        return exerciseRequestList.size();
    }

    public static class ExerciseListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemExerciseImageView;
        private final ImageButton itemExerciseEditImageButton;
        private final ImageButton itemExerciseDeleteImageButton;
        private final TextView itemExerciseIdTextView;
        private final TextView itemExerciseDescriptionTextView;
        private final TextView itemExerciseTypeTextView;

        public ExerciseListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemExerciseImageView = itemView.findViewById(R.id.itemExerciseImageView);
            itemExerciseEditImageButton = itemView.findViewById(R.id.itemExerciseEditImageButton);
            itemExerciseDeleteImageButton =
                    itemView.findViewById(R.id.itemExerciseDeleteImageButton);
            itemExerciseIdTextView = itemView.findViewById(R.id.itemExerciseIdTextView);
            itemExerciseDescriptionTextView =
                    itemView.findViewById(R.id.itemExerciseDescriptionTextView);
            itemExerciseTypeTextView = itemView.findViewById(R.id.itemExerciseTypeTextView);
        }

        public void bind(ExerciseRequest exerciseRequest, Context context,
                         OnExerciseDeleteClickListener onDeleteClickListener,
                         OnExerciseEditClickListener onEditClickListener) {
            itemExerciseEditImageButton.setOnClickListener(v -> onEditClickListener.onClick(exerciseRequest.getDocumentId()));
            itemExerciseDeleteImageButton.setOnClickListener(v -> onDeleteClickListener.onClick(exerciseRequest));
            itemExerciseDescriptionTextView.setText(exerciseRequest.getObservation());
            itemExerciseIdTextView.setText(String.valueOf(exerciseRequest.getId()));
            itemExerciseTypeTextView.setText(ExerciseType.toStringExerciseType(exerciseRequest.getType()));
            if (exerciseRequest.getType().getDescription().equals(ExerciseType.AEROBIC.getDescription())) {
                Glide.with(context).load(Constants.AEROBIC_PHOTO_URL_FROM_STORAGE).into(itemExerciseImageView);
            } else {
                Glide.with(context).load(Constants.BODYBUILDING_PHOTO_URL_FROM_STORAGE).into(itemExerciseImageView);
            }
        }
    }
}