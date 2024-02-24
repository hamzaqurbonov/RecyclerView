package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private Activity3Adapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView3;
    TextView nameText;
    static List<String> activityllist = new ArrayList<>();
    TextView nameText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        nameText3 = findViewById(R.id.nameText3);


        String model = getIntent().getExtras().getString("id");

        nameText3.setText(model);
        db.collection("Notebook2").document(model)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> list = (ArrayList<String>) document.get("tagm");
                        activityllist = (List<String>) document.get("tagm");


                        initViews();
                        setOnClickListner();
                        refreshAdapter(activityllist);

                    }
                }
            }

        });
//


//        initViews();
//        setOnClickListner();
//        refreshAdapter(activityllist);
    }


    private void initViews() {
        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new GridLayoutManager(MainActivity3.this, 1));

    }


    private void refreshAdapter(List<String> activityllist) {

        Activity3Adapter adapter = new Activity3Adapter(MainActivity3.this, activityllist, listner);
        recyclerView3.setAdapter(adapter);
    }


    private void setOnClickListner() {

        listner = new Activity3Adapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                Toast.makeText(MainActivity3.this, "MainActivity3", Toast.LENGTH_SHORT).show();
//                intent.putExtra( "Kurbanov",modellist .get(position).getLastName());
                intent.putExtra("id", getIntent().getExtras().getString("id"));
                startActivity(intent);
            }

        };

    }
}