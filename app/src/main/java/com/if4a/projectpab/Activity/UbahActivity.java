package com.if4a.projectpab.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.if4a.projectpab.API.APIRequestData;
import com.if4a.projectpab.API.RetroServer;
import com.if4a.projectpab.Model.ModelResponses;
import com.if4a.projectpab.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private String yId,yNama,yRasa,yRating,yHarga,yDeskripsiSingkat,yGambar;
    private EditText etNama,etRasa,etRating,etHarga,etDeskripsiSingkat,etGambar;
    private Button btnUbah;
    private String nama,rasa,rating,harga,deskripsiSingkat,gambar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xId");
        yNama = ambil.getStringExtra("xNama");
        yRasa = ambil.getStringExtra("xRasa");
        yRating = ambil.getStringExtra("xRating");
        yHarga = ambil.getStringExtra("xHarga");
        yDeskripsiSingkat = ambil.getStringExtra("xDeskripsiSingkat");
        yGambar = ambil.getStringExtra("xGambar");

        etNama = findViewById(R.id.et_ubahNama);
        etRasa = findViewById(R.id.et_ubahRasa);
        etRating = findViewById(R.id.et_ubahRating);
        etHarga = findViewById(R.id.et_ubahHarga);
        etDeskripsiSingkat = findViewById(R.id.et_ubahDeskripsi);
        etGambar =findViewById(R.id.et_ubahGambar);
        btnUbah = findViewById(R.id.btn_Ubah);


        etNama.setText(yNama);
        etRasa.setText(yRasa);
        etRating.setText(yRating);
        etHarga.setText(yHarga);
        etDeskripsiSingkat.setText(yDeskripsiSingkat);
        etGambar.setText(yGambar);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama= etNama.getText().toString();
                rasa= etRasa.getText().toString();
                rating= etRating.getText().toString();
                harga= etHarga.getText().toString();
                deskripsiSingkat= etDeskripsiSingkat.getText().toString();
                gambar=etGambar.getText().toString();

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
//                else if(gambar.trim().isEmpty()){
//                    etDeskripsiSingkat.setError("Gambar Tidak Boleh Kosong");
//                }
                else{
                    UbahJajanan();
                }
            }
        });
    }

    private void UbahJajanan(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponses> proses = ARD.ardUpdate(yId, nama,rasa,rating,harga,deskripsiSingkat,gambar);

        proses.enqueue(new Callback<ModelResponses>() {
            @Override
            public void onResponse(Call<ModelResponses> call, Response<ModelResponses> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this,"Kode : " + kode + ", Pesam: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponses> call, Throwable t) {
                Toast.makeText(UbahActivity.this,"Gagal Menghubungi Server" , Toast.LENGTH_SHORT).show();

            }
        });
    }

}
