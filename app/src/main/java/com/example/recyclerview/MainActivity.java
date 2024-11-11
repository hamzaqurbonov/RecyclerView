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
import android.widget.Spinner;
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
    Map<String,Object> nestedData = new HashMap<>();
    public List<String> activityllist = new ArrayList<>();
    EditText edit_short_id;
    Button add_button, pasteButton;
    Spinner spinner, spinner_young, spinnerDoc ;
    String DocName, NameSubDoc, youngNumber, videoId, url ;
    private ArrayList<String> subDocList = new ArrayList<>();
    private ArrayList<String> activityList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner_young = findViewById(R.id.spinner_young);
        spinner = findViewById(R.id.spinner);
        spinnerDoc = findViewById(R.id.spinnerDoc);
        edit_short_id = findViewById(R.id.edit_short_id);
        add_button = findViewById(R.id.add_button);
        pasteButton = findViewById(R.id.pasteButton);


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





        Collection();
        AddButton();
        spinner_young();
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
                nestedData.put("id",   videoId);
                nestedData.put("img",   "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg");
                nestedData.put("youngNumber", Integer.parseInt( youngNumber));

                // arrey орқали сақлаш
                DocumentReference Data = db.collection("Shorts").document(DocName).collection(DocName).document(DocName);
                Data.update("key", FieldValue.arrayUnion(nestedData));
                edit_short_id.setText("");
                Log.d("demo46", "DocName "  + DocName);
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


