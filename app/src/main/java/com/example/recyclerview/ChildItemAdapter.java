package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {

    private List<ChildItem> ChildItemList;

//    static ArrayList<String> mylist = new ArrayList<String>();

    public static final String[] my = {"4UbhF0uNpaM", "e_FWd4O9VY4", "mkZ3GXZoQtQ","pIj9XzDlJHU","hS1LNpuhslI","ZvLt_S4GzpY","X3tr5ax78V4", "k_an7b4r1_Q", "sOxloXyOAKA", "N-WDm0-R8YQ",};


    public static final Random random = new Random();


    public static String getNextVideoId() {

//        Log.d("demo21", String.valueOf(mylist));
//        Collections.shuffle(mylist);
//        Log.d("demo21", String.valueOf(mylist));

        return my[random.nextInt(my.length)];
    }



    // Constructor
    ChildItemAdapter(List<ChildItem> childItemList)
    {


        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {




        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.child_item,
                        viewGroup, false);

        return new ChildViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position){

        // Create an instance of the ChildItem
        // class for the given position
        ChildItem childItem = ChildItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        childViewHolder.ChildItemTitle.setText(childItem.getChildItemTitle());
//        childViewHolder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer)  {
//
//                String videoId = getNextVideoId();
//
//                youTubePlayer.cueVideo(videoId, 0);
//
//
////                Log.d("demo20", getNextVideoId());
////                Collections.shuffle(mylist);
////                Log.d("demo21", String.valueOf(mylist));
//
////
//            }

//        });

//
    }

    @Override
    public int getItemCount()
    {

        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return ChildItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView ChildItemTitle;

        YouTubePlayerView youTubePlayerView;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            ChildItemTitle  = itemView.findViewById(R.id.child_item_title);
//            youTubePlayerView  = itemView.findViewById(R.id.youtube_player_view);

        }
    }
}