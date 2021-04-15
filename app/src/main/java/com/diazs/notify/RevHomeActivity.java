package com.example.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class RevHomeActivity extends AppCompatActivity {
    ImageSlider imageSlider;
    private RecyclerView mRecyclerView;
    private List<String> titles;
    private List<Integer> mImages;
    private HomeMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_rev);

        imageSlider = findViewById(R.id.slider);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.slide1, null));
        images.add(new SlideModel(R.drawable.slide2, null));
        images.add(new SlideModel(R.drawable.slide1, null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);

        mRecyclerView = findViewById(R.id.recyclerview);

        titles =  new ArrayList<>();
        mImages = new ArrayList<>();

        adapter = new MyAdapter(this, titles, mImages);

        mImages.add(R.drawable.ic_forum);
        mImages.add(R.drawable.ic_voting);
        mImages.add(R.drawable.ic_elearning);
        mImages.add(R.drawable.ic_calendar);
        mImages.add(R.drawable.ic_chat);
        mImages.add(R.drawable.ic_post);

        titles.add("Forum");
        titles.add("Voting");
        titles.add("E-learning");
        titles.add("Calendar");
        titles.add("Chat");
        titles.add("Post");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(adapter);
    }
}