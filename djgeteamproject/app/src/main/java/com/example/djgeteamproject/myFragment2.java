package com.example.djgeteamproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


public class myFragment2 extends Fragment {
    private ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my2, container, false);
        imageView = (ImageView)v.findViewById(R.id.imageView);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList imageUrlList = prepareData();
        PhotoAdapter dataAdapter = new PhotoAdapter(getActivity().getApplicationContext(), imageUrlList);
        recyclerView.setAdapter(dataAdapter);
        return v;
    }
    private ArrayList prepareData() {
        String imageUrls[] = {
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300"
        };

        ArrayList imageUrlList = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            ImageUrl imageUrl = new ImageUrl();
            imageUrl.setImageUrl(imageUrls[i]);
            imageUrlList.add(imageUrl);
        }
        Log.d("MainActivity", "List count: " + imageUrlList.size());
        return imageUrlList;
    }
}