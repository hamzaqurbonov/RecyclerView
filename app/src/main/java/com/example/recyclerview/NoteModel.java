package com.example.recyclerview;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteModel {

        private String title;
        private String description;
        private String priority;
        int position;
        FirestoreRecyclerOptions<NoteModel> options;

        public NoteModel() {
            //empty constructor needed
        }

        public NoteModel(String title, String description, String priority, int position) {
            this.title = title;
            this.description = description;
            this.priority = priority;
            this.position = position;
        }

    public NoteModel(FirestoreRecyclerOptions<NoteModel> options) {
        this.options = options;
    }

//    public static int getPosition(int position) {
//
//        return position;
//    }

    public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getPriority() {
            return priority;
        }

   }
