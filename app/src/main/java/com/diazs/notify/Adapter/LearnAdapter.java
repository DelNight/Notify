package com.diazs.notify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.diazs.notify.Model.Learn;
import com.diazs.notify.R;

import java.util.ArrayList;

public class LearnAdapter extends RecyclerView.Adapter<LearnAdapter.LearnViewHolder> {
    private ArrayList<Learn> learnArrayList;

    public LearnAdapter(ArrayList<Learn> learnArrayList){
        this.learnArrayList = learnArrayList;
    }

    @NonNull
    @Override
    public LearnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_form_learn, parent, false);
        return  new LearnAdapter.LearnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnViewHolder holder, int position) {
        Learn learn = learnArrayList.get(position);
        int gambar = 0;

        if (learn.getType() == 1){
            gambar = R.drawable.ic_baseline_image_24;
        } else if(learn.getType() == 2){
            gambar = R.drawable.ic_baseline_videocam_24;
        } else if (learn.getType() == 3){
            gambar = R.drawable.ic_baseline_description_24;
        }

        Glide.with(holder.itemView.getContext())
                .load(gambar)
                .apply(new RequestOptions().override(55, 55))
                .into(holder.gambar);
        holder.fileName.setText(learnArrayList.get(position).getNamaFile());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LearnViewHolder extends RecyclerView.ViewHolder {
        private ImageView gambar;
        private TextView fileName;

        public LearnViewHolder(View view){
            super(view);
            gambar = (ImageView) view.findViewById(R.id.gambar);
            fileName = (TextView) view.findViewById(R.id.fileName);
        }
    }
}