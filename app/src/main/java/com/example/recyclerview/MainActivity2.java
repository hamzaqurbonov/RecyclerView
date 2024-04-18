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
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {


    private Activity2Adapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    TextView nameText;
    TextView firstname;
    List<String> activityllist = new ArrayList<>();



//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nameText = findViewById(R.id.nameText);
        firstname = findViewById(R.id.first_name2);
        firstname.setText(getIntent().getExtras().getString("title"));

        String model = getIntent().getExtras().getString("id");

        db.collection("Notebook2").document(model).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        List<String> list = (ArrayList<String>) document.get("tagm");
                        activityllist = (List<String>) document.get("tagm");

                        initViews();
                        setOnClickListner();
                        refreshAdapter(activityllist);

                        Map<String, Object> map = document.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("idUrl")) {
                                Log.d("demo22", entry.getValue().toString());
                            }
                            if (entry.getKey().equals("tagm")) {
                                Log.d("demo22", entry.getValue().toString());
                            }
                        }
                    }
                }
            }

        });

        nameText.setText(model);
//        firstname.setText(model);

//        initViews();
//        setOnClickListner();
//        refreshAdapter(activityllist);
    }


    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity2.this, 1));
//        Log.d("demo22", activityllist.toString());
    }


    private void refreshAdapter(List<String> activityllist) {

        Activity2Adapter adapter = new Activity2Adapter(this, activityllist, listner);
        recyclerView.setAdapter(adapter);
    }


    private void setOnClickListner() {

        listner = new Activity2Adapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                Toast.makeText(MainActivity2.this,  "MainActivity2", Toast.LENGTH_SHORT).show();
//                intent.putExtra( "Kurbanov",modellist .get(position).getLastName());
                intent.putExtra("id", getIntent().getExtras().getString("id"));
                startActivity(intent);
            }

        };

    }


}