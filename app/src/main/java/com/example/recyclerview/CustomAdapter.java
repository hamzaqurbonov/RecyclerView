package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder> {

    MainActivity mainActivity;
    private RecyclerViewClickListner listner;
    public List<Model> modellist;


    public CustomAdapter(MainActivity activity, List<Model> modellist, RecyclerViewClickListner listner) {

//        this.options2 = options2;
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

//        holder.fist_name.setText(model.getTitle());
//
//        holder.itemView.setText(model.getTitle());
//
//        Model member = options2.get(position);
//        if(holder instanceof CustomViewHolder){
//
//            TextView fist_name = ((CustomViewHolder) holder).first_name;
//            TextView last_name = ((CustomViewHolder) holder).last_name;
//
//            fist_name.setText(member.getIdUrl());
//            last_name.setText(member.getIdUrl());
//        }

    }

//    @Override
//    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Model model) {
////        holder.itemView.findViewById(R.id.first_name);
//
//    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View view;
        TextView Url, last_name;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
//            view = v;

            Url = itemView.findViewById(R.id.first_name);
            last_name = itemView.findViewById(R.id.last_name);
            itemView.setOnClickListener(this);
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