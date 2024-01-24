package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;

    private Context context;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = findViewById(R.id.youtube_player_view);

//        getLifecycle().addObserver(youTubePlayerView);
//        initYouTubePlayerView();

        initViews();
        List<Model> modellist = prepareMemerList();
        refreshAdapter(modellist);


    }


    private void initViews() {
        context = this;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

    }

    private void refreshAdapter (List<Model>modellist) {
        CustomAdapter adapter = new CustomAdapter(this, modellist);
        recyclerView.setAdapter(adapter);
    }

    public void openItem(Model modellist) {
//        Intent i = new Intent(MainActivity.this,MainActivity2.class);
//        startActivity(i);
        Log.d("demo15", modellist.getFirstName());
    }


    private List<Model> prepareMemerList() {
        List<Model> modellist = new ArrayList<>();
        for (int i = 0; i<=5; i++) {
            modellist.add(new Model("Kurbanov " + i, "Hamza " + i));

        }
        return modellist;
    }



    public void initYouTubePlayerView() {
        getLifecycle().addObserver(youTubePlayerView);
//        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.layout_panel);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

//                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(requireContext(), customPlayerUi, youTubePlayer, youTubePlayerView);
//                youTubePlayer.addListener(customPlayerUiController);
                setPlayNextVideoButtonClickListener(youTubePlayer);
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                       "HXrETVPKWh0",
                        0f
                );
//                Log.d("demo21", "1");
            }
        };
        // disable web ui
        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);
    }



//

    private void setPlayNextVideoButtonClickListener(final YouTubePlayer youTubePlayer) {

        Button playNextVideoButton = findViewById(R.id.button);


        playNextVideoButton.setOnClickListener(view ->

                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        "vBxNDtyE_Co",
                        0f

                )

        );
        Log.d("demo21", "2");
    }
}