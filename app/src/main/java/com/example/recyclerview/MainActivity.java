package com.example.recyclerview;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Model> modellist = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapter.RecyclerViewClickListner listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        prepareMemerList();
        setOnClickListner();
        refreshAdapter(modellist);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
    }


    private void refreshAdapter(List<Model> modellist) {
        CustomAdapter adapter = new CustomAdapter(this, modellist, listner);
        recyclerView.setAdapter(adapter);
    }

    private List prepareMemerList() {
        modellist.add(new Model("Kurbanov", "HXrETVPKWh0"));
        modellist.add(new Model("Kurbanov", "X3tr5ax78V4"));
        modellist.add(new Model("Kurbanov", "741"));

//        for (int i = 0; i<=5; i++) {
//            modellist.add(new Model("Kurbanov " + i, "Hamza " + i));
//        }
        return modellist;
    }

    private void setOnClickListner() {
        listner = new CustomAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                intent.putExtra( "Kurbanov",modellist .get(position).getLastName());
                startActivity(intent);
            }

        };

    }


}
