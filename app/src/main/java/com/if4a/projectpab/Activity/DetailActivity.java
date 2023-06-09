package com.if4a.projectpab.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.if4a.projectpab.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView  tvNama, tvRasa,tvRating,tvHarga,tvDeskripsiSingkat;
    private ImageView iv_Gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama=findViewById(R.id.tv_detailnama);
        tvRasa=findViewById(R.id.tv_detailrasa);
        tvRating=findViewById(R.id.tv_detailrating);
        tvHarga=findViewById(R.id.tv_detailharga);
        tvDeskripsiSingkat=findViewById(R.id.tv_detaildeskripsiSingkat);
        iv_Gambar = findViewById(R.id.iv_detail);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("varNama");
        String rasa = intent.getStringExtra("varRasa");
        String rating = intent.getStringExtra("varRating");
        String harga = intent.getStringExtra("varHarga");
        String deskripsiSingkat = intent.getStringExtra("varDeskripsiSingkat");
        String Gambar = getIntent().getStringExtra("varGambar");

        tvNama.setText(nama);
        tvRasa.setText(rasa);
        tvRating.setText(rating);
        tvHarga.setText(harga);
        tvDeskripsiSingkat.setText(deskripsiSingkat);
        Picasso.get().load(Gambar).into(iv_Gambar);

    }
}