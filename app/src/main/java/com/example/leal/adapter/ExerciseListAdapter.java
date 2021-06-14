package com.example.leal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leal.R;
import com.example.leal.domains.Exercise;

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


        public ExerciseListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void bind(Exercise exercise, Context context) {

        }
    }
}
