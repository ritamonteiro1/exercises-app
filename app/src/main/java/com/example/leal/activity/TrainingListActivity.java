package com.example.leal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.leal.R;

public class TrainingListActivity extends AppCompatActivity {
    private RecyclerView trainingListRecyclerView;
    private Button trainingListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        findViewsById();
      //  setupRecyclerView(trainingListAdapter);
    }

//    private void setupRecyclerView(TrainingListAdapter trainingListAdapter) {
//        trainingListRecyclerView.setAdapter(trainingListAdapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false);
//        trainingListRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
//,
//        trainingListRecyclerView.setLayoutManager(layoutManager);
//    }

    private void findViewsById() {
        trainingListRecyclerView = findViewById(R.id.trainingListRecyclerView);
        trainingListButton = findViewById(R.id.trainingListButton);
    }
}