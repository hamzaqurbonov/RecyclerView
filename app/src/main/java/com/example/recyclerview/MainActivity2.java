package com.example.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    TextView nameTextView;
    String user;
    private static final String[] myRef1 = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

          nameTextView = findViewById(R.id.nameTextView);

          String usersmane = "Users not set";


          Bundle extras = getIntent().getExtras();
          if (extras != null) {
              usersmane = extras.getString("Kurbanov")       ;
          }

          nameTextView.setText(usersmane);

        Log.d("demo17", String.valueOf(usersmane));



        youTubePlayerView = findViewById(R.id.youtube_player_view2);
        initYouTubePlayerView();
    }

//    private static String getSetText(String myRef1) {
//
////        Log.d("demo17", String.valueOf(myRef1));
//        return myRef1;
//}


//    public static String getNextVideoId() {
//
//        return clon;
//    }



    public void initYouTubePlayerView() {
        getLifecycle().addObserver(youTubePlayerView);
//        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.layout_panel);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

//                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(requireContext(), customPlayerUi, youTubePlayer, youTubePlayerView);
//                youTubePlayer.addListener(customPlayerUiController);
//                setPlayNextVideoButtonClickListener(youTubePlayer);
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        "",
                        0f
                );
//                Log.d("demo17", getSetText().toString());
            }
        };
        // disable web ui
        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);
    }
}
