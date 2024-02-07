package com.example.recyclerview;

import static android.media.CamcorderProfile.get;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class NoteAdapter extends FirestoreRecyclerAdapter<NoteModel, NoteAdapter.NoteHolder> {
    private final FirestoreRecyclerOptions<NoteModel> options;
    private List<NoteModel> modellist;
    private RecyclerViewClickListner listner;
    public NoteAdapter(FirestoreRecyclerOptions<NoteModel> options, RecyclerViewClickListner listner) {
        super(options);
        this.listner=listner;
        this.options=options;
//        super(options);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_layout,
                parent, false);
        return new NoteHolder(v);
    }



    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull NoteModel noteModel) {



            holder.textViewTitle.setText(noteModel.getTitle());
//        holder.textViewDescription.setText(model.getDescription());
            holder.textViewPriority.setText(String.valueOf(noteModel.getPriority()));



    }



    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPriority;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewPriority = itemView.findViewById(R.id.first_name);
//            textViewDescription = itemView.findViewById(R.id.last_name);
            textViewTitle = itemView.findViewById(R.id.last_name);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
                listner.onClick(itemView, getAdapterPosition());
        }

    }

    public interface RecyclerViewClickListner {
        void onClick(View v, int position);

    }
}
















//public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


//    private MainActivity activity;
//    private RecyclerViewClickListner listner;
//
////    private Context context;
//    public List<Model> modellist;
//
//
//        public CustomAdapter(MainActivity activity, List<Model> modellist, RecyclerViewClickListner listner) {
////        this.context=context;
//        this.modellist=modellist;
//        this.activity=activity;
//        this.listner=listner;
//    }
//
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_layout, parent, false);
//        return new CustomViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Model member = modellist.get(position);
//
//        if(holder instanceof CustomViewHolder){
//            LinearLayout lay_click = ((CustomViewHolder) holder).lay_click;
//            TextView fist_name = ((CustomViewHolder) holder).first_name;
//            TextView last_name = ((CustomViewHolder) holder).last_name;
//
//            fist_name.setText(member.getFirstName());
//            last_name.setText(member.getLastName());
//
////            lay_click.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    activity.openItem(member);
////                    activity.initYouTubePlayerView();
////                    Intent intent = new Intent(v.getContext(), MainActivity2.class);
////                    v.getContext().startActivity(intent);
//
////                    ((CustomViewHolder) holder).youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
////                        @Override
////                        public void onReady(@NonNull YouTubePlayer youTubePlayer)  {
////
////                            String videoId = "HXrETVPKWh0";
////                            youTubePlayer.cueVideo(videoId, 0);
//////                Log.d("demo20", String.valueOf(2));
//////
////                        }
////
////                    });
////                }
////            });
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return modellist.size();
//    }
//
//        public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public View view;
//        private LinearLayout lay_click;
//        YouTubePlayerView youTubePlayerView;
//        public TextView first_name, last_name, nameTxt;
//        public CustomViewHolder(View v) {
//            super(v);
//            view = v;
//            lay_click = view.findViewById(R.id.lay_click);
//            first_name = view.findViewById(R.id.first_name);
//            last_name = view.findViewById(R.id.last_name);
//            youTubePlayerView = view.findViewById(R.id.youtube_player_view);
////            nameTxt = view.findViewById(R.id.nameTextView);
//            view.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//                listner.onClick(view, getAdapterPosition());
//        }
//    }
//
//
//        public interface RecyclerViewClickListner {
//            void onClick(View v, int position);
//        }



//}
