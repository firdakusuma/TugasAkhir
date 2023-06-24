package com.example.tugasakhir;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectadam.databinding.ItemViewBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MobilAdapter extends RecyclerView.Adapter<MobilAdapter.ViewHolder> {

    private final ArrayList<Mobil> mobilList;

    public MobilAdapter(ArrayList<Mobil> mobilList) {
        this.mobilList = mobilList;
    }

    @NonNull
    @Override
    public MobilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding binding = ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MobilAdapter.ViewHolder holder, int position) {
        holder.bind(mobilList.get(position));
    }

    @Override
    public int getItemCount() {
        return mobilList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemViewBinding binding;
        private final FirebaseDatabase firebaseDatabase;
        private final DatabaseReference databaseReference;

        public ViewHolder(@NonNull ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            firebaseDatabase = FirebaseDatabase.getInstance("https://tugasakhir-187318-default-rtdb.asia-southeast1.firebasedatabase.app/");
            databaseReference = firebaseDatabase.getReference("mobil");
        }

        public void bind(Mobil mobil) {
            binding.tvNamaMobil.setText(mobil.getNamaMobil());
            binding.tvModel.setText(mobil.getModel());

            binding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), EditPage.class);
                    intent.putExtra("EXTRA_MOBIL", mobil);
                    view.getContext().startActivity(intent);
                }
            });

            binding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference.child(mobil.getKey()).removeValue();
                        }
                    }).setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setMessage("APAKAH ANDA INGIN MENGHAPUS MOBIL?");
                    builder.show();
                }
            });

            binding.cvItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailMobilActivity.class);
                    intent.putExtra("EXTRA_MOBIL", mobil);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }
}
