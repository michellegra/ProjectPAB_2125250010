package com.if4a.projectpab.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.if4a.projectpab.API.APIRequestData;
import com.if4a.projectpab.API.RetroServer;
import com.if4a.projectpab.Model.ModelResponses;
import com.if4a.projectpab.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama,etAsal,etDeskripsiSingkat;
    private Button btnSimpan;
    private String nama,asal,deskripsiSingkat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etAsal = findViewById(R.id.et_asal);
        etDeskripsiSingkat = findViewById(R.id.et_deskripsi);
        btnSimpan = findViewById(R.id.btn_tambah);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama= etNama.getText().toString();
                asal= etAsal.getText().toString();
                deskripsiSingkat= etDeskripsiSingkat.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Tidak Boleh kosong");
                }
                else if(asal.trim().isEmpty()){
                    etAsal.setError("Asal Tidak Boleh Kosong");
                }
                else if(deskripsiSingkat.trim().isEmpty()){
                    etDeskripsiSingkat.setError("Deskripsi singkat Tidak Boleh Kosong");
                }
                else{
                    tambahKuliner();

                }
            }
        });
    }
    private void tambahKuliner(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponses> proses = ARD.ardCreate(nama,asal,deskripsiSingkat);

        proses.enqueue(new Callback<ModelResponses>() {
            @Override
            public void onResponse(Call<ModelResponses> call, Response<ModelResponses> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this,"Kode : " + kode + ", Pesam: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponses> call, Throwable t) {
                Toast.makeText(TambahActivity.this,"Gagal Menghubungi Server" , Toast.LENGTH_SHORT).show();

            }
        });
    }
}

