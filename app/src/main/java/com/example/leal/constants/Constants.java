package com.example.leal.constants;

import java.util.Locale;

public class Constants {
    public static final String EMPTY = "";
    public static final String BLANK_SPACE = " ";
    public static final String LOGGED_USER_EMAIL = "user";
    public static final String USERS_COLLECTION_PATH = "users";
    public static final String TRAINING_LIST_COLLECTION_PATH = "traininglist";
    public static final String EXERCISE_LIST_COLLECTION_PATH = "exerciselist";
    public static final String TRAINING_DOCUMENT_ID = "training document id";
    public static final String DESCRIPTION_FIELD_TRAINING_LIST = "description";
    public static final String OBSERVATION_FIELD_EXERCISE_LIST = "observation";
    public static final String EXERCISE_DOCUMENT_ID = "exercise document id";
    public static final String EXERCISE_LIST_FIELD_TRAINING_LIST = "exerciselist";
    public static final String TRAINING_NUMBER_ID = "training number id";
    public static final String TRAINING_TIMESTAMP = "date";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final Locale LOCALE_BR = new Locale("pt", "BR");
    public static final String AEROBIC_PHOTO_URL_FROM_STORAGE = "https://firebasestorage" +
            ".googleapis.com/v0/b/leal-app-firebase.appspot.com/o/images%2Faerobic" +
            ".jpg?alt=media&token=6a0cfda4-64a6-4879-8cd9-e7dc58c44019";
    public static final String BODYBUILDING_PHOTO_URL_FROM_STORAGE = "https://firebasestorage" +
            ".googleapis.com/v0/b/leal-app-firebase.appspot.com/o/images%2Fbodybuilding" +
            ".jpg?alt=media&token=1c2edfed-3b68-4164-af20-afc4de5a2895";
}
