package com.if4a.projectpab.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if4a.projectpab.API.APIRequestData;
import com.if4a.projectpab.API.RetroServer;
import com.if4a.projectpab.Activity.MainActivity;
import com.if4a.projectpab.Activity.UbahActivity;
import com.if4a.projectpab.Model.ModelJajanan;
import com.if4a.projectpab.Model.ModelResponses;
import com.if4a.projectpab.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterJajanan extends RecyclerView.Adapter<AdapterJajanan.VHKuliner> {
    private Context ctx;
    private List<ModelJajanan> listJajanan;

    public AdapterJajanan(Context ctx, List<ModelJajanan> listJajanan) {
        this.ctx = ctx;
        this.listJajanan = listJajanan;
    }

    @NonNull
    @Override
    public VHKuliner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_jajanan, parent,false);
        return new VHKuliner(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHKuliner holder, int position) {
        ModelJajanan MK = listJajanan.get(position);
        holder.tvId.setText(MK.getId());
        holder.tvNama.setText((position+1) + "." + MK.getNama());
        holder.tvAsal.setText(MK.getAsal());
        holder.tvDeskrpsiSingkat.setText(MK.getDeskripsi_singkat());
    }

    @Override
    public int getItemCount() {
        return listJajanan.size();
    }

    public class VHKuliner extends RecyclerView.ViewHolder{
        TextView tvId,tvNama,tvAsal,tvDeskrpsiSingkat;

        public VHKuliner(@NonNull View itemView) {
            super(itemView);

            tvId =itemView.findViewById(R.id.tv_id);
            tvNama =itemView.findViewById(R.id.tv_nama);
            tvAsal =itemView.findViewById(R.id.tv_asal);
            tvDeskrpsiSingkat =itemView.findViewById(R.id.tv_deskripsi);

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
                            pindah.putExtra("xAsal", tvAsal.getText().toString());
                            pindah.putExtra("xDeskripsiSingkat", tvDeskrpsiSingkat.getText().toString());
                            ctx.startActivity(pindah);
                        }
                    });
                    pesan.show();

                    return false;
                }
            });
        }

        private void hapusJajanan(String idJajanan){
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponses> proses = ARD.ardDelete(idJajanan);

            proses.enqueue(new Callback<ModelResponses>() {
                @Override
                public void onResponse(Call<ModelResponses> call, Response<ModelResponses> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode :" + kode +",Pesan: " + pesan,Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieveKuliner();
                }

                @Override
                public void onFailure(Call<ModelResponses> call, Throwable t) {

                }
            });

        }

    }
}
