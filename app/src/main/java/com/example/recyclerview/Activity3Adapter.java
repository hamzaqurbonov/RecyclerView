package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Activity3Adapter extends  RecyclerView.Adapter< RecyclerView.ViewHolder> {


private RecyclerViewClickListner listner;
        MainActivity3 activity3;
        List<String> activityllist ;


public Activity3Adapter(MainActivity3 activity3, List<String> activityllist, RecyclerViewClickListner listner) {
        this.activityllist = activityllist;
        this.activity3 = activity3;
        this.listner = listner;
        }


@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity2_item, parent, false);

        return new Activity3Adapter.Activity3AdapterViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView Url= ((Activity3AdapterViewHolder) holder).last_name;
        Url.setText(activity3.activityllist.get(position));

        }

@Override
public int getItemCount() {
        int dd =  activity3.activityllist.size();
        return dd;
        }



public class Activity3AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View view;
    TextView Url, last_name;

    public Activity3AdapterViewHolder(View v) {
        super(v);
        view = v;

        Url = view.findViewById(R.id.first_2);
        last_name = view.findViewById(R.id.last_2);
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