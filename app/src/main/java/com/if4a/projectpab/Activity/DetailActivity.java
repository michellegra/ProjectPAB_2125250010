package com.if4a.projectpab.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.if4a.projectpab.R;

public class DetailActivity extends AppCompatActivity {
    public static final String ITEM_EXTRA = "item_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imgjajanan = findViewById(R.id.img);
        TextView tvDetailnama = findViewById(R.id.tv_detailnama);
        TextView tvDetailRasa = findViewById(R.id.tv_detailrasa);



    }
}