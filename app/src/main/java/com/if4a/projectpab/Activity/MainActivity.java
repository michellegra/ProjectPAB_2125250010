package com.if4a.projectpab.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.if4a.projectpab.API.APIRequestData;
import com.if4a.projectpab.API.RetroServer;
import com.if4a.projectpab.Adapter.AdapterJajanan;
import com.if4a.projectpab.Model.ModelJajanan;
import com.if4a.projectpab.Model.ModelResponses;
import com.if4a.projectpab.R;
import com.if4a.projectpab.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvJajanan;
    private FloatingActionButton fabTambah;
    private ProgressBar pbJajanan;
    private RecyclerView.Adapter adJajanan;
    private RecyclerView.LayoutManager lmJajanan;
    private List<ModelJajanan> listJajanan = new ArrayList<>();

//    private lateinit var binding:ActivityMainBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        refreshApp();

        rvJajanan = findViewById(R.id.rv_Jajanan);
        fabTambah = findViewById(R.id.fab_tambah);
        pbJajanan = findViewById(R.id.pb_Jajanan);
        lmJajanan = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvJajanan.setLayoutManager(lmJajanan);

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
        retrieveJajanan();
    }

    public void retrieveJajanan() {
        pbJajanan.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponses> proses = ARD.ardRetrieve();

        proses.enqueue(new Callback<ModelResponses>() {
            @Override
            public void onResponse(Call<ModelResponses> call, Response<ModelResponses> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listJajanan = response.body().getData();

                adJajanan = new AdapterJajanan(MainActivity.this, listJajanan);
                rvJajanan.setAdapter(adJajanan);
                adJajanan.notifyDataSetChanged();

                pbJajanan.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponses> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbJajanan.setVisibility(View.GONE);
            }
        });

//        private void refreshApp(){
//            Object swipeToRefresh;
//            swipeToRefresh.setOnRefreshListener{
//                Toast.makeText(this,"Page refreshed", Toast.LENGTH_SHORT).show();
//            swipeToRefresh.isRefreshing = false
//            }
//        }
    }
}