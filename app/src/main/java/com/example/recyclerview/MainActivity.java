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
    private EditText courseNameEdt, courseTracksEdt, courseDurationEdt, courseDescriptionEdt, idTestEdit;
    private Button addCourseBtn, readCourseBtn, shareSend;
    private DBHandler dbHandler;

    static List<String> activityllist1 = new ArrayList<>();
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


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");


//        courseNameEdt = findViewById(R.id.idEdtCourseName);
//        courseTracksEdt = findViewById(R.id.idEdtCourseTracks);
//        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
        idTestEdit = findViewById(R.id.idTest);
//        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        readCourseBtn = findViewById(R.id.idBtnReadCourse);

        shareSend = findViewById(R.id.share_send);
        shareSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Hello");
                intent.setType("text/plain");
                if(intent.resolveActivity(getPackageManager()) !=null){
                    startActivity(intent);
                }
            }
        });



        dbHandler = new DBHandler(MainActivity.this);

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LongModel longModel = new LongModel();

                String model = "model";
                // below line is to get data from all edit text fields.
//                String courseName = model;
//                String courseTracks = courseTracksEdt.getText().toString();
//                String courseDuration = courseDurationEdt.getText().toString();
                String courseTest = idTestEdit.getText().toString();
//                String courseDescription = courseDescriptionEdt.getText().toString();

                // validating if the text fields are empty or not.
                if (courseTest.isEmpty() ) {
                    Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewCourse(courseTest);
                dbHandler.addNewCourse(courseTest);

                // after adding the data we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();
//                courseNameEdt.setText("");
//                courseDurationEdt.setText("");
//                courseTracksEdt.setText("");
                idTestEdit.setText("");
//                courseDescriptionEdt.setText("");
            }
        });

        readCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via a intent.
                Intent i = new Intent(MainActivity.this, ViewCourses.class);
                startActivity(i);
            }
        });

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");

//        setSupportActionBar(findViewById(R.id.toolbar));
//        Toolbar toolbar = findViewById(R.id.toolbar);

//        setSupportActionBar(findViewById(R.id.toolbar));
////        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
        Log.d("demo3", "mysearch: 3");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

//        initViews();
//        prepareMemerList();
//        setOnClickListner();
//        refreshAdapter(modellist);

        recyclerView = findViewById(R.id.recyclerviewId);
        setUpRecyclerView();


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









    private void setUpRecyclerView() {

        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<LongModel> options = new FirestoreRecyclerOptions.Builder<LongModel>().setQuery(query, LongModel.class).build();

        adapter = new LongAdapter(options);

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1  ));
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
                intent.putExtra("id", getIdUrl);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem menuItem = menu.findItem(R.id.searchId);
        SearchView searchView = (SearchView) menuItem.getActionView();


        searchView.setQueryHint("Search 123");
        Log.d("demo3", "mysearch: 1");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                mysearch(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

//                recyclerView.getFilterTouchesWhenObscured();
                mysearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void mysearch(String str) {
//        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);
//
        Log.d("demo3", "mysearch: 2");
//
//        FirestoreRecyclerOptions<LongModel> options =
//                new FirestoreRecyclerOptions.Builder<LongModel>()
//                        .setQuery( query, LongModel.class).build();
//        hadRef.orderBy("idUrl") .startAt(str).endAt(str+"\uf8ff")

        Query query = hadRef.orderBy("title", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<LongModel> options =
                new FirestoreRecyclerOptions.Builder<LongModel>()
                .setQuery(query, LongModel.class).build();

        adapter = new LongAdapter(options);

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1  ));
        recyclerView.setAdapter(adapter);
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
