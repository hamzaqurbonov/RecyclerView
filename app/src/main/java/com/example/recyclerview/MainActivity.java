package com.example.recyclerview;


import static android.graphics.Insets.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.SearchView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
//import androidx.appcompat.widget.SearchView;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public  static ArrayList<String> nextArrayList = new ArrayList<>();
    String oldID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
        nextArrayList();

    }


    private void nextArrayList() {
        // Birinchi holati ishlaydi Arreyni shakillantirib beradi

        db.collection("Shorts").orderBy("key").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        ArrayList<String> arrayMapList = (ArrayList<String>) document.get("key");
                        for (Object transaction: arrayMapList) {
                            Map values = (Map)transaction;
                            nextArrayList.add((String) values.get("id"));
                        }


                    }
                    Log.d("demo47", "Fragment ID: " + nextArrayList);
                    Collections.shuffle( nextArrayList);
                } else {
                }
            }
        });
        Log.d("demo47", "Found: ");
        db.collection("Shorts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                            nextArrayList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // "key" массивини оламиз
                                List<Map<String, Object>> keys = (List<Map<String, Object>>) document.get("key");

                                // Массив ичидаги youngNumber бўйича фильтрлаш
                                for (Map<String, Object> key : keys) {
//                                    long youngNumber = (long) key.get("youngNumber"); // youngNumberни оламиз

//                                    if (youngNumber ==  2) { // youngNumber 0 дан катта бўлса
//                                        Log.d("demo47", "Found: " + key.get("id")); // Мос келганларини логга чиқарамиз
//                                    }
                                    Object youngNumberObj = key.get("youngNumber");
                                    if (youngNumberObj != null && !youngNumberObj.toString().isEmpty()) {
                                        try {
                                            // Агар youngNumber String бўлса, уни long га айлантирамиз
                                            long youngNumber = Long.parseLong(youngNumberObj.toString());

                                            if (youngNumber == Integer.parseInt(oldID)) {

                                                nextArrayList.add((String) key.get("id"));
                                                Log.d("demo47", "Found: " + key.get("id"));
                                            }
                                        } catch (NumberFormatException e) {
//                                            Log.e("demo47", "Маълумотни long га айлантиришда хато: " + youngNumberObj.toString());
                                        }
                                    } else {
                                        Log.w("demo47", "youngNumber null ёки бўш, текширинг: " + key.toString());
                                    }


                                }
                            }
                            Collections.shuffle( nextArrayList);
                        } else {
                            Log.w("demo47", "Маълумот олишда хато юз берди.", task.getException());
                        }
                    }
                });

    }

}
