package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    List<Activity2Model> modellist = new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initViews();
        prepareMemerList();
        refreshAdapter(modellist);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity2.this, 1));

    }


    private void refreshAdapter(List<Activity2Model> modellist) {

        Activity2Adapter adapter = new Activity2Adapter(this, modellist);
        recyclerView.setAdapter(adapter);
    }

    private List prepareMemerList() {
        modellist.add(new Activity2Model("Kurbanov", "HXrETVPKWh0"));
        modellist.add(new Activity2Model("Kurbanov", "X3tr5ax78V4"));
        modellist.add(new Activity2Model("Kurbanov", "741"));
        return modellist;
    }
}