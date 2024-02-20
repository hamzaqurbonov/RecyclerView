package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Activity2Adapter extends RecyclerView.Adapter< RecyclerView.ViewHolder> {


    MainActivity2 activity2;
    List<Activity2Model> modellist ;
    public ArrayList<String> list2;

    public Activity2Adapter(MainActivity2 activity2, List<Activity2Model> modellist) {
        this.modellist = modellist;
        this.activity2 = activity2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity2_item, parent, false);
        return new Activity2Adapter.Activity2AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Activity2AdapterViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView Url, last_name;

        public Activity2AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
//            view = v;

            Url = itemView.findViewById(R.id.first_2);
            last_name = itemView.findViewById(R.id.last_2);

        }


    }
}
