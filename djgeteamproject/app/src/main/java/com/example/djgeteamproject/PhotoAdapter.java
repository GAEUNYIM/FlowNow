package com.example.djgeteamproject;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private ArrayList<PhotoItem> imageslist;
    private Context context;

    public PhotoAdapter(Context context, ArrayList<PhotoItem> list) {
        this.context = context;
        this.imageslist = list;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.imagelayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Log.e("photo uri", imageslist.get(i).getpath());
        Glide.with(context).load(imageslist.get(i).getpath()).into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return imageslist.size();
    }

    public interface photoclicklistener {
        void onPhotoClick(View v,int position);
    }

    private photoclicklistener mListener = null;

    public void setphotolicklistener(photoclicklistener listener){
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if (mListener != null){
                            mListener.onPhotoClick(v,pos);
                        }
                    }
                }
            });
        }
    }
}
