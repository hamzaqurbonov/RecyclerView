package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {


    private Activity2Adapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    TextView nameText;
    static List<String> activityllist = new ArrayList<>();




//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nameText = findViewById(R.id.nameText);
//        nameText.setText(getIntent().getExtras().getString("title"));

        String model = getIntent().getExtras().getString("id");

        db.collection("Notebook2").document(model).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        List<String> list = (ArrayList<String>) document.get("tagm");
                        activityllist = (List<String>) document.get("tagm");

                    }
                }
            }

        });

        nameText.setText(model);

        initViews();
        setOnClickListner();
        refreshAdapter(activityllist);
    }


    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity2.this, 1));

    }


    private void refreshAdapter(List<String> activityllist) {

        Activity2Adapter adapter = new Activity2Adapter(this, activityllist, listner);
        recyclerView.setAdapter(adapter);
    }


    private void setOnClickListner() {

        listner = new Activity2Adapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                Toast.makeText(MainActivity2.this,  "1", Toast.LENGTH_SHORT).show();
//                intent.putExtra( "Kurbanov",modellist .get(position).getLastName());
                startActivity(intent);
            }

        };

    }


}