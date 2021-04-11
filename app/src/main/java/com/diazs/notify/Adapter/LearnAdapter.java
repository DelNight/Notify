package com.diazs.notify.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private Context context;

    public LearnAdapter(ArrayList<Learn> learnArrayList, Context context){
        this.learnArrayList = learnArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public LearnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_form_learn, parent, false);
        return new LearnAdapter.LearnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnViewHolder holder, int position) {
        Learn learn = learnArrayList.get(position);
        Drawable drawable;
        ImageView gambar;

        if (learn.getType() == 1){
            drawable = this.context.getResources().getDrawable(R.drawable.ic_baseline_image_24);
            gambar = holder.gambar;
            gambar.setImageDrawable(drawable);
        } else if(learn.getType() == 2){
            drawable = this.context.getResources().getDrawable(R.drawable.ic_baseline_videocam_24);
            gambar = holder.gambar;
            gambar.setImageDrawable(drawable);
        } else if (learn.getType() == 3){
            drawable = this.context.getResources().getDrawable(R.drawable.ic_baseline_description_24);
            gambar = holder.gambar;
            gambar.setImageDrawable(drawable);
        } else{
            drawable = this.context.getResources().getDrawable(R.drawable.ic_baseline_description_24);
            gambar = holder.gambar;
            gambar.setImageDrawable(drawable);
        }

        Glide.with(holder.itemView.getContext())
                .load(drawable)
                .apply(new RequestOptions().override(55, 55))
                .into(holder.gambar);
        holder.fileName.setText(learnArrayList.get(position).getNamaFile());
    }

    @Override
    public int getItemCount() {
        return learnArrayList.size();
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