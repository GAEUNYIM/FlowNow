package com.example.djgeteamproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.MyViewHolder> {
    private ArrayList<SaveScore> mDataset;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView score;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.scorelayoutid);
            score = (TextView) view.findViewById(R.id.scorelayoutscore);
        }
    }

    public ScoreAdapter(ArrayList<SaveScore> myData) {
        this.mDataset = myData;
    }


    @NonNull
    @NotNull
    @Override
    public ScoreAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scorelayout, parent, false);
        context=parent.getContext();
        return new ScoreAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.score.setText(mDataset.get(position).getscore());
        holder.id.setText(mDataset.get(position).getid());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
