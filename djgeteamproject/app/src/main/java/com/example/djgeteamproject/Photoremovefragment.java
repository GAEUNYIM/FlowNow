package com.example.djgeteamproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class Photoremovefragment extends DialogFragment implements View.OnClickListener{
    String filepath;
    Uri uri;
    public static Photoremovefragment getInstance(){
        Photoremovefragment p = new Photoremovefragment();
        return p;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.photoremovepopup, container);
        Bundle path = getArguments();
        filepath = path.getString("data");
        uri = getUriFromPath(filepath);
        FloatingActionButton remove = v.findViewById(R.id.remove);
        FloatingActionButton notremove = v.findViewById(R.id.notremove);
        remove.setOnClickListener(this);
        notremove.setOnClickListener(this);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notremove:
                dismiss();
                break;
            case R.id.remove:
                ContentResolver resolver = getActivity().getApplicationContext().getContentResolver();
                resolver.delete(uri,null, null);
                dismiss();
        }
    }

    public Uri getUriFromPath(String filePath) {
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, "_data = '" + filePath + "'", null, null);
        cursor.moveToNext();
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
        return uri;
    }
}
