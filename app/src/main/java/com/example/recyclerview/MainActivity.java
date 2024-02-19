package com.example.recyclerview;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    List<Model> modellist = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hadRef = db.collection("Notebook2");

//    ArrayList<String> list =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        prepareMemerList();
        setOnClickListner();
//        Activity2RecyclerView();
        refreshAdapter(modellist);

        db.collection("Notebook2").document("zlenq8wcvT1bb1lKsIfV").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<String> list = (ArrayList<String>) document.get("tagm");
                        Log.d("demo12", list.toString());
                    }
                }
            }
        });



    }
//    private void Activity2RecyclerView() {
//        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);
//
//        FirestoreRecyclerOptions<Model> options2 = new FirestoreRecyclerOptions.Builder<Model>().setQuery(query, Model.class).build();
//
//        adapter = new Activity2Adapter(options2);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerView.setAdapter(adapter);
//    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
    }


    private void refreshAdapter(List<Model> modellist) {
//        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);
//
//        FirestoreRecyclerOptions<Model> options2 = new FirestoreRecyclerOptions.Builder<Model>().setQuery(query, Model.class).build();

        CustomAdapter adapter = new CustomAdapter(this, listner);
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

                Toast.makeText(MainActivity.this, "ID " + position , Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

        };

    }


}
