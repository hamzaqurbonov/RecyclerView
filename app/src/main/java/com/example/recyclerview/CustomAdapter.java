package com.example.recyclerview;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<java.lang.reflect.Member> members;

    public CustomAdapter (Context context, List<java.lang.reflect.Member> members) {
        this.context=context;
        this.members=members;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_layout, parent, false);
        return new CustomViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Member member = (Member) members.get(position);

        if(holder instanceof CustomViewHolder){
            TextView fist_name = ((CustomViewHolder) holder).first_name;
            TextView last_name = ((CustomViewHolder) holder).last_name;

            fist_name.setText(member.getFirstName());
            last_name.setText(member.getLastName());
        }

    }



    @Override
    public int getItemCount() {
        return members.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView first_name, last_name;
        public CustomViewHolder(View v) {
            super(v);
            view = v;
            first_name = view.findViewById(R.id.first_name);
            last_name = view.findViewById(R.id.last_name);
        }
    }
}
