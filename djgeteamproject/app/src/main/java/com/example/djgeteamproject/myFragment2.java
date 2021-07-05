package com.example.djgeteamproject;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.djgeteamproject.databinding.PhotoremovepopupBinding;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import static androidx.fragment.app.FragmentKt.setFragmentResult;


public class myFragment2 extends Fragment {

    private ArrayList<PhotoItem> imageslist = new ArrayList<>();
    private ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    private PhotoAdapter mAdapter;
    private SwipeRefreshLayout refreshview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my2, container, false);
        prepareData();
        imageView = (ImageView)v.findViewById(R.id.imageView);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        PhotoAdapter dataAdapter = new PhotoAdapter(getActivity().getApplicationContext(), imageslist);
        recyclerView.setAdapter(dataAdapter);
        dataAdapter.setphotolicklistener(new PhotoAdapter.photoclicklistener(){
            @Override
            public void onPhotoClick(View v, int position) {
                if(((MainActivity)getActivity()).getisSelect()){
                    Log.e("Frag2", "PhotoSelected "+position);
                    String imagepath = imageslist.get(position).getpath();
                    Bundle result = new Bundle();
                    result.putString("bundleKey", imagepath);
                    getParentFragmentManager().setFragmentResult("key", result);
                    ((MainActivity)getActivity()).gotoFrag3();
                }
            }
        });

        dataAdapter.setphototouchlistener(new PhotoAdapter.phototouchlistener(){
            @Override
            public void onPhotoTouch(View v, int position) {
                String imagepath = imageslist.get(position).getpath();
                ((MainActivity)getActivity()).makepopup(imagepath);
            }
        });
        refreshview = v.findViewById(R.id.refresh_layout);
        refreshview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshview.setRefreshing(false);
                    }
                }, 500);
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
            }
        });

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void prepareData(){ getImageList(); }

    public Uri getUriFromPath(String filePath) {
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, "_data = '" + filePath + "'", null, null);

        cursor.moveToNext();
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

        return uri;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getImageList() {
        // Initialize uri
        Uri uri =  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // String projection
        String[] projection = new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME
        };
        // Selection
//        String[] selectionArgs = null;
        // Sort by ascending
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";
        // Initialize cursor

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, sortOrder);

        LinkedHashSet<PhotoItem> hashlist = new LinkedHashSet<>();
        // Check condition
        if(cursor.moveToFirst()) {
            do {
                PhotoItem photoItem = new PhotoItem();
                Uri photouri = getUriFromPath(cursor.getString(1));
                photoItem.setData(photouri);
                photoItem.setpath(cursor.getString(1));
                photoItem.setDisplayName(cursor.getString(2));
                photoItem.setId(cursor.getInt(0));
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