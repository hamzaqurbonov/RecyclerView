package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Activity2Model> modellist = new ArrayList<>();
    static List<String> modellist2 = new ArrayList<>();
    static List<String> activityllist = new ArrayList<>();
    private RecyclerView recyclerView;

    TextView nameText;
    String n;

    @Override
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
                        List<String> list = (ArrayList<String>) document.get("tagm");
                        activityllist = (List<String>) document.get("tagm");

                        Log.d("demo16", "1 " + activityllist.toString());
                        Log.d("demo16", "3 " + activityllist.size());
//                        activityllist.add(String.valueOf(list));

                    }
                }
            }

        });




        nameText.setText(model);
//        Log.d("demo16", "2 " + activityllist.toString());
//        Log.d("demo16", String.valueOf(activityllist.size()));

        initViews();
        prepareMemerList();
        refreshAdapter(activityllist);
    }


    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity2.this, 1));

    }


    private void refreshAdapter(List<String> activityllist) {

        Activity2Adapter adapter = new Activity2Adapter(this, activityllist);
        recyclerView.setAdapter(adapter);
    }

    private List prepareMemerList() {
        modellist.add(new Activity2Model("Kurbanov", "HXrETVPKWh0"));
        modellist.add(new Activity2Model("Kurbanov", "X3tr5ax78V4"));
        modellist.add(new Activity2Model("Kurbanov", "741"));
        return modellist;
    }
}