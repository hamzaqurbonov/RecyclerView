package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerViewClickListner listner;
    public List<Model> modellist;

    public CustomAdapter(MainActivity activity, List<Model> modellist, RecyclerViewClickListner listner) {
        this.modellist=modellist;
        this.listner=listner;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity2, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Model member = modellist.get(position);
        if(holder instanceof CustomViewHolder){

            TextView fist_name = ((CustomViewHolder) holder).first_name;
            TextView last_name = ((CustomViewHolder) holder).last_name;

            fist_name.setText(member.getFirstName());
            last_name.setText(member.getLastName());
        }

    }

    @Override
    public int getItemCount() {
        return modellist.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View view;
        public TextView first_name, last_name;
        public CustomViewHolder(View v) {
            super(v);
            view = v;

            first_name = view.findViewById(R.id.first_name);
            last_name = view.findViewById(R.id.last_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.onClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListner {
        void onClick(View v, int position);
    }



}