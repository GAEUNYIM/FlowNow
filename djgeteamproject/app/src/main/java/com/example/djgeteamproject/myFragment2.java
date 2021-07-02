package com.example.djgeteamproject;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;


public class myFragment2 extends Fragment {

    private ArrayList<PhotoItem> imageslist = new ArrayList<>();
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

//        ArrayList imageUrlList = prepareData();
//        PhotoAdapter dataAdapter = new PhotoAdapter(getActivity().getApplicationContext(), imageUrlList);

        prepareData();
        PhotoAdapter dataAdapter = new PhotoAdapter(getActivity().getApplicationContext(), imageslist);


        recyclerView.setAdapter(dataAdapter);
        return v;
    }

//    private ArrayList prepareData() {
        private void prepareData(){
            getImageList();

//        String imageUrls[] = {
//                "https://picsum.photos/id/237/200/300",
//                "https://picsum.photos/id/237/200/300",
//                "https://picsum.photos/id/237/200/300",
//                "https://picsum.photos/id/237/200/300",
//                "https://picsum.photos/id/237/200/300",
//                "https://picsum.photos/id/237/200/300",
//                "https://picsum.photos/id/237/200/300",
//                "https://picsum.photos/id/237/200/300"
//        };
//
//        ArrayList imageUrlList = new ArrayList<>();
//        for (int i = 0; i < imageUrls.length; i++) {
//            ImageUrl imageUrl = new ImageUrl();
//            imageUrl.setImageUrl(imageUrls[i]);
//            imageUrlList.add(imageUrl);
//        }
//        Log.d("MainActivity", "List count: " + imageUrlList.size());
//        return imageUrlList;

    }

    private void getImageList() {

        System.out.println("<1>");
        // Initialize uri
        Uri uri =  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // String projection
        String[] projection = new String[] {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID
        };
        System.out.println("<2>");
        // Selection
//        String[] selectionArgs = null;
        // Sort by ascending
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " ASC";
        // Initialize cursor

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, sortOrder);

        System.out.println("<3>");
        LinkedHashSet<PhotoItem> hashlist = new LinkedHashSet<>();
        // Check condition
        if(cursor.moveToFirst()) {
            do {
                PhotoItem photoItem = new PhotoItem();
                photoItem.setData(cursor.getString(0));
                photoItem.setDisplayName(cursor.getString(1));
                photoItem.setId(cursor.getInt(2));

                hashlist.add(photoItem);
            } while (cursor.moveToNext());
        }
        ArrayList<PhotoItem> photoItems = new ArrayList<PhotoItem>(hashlist);
        System.out.println(photoItems.size());
        for (int i=0; i < photoItems.size(); i++) {
            photoItems.get(i).setId(i);
        }
        imageslist = photoItems;
    }
}