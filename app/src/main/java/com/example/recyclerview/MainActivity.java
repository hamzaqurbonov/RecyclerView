package com.example.recyclerview;


import static android.graphics.Insets.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LongAdapter adapter;
    private RecyclerView recyclerView;
//    List<Model> modellist = new ArrayList<>();
//    private RecyclerView recyclerView;
//    private CustomAdapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hadRef = db.collection("Notebook2");

    public static   ArrayList<String> list2 =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initViews();
//        prepareMemerList();
//        setOnClickListner();
//        refreshAdapter(modellist);

        recyclerView = findViewById(R.id.recyclerView);
        setUpRecyclerView();


    }


    private void setUpRecyclerView() {

        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<LongModel> options = new FirestoreRecyclerOptions.Builder<LongModel>().setQuery(query, LongModel.class).build();

        adapter = new LongAdapter(options);

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3  ));
        recyclerView.setAdapter(adapter);



        adapter.setItemClickListner(new LongAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(MainActivity.this,  position + " " + id , Toast.LENGTH_SHORT).show();

                String chapterName = adapter.getItem(position).getTitle();
                String getIdUrl = adapter.getItem(position).getIdUrl();
//                String getImageUrl = adapter.getItem(position).getImageUrl();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("id", id);
                intent.putExtra("title", chapterName);
                intent.putExtra("idUrl", getIdUrl);
//                intent.putExtra("imageUrl", getImageUrl);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }






















//    private void initViews() {
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
//
//    }
//
//
//    private void refreshAdapter(List<Model> modellist) {
////        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);
////
////        FirestoreRecyclerOptions<Model> options2 = new FirestoreRecyclerOptions.Builder<Model>().setQuery(query, Model.class).build();
//
//        CustomAdapter adapter = new CustomAdapter(this, modellist, listner);
//        recyclerView.setAdapter(adapter);
//    }
//
//    private List prepareMemerList() {
//        modellist.add(new Model("Kurbanov", "HXrETVPKWh0"));
//        modellist.add(new Model("Kurbanov", "X3tr5ax78V4"));
//        modellist.add(new Model("Kurbanov", "741"));
//        return modellist;
//    }
//
//    private void setOnClickListner() {
//
//
//        listner = new CustomAdapter.RecyclerViewClickListner() {
//            @Override
//            public void onClick(View v, int position) {
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
////                intent.putExtra( "Kurbanov",modellist .get(position).getLastName());
//                Log.d("demo12", String.valueOf(list2));
//
//                intent.putExtra("title", list2.toString());
//                Toast.makeText(MainActivity.this, "ID " + position , Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//            }
//
//        };
//
//    }





}
