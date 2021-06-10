package com.example.leal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.leal.R;
import com.example.leal.adapter.TrainingListAdapter;

public class TrainingListActivity extends AppCompatActivity {
    private RecyclerView trainingRecyclerView;
    private Button trainingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        findViewsById();
       // setupRecyclerView(trainingListAdapter);
    }

//    private void setupRecyclerView(TrainingListAdapter trainingListAdapter) {
//        trainingRecyclerView.setAdapter(trainingListAdapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false);
//        trainingRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
//,
//        trainingRecyclerView.setLayoutManager(layoutManager);
//    }

    private void findViewsById() {
        trainingRecyclerView = findViewById(R.id.trainingRecyclerView);
        trainingButton = findViewById(R.id.trainingButton);
    }
}