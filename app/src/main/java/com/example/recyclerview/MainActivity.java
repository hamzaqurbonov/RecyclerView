package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;
    Button playNextVideoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        initYouTubePlayerView();


        RecyclerView ParentRecyclerViewItem = findViewById( R.id.parent_recyclerview);

        // Initialise the Linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()
        ParentItemAdapter parentItemAdapter = new ParentItemAdapter(ParentItemList());

        // Set the layout manager
        // and adapter for items
        // of the parent recyclerview
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

    }

    private List<ParentItem> ParentItemList()
    {
        List<ParentItem> itemList = new ArrayList<>();

        ParentItem item = new ParentItem("Title 1", ChildItemList());
        itemList.add(item);

//        ParentItem item1
//                = new ParentItem(
//                "Title 2",
//                ChildItemList());
//        itemList.add(item1);
//        ParentItem item2
//                = new ParentItem(
//                "Title 3",
//                ChildItemList());
//        itemList.add(item2);
//        ParentItem item3
//                = new ParentItem(
//                "Title 4",
//                ChildItemList());
//        itemList.add(item3);
//        ParentItem item5
//                = new ParentItem(
//                "Title 5",
//                ChildItemList());
//        itemList.add(item5);

        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<ChildItem> ChildItemList()
    {
        List<ChildItem> ChildItemList = new ArrayList<>();

        ChildItemList.add(new ChildItem(ChildItemAdapter.getNextVideoId()));
        ChildItemList.add(new ChildItem(ChildItemAdapter.getNextVideoId()));
        ChildItemList.add(new ChildItem(ChildItemAdapter.getNextVideoId()));
        ChildItemList.add(new ChildItem(ChildItemAdapter.getNextVideoId()));
        ChildItemList.add(new ChildItem(ChildItemAdapter.getNextVideoId()));

        return ChildItemList;
    }


    private void initYouTubePlayerView() {
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
                        ChildItemAdapter.getNextVideoId(), 0f
                );
                Log.d("demo21", "1");
            }
        };
        // disable web ui
        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);
    }





    private void setPlayNextVideoButtonClickListener(final YouTubePlayer youTubePlayer) {

        Button playNextVideoButton = findViewById(R.id.slect_button);


        playNextVideoButton.setOnClickListener(view ->

                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        ChildItemAdapter.getNextVideoId(),
                        0f

                )

        );
        Log.d("demo21", "2");
    }
}