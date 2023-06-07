package com.if4a.projectpab.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.if4a.projectpab.API.APIRequestData;
import com.if4a.projectpab.API.RetroServer;
import com.if4a.projectpab.Activity.DetailActivity;
import com.if4a.projectpab.Activity.MainActivity;
import com.if4a.projectpab.Activity.UbahActivity;
import com.if4a.projectpab.Model.ModelJajanan;
import com.if4a.projectpab.Model.ModelResponses;
import com.if4a.projectpab.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterJajanan extends RecyclerView.Adapter<AdapterJajanan.VHJajanan> {
    private Context ctx;
    private List<ModelJajanan> listJajanan;

    public AdapterJajanan(Context ctx, List<ModelJajanan> listJajanan) {
        this.ctx = ctx;
        this.listJajanan = listJajanan;
    }

    @NonNull
    @Override
    public VHJajanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_jajanan, parent,false);
        return new VHJajanan(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHJajanan holder, int position) {
        ModelJajanan MJ = listJajanan.get(position);
        holder.tvId.setText(MJ.getId());
        holder.tvNama.setText((position+1) + "." + MJ.getNama());
        holder.tvRasa.setText(MJ.getRasa());
        holder.tvRating.setText(MJ.getRating());
        holder.tvHarga.setText(MJ.getHarga());
        holder.tvDeskrpsiSingkat.setText(MJ.getDeskripsi_singkat());

//        holder.bind(new ModelJajanan(MJ.getGambar()));

        if(MJ.getGambar().isEmpty()){
            holder.iv_gambar.setImageResource(R.drawable.ic_launcher_background);
        }
        else{
            Picasso.get().load(MJ.getGambar()).into(holder.iv_gambar);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = MJ.getNama();
                String rasa = MJ.getRasa();
                String rating = MJ.getRating();
                String harga = MJ.getHarga();
                String deskripsiSingkat = MJ.getDeskripsi_singkat();
                String Gambar = MJ.getGambar();

                Intent intent  = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("varNama",nama);
                intent.putExtra("varRasa",rasa);
                intent.putExtra("varRating",rating);
                intent.putExtra("varHarga",harga);
                intent.putExtra("varDeskripsiSingkat",deskripsiSingkat);
                intent.putExtra("varGambar", Gambar);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listJajanan.size();
    }

    public class VHJajanan extends RecyclerView.ViewHolder{
        TextView tvId, tvNama,tvRasa,tvRating,tvHarga,tvDeskrpsiSingkat,tvGambar;
        ImageView iv_gambar;
//        private ModelJajanan gambar;

        public VHJajanan(@NonNull View itemView) {
            super(itemView);

            tvId =itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvRasa = itemView.findViewById(R.id.tv_rasa);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            tvDeskrpsiSingkat =itemView.findViewById(R.id.tv_deskripsi);
            iv_gambar = itemView.findViewById(R.id.iv_jajanan);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);

                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Operasi Apa yang Ingin Dilakukan?");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hapusJajanan(tvId.getText().toString());
                            dialog.dismiss();

                        }
                    });

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent pindah = new Intent(ctx, UbahActivity.class);
                            pindah.putExtra("xId", tvId.getText().toString());
                            pindah.putExtra("xNama", tvNama.getText().toString());
                            pindah.putExtra("xRasa", tvRasa.getText().toString());
                            pindah.putExtra("xRating", tvRating.getText().toString());
                            pindah.putExtra("xHarga", tvHarga.getText().toString());
                            pindah.putExtra("xDeskripsiSingkat", tvDeskrpsiSingkat.getText().toString());
                            ctx.startActivity(pindah);
                        }
                    });
                    pesan.show();

                    return false;
                }
            });
        }

//        public void bind (ModelJajanan gambar){
//            this.gambar = gambar;
//
//            Glide.with(itemView.getContext())
//                    .load(gambar.getGambar())
//                    .into(iv_gambar);
//        }

        private void hapusJajanan(String idJajanan){
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponses> proses = ARD.ardDelete(idJajanan);

            proses.enqueue(new Callback<ModelResponses>() {
                @Override
                public void onResponse(Call<ModelResponses> call, Response<ModelResponses> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode :" + kode +",Pesan: " + pesan,Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieveJajanan();
                }

                @Override
                public void onFailure(Call<ModelResponses> call, Throwable t) {

                }
            });

        }

    }
}
