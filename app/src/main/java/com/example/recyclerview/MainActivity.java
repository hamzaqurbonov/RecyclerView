package com.example.recyclerview;


import static android.graphics.Insets.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.SearchView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
//import androidx.appcompat.widget.SearchView;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> nestedData = new HashMap<>();
    public List<String> activityllist = new ArrayList<>();
    EditText edit_short_id, add_edit_document, add_edit_collection, edit_DataSync_id;
    Button add_button, pasteButton, add_document_button, add_collection_button, Button_DataSync;
    Spinner spinner, spinner_young, spinnerDoc, spinner_collection;
    String DocName, NameSubDoc, youngNumber, videoId, url;
    private ArrayList<String> subDocList = new ArrayList<>();
    private ArrayList<String> activityList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView add_plus, add_DataSync;
    private boolean isFullscreen = false;
    private boolean isFullscreen2 = false;

    RelativeLayout relative, relative_DataSync;
    TextView textView_DataSync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        add_plus = findViewById(R.id.add_plus);
        relative = findViewById(R.id.relative);
        relative_DataSync = findViewById(R.id.relative_DataSync);

        spinner_young = findViewById(R.id.spinner_young);
        spinner = findViewById(R.id.spinner);
        spinnerDoc = findViewById(R.id.spinnerDoc);
        edit_short_id = findViewById(R.id.edit_short_id);
        add_button = findViewById(R.id.add_button);
        pasteButton = findViewById(R.id.pasteButton);

        // collection qo'shish
        add_edit_collection = findViewById(R.id.add_edit_collection);
        add_collection_button = findViewById(R.id.add_collection_button);
        // document qo'shish
        spinner_collection = findViewById(R.id.spinner_collection);
        add_edit_document = findViewById(R.id.add_edit_document);
        add_document_button = findViewById(R.id.add_document_button);

        add_plus.setOnClickListener(view -> {
            isFullscreen = !isFullscreen;
            if (isFullscreen) {
                relative.setVisibility(View.VISIBLE);
                relative_DataSync.setVisibility(View.GONE);
            } else {
                relative.setVisibility(View.GONE);
            }
        });
//        swipeRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
//            // Ensure the child is not null before calling canScrollVertically
//            if (swipeRefreshLayout.getChildCount() > 0 && swipeRefreshLayout.getChildAt(0) != null) {
//                // Your refresh logic here
//            }
//        });


//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
////                Collection();
//            }
//        });


        Collection();
        AddButton();
        spinner_young();
        pasteButton();
        AddEditCollection();
        DataSync();
    }


    private void DataSync() {
        add_DataSync = findViewById(R.id.add_DataSync);
        textView_DataSync = findViewById(R.id.textView_DataSync);
        Button_DataSync = findViewById(R.id.Button_DataSync);
        edit_DataSync_id = findViewById(R.id.edit_DataSync_id);

        add_DataSync.setOnClickListener(view -> {
            isFullscreen2 = !isFullscreen2;
            if (isFullscreen2) {
                relative.setVisibility(View.GONE);
                relative_DataSync.setVisibility(View.VISIBLE);
            } else {
                relative_DataSync.setVisibility(View.GONE);
            }
        });

        db.collection("DataSync")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("demo47", document.getId() + " => " + document.getData() + "version " + document.getData().get("version"));

                                String version = (String) document.getData().get("version");
                                textView_DataSync.setText("Жорий версия " + version);
                            }
                        } else {
                            Log.d("demo1", "Error getting documents: ", task.getException());
                        }
                    }
                });

        Button_DataSync.setOnClickListener(v -> {
            if (!edit_DataSync_id.getText().toString().isEmpty()) {
                DocumentReference Data = db.collection("DataSync").document("DataSync");
                Data.update("version", edit_DataSync_id.getText().toString());

                textView_DataSync.setText("Жорий версия " + edit_DataSync_id.getText().toString());
                edit_DataSync_id.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Майдонни тўлдиринг!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addDocumentButton() {
        CustomSpinnerAdapter adapterDoc = new CustomSpinnerAdapter(this, activityList, R.layout.spinner_item);
        spinner_collection.setAdapter(adapterDoc);

        spinner_collection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                DocName = parentView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Tanlangan: " + DocName, Toast.LENGTH_SHORT).show();

                // Tabiat hujjatini asosida ichki kolleksiyani olamiz
//                loadSubDocuments(DocName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });


        add_document_button.setOnClickListener(view -> {

            if (!add_edit_document.getText().toString().isEmpty()) {
                Log.d("demo59", " ққққ " + DocName + " " + DocName + " " + add_edit_document.getText().toString());

                db.collection("Shorts").document(DocName).collection(DocName).document(add_edit_document.getText().toString())
                        .set(new HashMap<>())
                        .addOnSuccessListener(aVoid -> {
                            // Муваффақиятли амалга оширилди
                            Log.d("Firestore", "Ҳужжат муваффақиятли яратилди");
                        })
                        .addOnFailureListener(e -> {
                            // Хатолик содир бўлганда
                            Log.w("Firestore", "Ҳужжатни яратишда хатолик юз берди", e);
                        });
                add_edit_document.setText("");
                Collection();
            } else {
                Toast.makeText(MainActivity.this, "Майдонни тўлдиринг!", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void AddEditCollection() {
        add_collection_button.setOnClickListener(view -> {

            if (!add_edit_collection.getText().toString().isEmpty()) {
                Log.d("demo58", " ққққ " + add_edit_collection.getText().toString());

                db.collection("Shorts").document(add_edit_collection.getText().toString())
                        .set(new HashMap<>())
                        .addOnSuccessListener(aVoid -> {
                            // Муваффақиятли амалга оширилди
                            Log.d("Firestore", "Ҳужжат муваффақиятли яратилди");
                        })
                        .addOnFailureListener(e -> {
                            // Хатолик содир бўлганда
                            Log.w("Firestore", "Ҳужжатни яратишда хатолик юз берди", e);
                        });
                add_edit_collection.setText("");
                Collection();
            } else {
                Toast.makeText(MainActivity.this, "Майдонни тўлдиринг!", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void pasteButton() {
        pasteButton.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClip() != null) {
                CharSequence copiedText = clipboard.getPrimaryClip().getItemAt(0).getText();
                if (copiedText != null) {
                    edit_short_id.setText(copiedText.toString());
                } else {
                    edit_short_id.setText("Буферда матн мавжуд эмас");
                }
            } else {
                edit_short_id.setText("Буферда матн мавжуд эмас");
            }

        });

    }


    private void Collection() {
        db.collection("Shorts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("demo28", "Error : " + e.getMessage());
                    return;
                }

                activityList.clear();  // Clear previous data
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String docId = doc.getDocument().getId();
                        activityList.add(docId);
                    }
                }
                setupDocSpinner();
                addDocumentButton();
            }
        });
    }

    private void setupDocSpinner() {
        CustomSpinnerAdapter adapterDoc = new CustomSpinnerAdapter(this, activityList, R.layout.spinner_item);
        spinnerDoc.setAdapter(adapterDoc);

        spinnerDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                DocName = parentView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Tanlangan: " + DocName, Toast.LENGTH_SHORT).show();

                // Tabiat hujjatini asosida ichki kolleksiyani olamiz
                loadSubDocuments(DocName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void loadSubDocuments(String docName) {
        db.collection("Shorts").document(docName).collection(docName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("demo28", "Error : " + e.getMessage());
                            return;
                        }

                        subDocList.clear();  // Clear previous data
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                String subDocId = doc.getDocument().getId();
                                subDocList.add(subDocId);
                            }
                        }
                        setupSubDocSpinner();
                    }
                });
    }

    private void setupSubDocSpinner() {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, subDocList, R.layout.spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                NameSubDoc = parentView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Tanlangan: " + NameSubDoc, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void spinner_young() {
        // spinner_young ga tegishli
        String[] spinner_young_list = {"2", "3", "4", "5"};

        CustomSpinnerAdapter adapter_young = new CustomSpinnerAdapter(MainActivity.this, Arrays.asList(spinner_young_list), R.layout.spinner_number_item);
        spinner_young.setAdapter(adapter_young);
        Log.d("demo46", "activityllist " + activityllist);
        spinner_young.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Танланган элементни олиш
                String selectedOption = parentView.getItemAtPosition(position).toString();
                youngNumber = selectedOption;
                Toast.makeText(MainActivity.this, "Tanlangan: " + selectedOption, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Хеч нарса танланмаса (ўрнатилган ҳолат)
            }
        });
    }


    public void AddButton() {
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Киритилаётган Видео линки
                url = edit_short_id.getText().toString();

                // Линкни аниқлаш олиш учун
                String url_app_longV = "https://youtu.be/";
                String url_app_shorts = "https://youtube.com/shorts/";
                String url_pk_longV = "https://www.youtube.com/watch?v=";
                String url_pk_shorts = "https://www.youtube.com/shorts/";

                // Линкни филтирлаш учун
                if (url.startsWith(url_app_longV)) {
                    videoId = url.substring(url.indexOf("/youtu.be/") + 10, url.indexOf("?"));
                } else if (url.startsWith(url_app_shorts)) {
                    videoId = url.substring(url.indexOf("/shorts/") + 8, url.indexOf("?"));
                } else if (url.startsWith(url_pk_longV)) {

                    videoId = url.substring(url.indexOf("v=") + 2, url.indexOf("&") == -1 ? url.length() : url.indexOf("&"));
                    if (url.length() == url.indexOf("v=") + 2 + videoId.length()) {
                        // IDдан кейин бошқа белгилар йўқ
                        videoId = url.substring(url.indexOf("v=") + 2);
                    } else {
                        // IDдан кейин белгилар мавжуд
                        videoId = url.substring(url.indexOf("v=") + 2, url.indexOf("&"));
                    }
                } else if (url.startsWith(url_pk_shorts)) {
                    videoId = url.substring(url.lastIndexOf("/") + 1);
                } else {
                    Toast.makeText(MainActivity.this, "Фақат YouTube линкини киритиш зарур", Toast.LENGTH_SHORT).show();
                    return; // Функцияни тугатади
                }

                // Map яратиш
                nestedData.put("url", edit_short_id.getText().toString());
                nestedData.put("id", videoId);
                nestedData.put("img", "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg");
                nestedData.put("youngNumber", Integer.parseInt(youngNumber));

                // arrey орқали сақлаш
                DocumentReference Data = db.collection("Shorts").document(DocName).collection(DocName).document(NameSubDoc);
                Data.update("key", FieldValue.arrayUnion(nestedData));
                edit_short_id.setText("");
                Log.d("demo46", "DocName " + DocName);
                Toast.makeText(MainActivity.this, "Видео сақланди " + DocName, Toast.LENGTH_SHORT).show();
            }
        });
    }


//    public void Spinner() {
//
//        // spinnerни техт каттароқ қилиш учун CustomSpinnerAdapter класси яратилди
//        CustomSpinnerAdapter adapterDoc = new CustomSpinnerAdapter(this, activityllist, R.layout.spinner_item);
//        spinnerDoc.setAdapter(adapterDoc);
//        Log.d("demo46", "activityllist "  + activityllist);
//        spinnerDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Танланган элементни олиш
//                String selectedOption = parentView.getItemAtPosition(position).toString();
//                DocName = selectedOption;
//                Toast.makeText(MainActivity.this, "Tanlangan: " + selectedOption, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Хеч нарса танланмаса (ўрнатилган ҳолат)
//            }
//        });
//
//        // spinnerни техт каттароқ қилиш учун CustomSpinnerAdapter класси яратилди
//        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, activityllist, R.layout.spinner_item);
//        spinner.setAdapter(adapter);
//        Log.d("demo46", "activityllist "  + activityllist);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Танланган элементни олиш
//                String selectedOption = parentView.getItemAtPosition(position).toString();
//                NameSubDoc = selectedOption;
//                Toast.makeText(MainActivity.this, "Tanlangan: " + selectedOption, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Хеч нарса танланмаса (ўрнатилган ҳолат)
//            }
//        });
//
//        // spinner_young ga tegishli
//        String[] spinner_young_list = {"2", "3", "4", "5"};
//
////        ArrayAdapter<String> adapter_young = new ArrayAdapter<>(getActivity(),  android.R.layout.simple_spinner_item , spinner_young_list);
//        CustomSpinnerAdapter adapter_young = new CustomSpinnerAdapter(MainActivity.this, Arrays.asList(spinner_young_list),  R.layout.spinner_number_item);
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_young.setAdapter(adapter_young);
//        Log.d("demo46", "activityllist "  + activityllist);
//        spinner_young.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Танланган элементни олиш
//                String selectedOption = parentView.getItemAtPosition(position).toString();
//                youngNumber = selectedOption;
//                Toast.makeText(MainActivity.this, "Tanlangan: " + selectedOption, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Хеч нарса танланмаса (ўрнатилган ҳолат)
//            }
//        });
//
//    }


}


