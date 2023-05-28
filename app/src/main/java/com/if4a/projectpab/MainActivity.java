package com.if4a.projectpab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvKuliner;
    private FloatingActionButton fabTambah;
    private ProgressBar pbKuliner;
    private RecyclerView.Adapter adKuliner;
    private RecyclerView.LayoutManager lmKuliner;
    private List<ModelKuliner> listKuliner = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvKuliner = findViewById(R.id.rv_kuliner);
        fabTambah = findViewById(R.id.fab_tambah);
        pbKuliner = findViewById(R.id.pb_kuliner);
        lmKuliner = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvKuliner.setLayoutManager(lmKuliner);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TambahActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveKuliner();
    }

    public void retrieveKuliner() {
        pbKuliner.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listKuliner = response.body().getData();

                adKuliner = new AdapterKuliner(MainActivity.this, listKuliner);
                rvKuliner.setAdapter(adKuliner);
                adKuliner.notifyDataSetChanged();

                pbKuliner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbKuliner.setVisibility(View.GONE);
            }
        });
    }
}