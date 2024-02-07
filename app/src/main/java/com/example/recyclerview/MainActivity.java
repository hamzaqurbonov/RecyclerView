package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private NoteAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteDB = db.document("main/short");

    private CollectionReference  notebookRef = db.collection("Notebook");
    private static final String KEY_TITLE = "shortObj";

    TextView dbText;
//    FirestoreRecyclerOptions<NoteModel> options;

//    YouTubePlayerView youTubePlayerView;
//    List<Model> modellist = new ArrayList<>();
//
//    private Context context;
//    private RecyclerView recyclerView;
    private NoteAdapter.RecyclerViewClickListner listner;
//    Button playNextVideoButton;
//    TextView dbText;
//    private CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        youTubePlayerView = findViewById(R.id.youtube_player_view);
        dbText = findViewById(R.id.db_text);

//        getLifecycle().addObserver(youTubePlayerView);
//        initYouTubePlayerView();

//            initViews();
//            List<Model> modellist = prepareMemerList();
//            refreshAdapter(modellist);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");

//        getData();

        setUpRecyclerView();
    }



    private void setUpRecyclerView() {
        Query query = notebookRef.orderBy("titles", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<NoteModel> options = new FirestoreRecyclerOptions.Builder<NoteModel>()
                .setQuery(query, NoteModel.class)
                .build();

        setOnClickListner();
        adapter = new NoteAdapter(options, listner);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Log.d("demo21", options.toString());
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void setOnClickListner() {
        listner = new NoteAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
//                intent.putExtra( "titles", NoteModel.getPosition(position));
//                intent.putExtra( "Kurbanov",modellist .get(position).getLastName());
                startActivity(intent);
            }

        };

    }


    public void loadDB (View v) {
        noteDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String title = documentSnapshot.getString(KEY_TITLE);
                    dbText.setText("URL: " + title);
                } else {
                    Toast.makeText(MainActivity.this, "short", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }




//
//
//        private void initViews() {
//            context = this;
//            recyclerView = findViewById(R.id.recyclerView);
//            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
//
//        }
//
//    private void refreshAdapter (List<Model>modellist) {
//        setOnClickListner();
//        CustomAdapter adapter = new CustomAdapter(this, modellist,listner);
//        recyclerView.setAdapter(adapter);
//    }
//


//

//
//    private List<Model> prepareMemerList() {
//
//        modellist.add(new Model("Kurbanov", "HXrETVPKWh0"));
//        modellist.add(new Model("Kurbanov", "X3tr5ax78V4"));
//        modellist.add(new Model("Kurbanov", "k_an7b4r1_Q"));
//
////        for (int i = 0; i<=5; i++) {
////            modellist.add(new Model("Kurbanov " + i, "Hamza " + i));
////
////        }
//        return modellist;
//    }
















//    private void getData() {
//        FirebaseFirestore db  = FirebaseFirestore.getInstance();
//        db.collection("main")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                dbText.setText(document.getId() + document.getData());
////                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                Log.d("demo21", document.getId() + " => " + document.getData());
//                            }
//                        } else {
////                            Log.w(TAG, "Error getting documents.", task.getException());
//                            Log.d("demo21", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }



//
//
//    public void initYouTubePlayerView() {
//        getLifecycle().addObserver(youTubePlayerView);
////        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.layout_panel);
//
//        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//
////                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(requireContext(), customPlayerUi, youTubePlayer, youTubePlayerView);
////                youTubePlayer.addListener(customPlayerUiController);
//                setPlayNextVideoButtonClickListener(youTubePlayer);
//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer, getLifecycle(),
//                       "HXrETVPKWh0",
//                        0f
//                );
////                Log.d("demo21", "1");
//            }
//        };
//        // disable web ui
//        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
//        youTubePlayerView.initialize(listener, options);
//    }
//
//
//
////
//
//    private void setPlayNextVideoButtonClickListener(final YouTubePlayer youTubePlayer) {
//
////        Button playNextVideoButton = findViewById(R.id.button);
//
//
//        playNextVideoButton.setOnClickListener(view ->
//
//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer,
//                        getLifecycle(),
//                        "vBxNDtyE_Co",
//                        0f
//
//                )
//
//        );
//        Log.d("demo21", "2");
//    }
}