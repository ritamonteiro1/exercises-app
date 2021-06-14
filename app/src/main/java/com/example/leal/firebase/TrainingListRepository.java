package com.example.leal.firebase;



import com.example.leal.constants.Constants;
import com.example.leal.domains.Training;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;



import java.util.ArrayList;
import java.util.List;

public class TrainingListRepository {
    public static List<Training> getTrainingListFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Training> trainingList = new ArrayList<>();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(Constants.EMAIL_DOCUMENT_PATH)
                .collection(Constants.EMAIL_DOCUMENT_PATH)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            trainingList.add(new Training(document.hashCode(),
                                    document.getString("description"),
                                    document.getLong("data")));
                        }
                    }
                }).addOnFailureListener(e -> {

                });
        return trainingList;
    }
}
