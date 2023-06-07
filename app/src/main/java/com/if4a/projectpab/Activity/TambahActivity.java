package com.if4a.projectpab.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.if4a.projectpab.API.APIRequestData;
import com.if4a.projectpab.API.RetroServer;
import com.if4a.projectpab.Model.ModelResponses;
import com.if4a.projectpab.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama,etRasa,etRating,etHarga,etGambar,etDeskripsiSingkat;
    private Button btnSimpan;
    private String nama,rasa,rating,harga,deskripsiSingkat,Gambar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etRasa = findViewById(R.id.et_rasa);
        etRating = findViewById(R.id.et_rating);
        etHarga = findViewById(R.id.et_harga);
        etDeskripsiSingkat = findViewById(R.id.et_deskripsi);
        btnSimpan = findViewById(R.id.btn_tambah);
        etGambar = findViewById(R.id.et_Gambar);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama= etNama.getText().toString();
                rasa= etRasa.getText().toString();
                rating= etRating.getText().toString();
                harga= etHarga.getText().toString();
                deskripsiSingkat= etDeskripsiSingkat.getText().toString();
                Gambar = etGambar.getText().toString();


                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Tidak Boleh kosong");
                }
                else if(rasa.trim().isEmpty()){
                    etRasa.setError("Asal Tidak Boleh Kosong");
                }
                else if(rating.trim().isEmpty()){
                    etRating.setError("Rating Tidak Boleh Kosong");
                }
                else if(harga.trim().isEmpty()){
                    etHarga.setError("Asal Tidak Boleh Kosong");
                }
                else if(deskripsiSingkat.trim().isEmpty()){
                    etDeskripsiSingkat.setError("Deskripsi singkat Tidak Boleh Kosong");
                }
                else{
                    tambahJajanan();

                }
            }
        });
    }
    private void tambahJajanan(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponses> proses = ARD.ardCreate(nama,rasa,rating,harga,deskripsiSingkat,Gambar);

        proses.enqueue(new Callback<ModelResponses>() {
            @Override
            public void onResponse(Call<ModelResponses> call, Response<ModelResponses> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this,"Kode : " + kode + ", Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponses> call, Throwable t) {
                Toast.makeText(TambahActivity.this,"Gagal Menghubungi Server" + t.toString() , Toast.LENGTH_SHORT).show();

            }
        });
    }
}

