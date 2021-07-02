package com.example.djgeteamproject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;



public class DicAdapter extends RecyclerView.Adapter<DicAdapter.MyViewHolder>{
    private ArrayList<ContactItem> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phonenumber);
        }
    }

    public DicAdapter(ArrayList<ContactItem> myData){
        this.mDataset = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DicAdapter.MyViewHolder holder, int position) {
        holder.name.setText(mDataset.get(position).getUsername());
        holder.phone.setText(mDataset.get(position).getPhonenumber());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

