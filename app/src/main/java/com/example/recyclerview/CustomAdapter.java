package com.example.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainActivity activity;

//    private Context context;
    private List<Model> modellist;


    public CustomAdapter(MainActivity activity, List<Model> modellist) {
//        this.context=context;
        this.modellist=modellist;
        this.activity=activity;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Model member = modellist.get(position);

        if(holder instanceof CustomViewHolder){
            LinearLayout lay_click = ((CustomViewHolder) holder).lay_click;
            TextView fist_name = ((CustomViewHolder) holder).first_name;
            TextView last_name = ((CustomViewHolder) holder).last_name;

            fist_name.setText(member.getFirstName());
            last_name.setText(member.getLastName());

            lay_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    activity.openItem(member);
//                    activity.initYouTubePlayerView();
                    Intent intent = new Intent(v.getContext(), MainActivity2.class);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return modellist.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public View view;
        private LinearLayout lay_click;
        public TextView first_name, last_name;
        public CustomViewHolder(View v) {
            super(v);
            view = v;
            lay_click = view.findViewById(R.id.lay_click);
            first_name = view.findViewById(R.id.first_name);
            last_name = view.findViewById(R.id.last_name);
        }
    }
}
