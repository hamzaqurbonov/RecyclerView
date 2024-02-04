package com.example.recyclerview;

public class NoteModel {

        private String title;
        private String description;
        private int priority;

        public NoteModel() {
            //empty constructor needed
        }

        public NoteModel(String title, String description, int priority) {
            this.title = title;
            this.description = description;
            this.priority = priority;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getPriority() {
            return priority;
        }
}
