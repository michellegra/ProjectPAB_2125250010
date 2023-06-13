package com.if4a.projectpab.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.if4a.projectpab.API.APIRequestData;
import com.if4a.projectpab.API.RetroServer;
import com.if4a.projectpab.Adapter.AdapterJajanan;
import com.if4a.projectpab.Model.ModelJajanan;
import com.if4a.projectpab.Model.ModelResponses;
import com.if4a.projectpab.R;

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

    ImageView imageViewDay;
    SharedPreferences sharedPreferences=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewDay = findViewById(R.id.imageviewDay);
        SwitchMaterial switchMaterial = findViewById(R.id.switch_material);
//        sharedPreferences = getSharedPreferences("night", 0);
//        Boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);
//            if(booleanValue){
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                switchMaterial.setChecked(true);
//                imageViewDay.setImageResource(R.drawable.nightmode);
//            }


        rvJajanan = findViewById(R.id.rv_Jajanan);
        fabTambah = findViewById(R.id.fab_tambah);
        pbJajanan = findViewById(R.id.pb_Jajanan);

        lmJajanan = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvJajanan.setLayoutManager(lmJajanan);

        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchMaterial.setChecked(true);
                    imageViewDay.setImageResource(R.drawable.nightmode);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("night_mode",true);
//                    editor.commit();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switchMaterial.setChecked(false);
                    imageViewDay.setImageResource(R.drawable.lightmode);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("night_mode",true);
//                    editor.commit();
                }

            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return true;
    }

//    @SuppressLint("NonConstrantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.menu_about)
        {
            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
        }
        else
        {
            startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
        }
        return super.onOptionsItemSelected(item);
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
    }
}