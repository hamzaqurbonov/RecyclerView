package com.example.recyclerview;

public class NoteModel {

        private String title;
        private String description;
        private String priority;

        public NoteModel() {
            //empty constructor needed
        }

        public NoteModel(String title, String description, String priority) {
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

        public String getPriority() {
            return priority;
        }
}
