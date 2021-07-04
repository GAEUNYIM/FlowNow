package com.example.djgeteamproject;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class myFragment3 extends Fragment implements View.OnClickListener{

    View v = null;
    boolean fix = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my3, container, false);
        ImageView imgview = v.findViewById(R.id.frag3imageview);
        Button selectbutton =  (Button) v.findViewById(R.id.selectphotobutton);
        Button fixbutton = (Button) v.findViewById(R.id.fixbutton);
        SeekBar seekbar = v.findViewById(R.id.seekbar);
        selectbutton.setOnClickListener(this);
        fixbutton.setOnClickListener(this);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                String result = bundle.getString("bundleKey");
                // Do something with the result..
                Log.e("Frag3", "Received message:"+result);
                Uri item = getUriFromPath(result);
                if (item != null){
                    imgview.setImageURI(item);
                    Log.e("Frag3", "Success");
                }
            }
        });
        seekbar.setProgress(50);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Frag3", String.format("onProgressChanged 값 변경 중 : progress [%d] fromUser [%b]", progress, fromUser));
                transparency(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("Frag3", String.format("onStartTrackingTouch 값 변경 시작 : progress [%d]", seekBar.getProgress()));
                transparency(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("Frag3", String.format("onStopTrackingTouch 값 변경 종료: progress [%d]", seekBar.getProgress()));
                transparency(seekBar.getProgress());
            }
        });
        return v;
    }

    public void transparency(int trans){
        v.findViewById(R.id.frag3imageview).setAlpha(1-(float)trans/100);
    }
    public void onClick(View view){
        int id = view.getId();
        if(id==R.id.selectphotobutton){
            Log.e("Frag3", "ButtonPressed");
            ((MainActivity)getActivity()).gotoFrag2();
        }
        if(id==R.id.fixbutton){
            if(fix) {
                ((MainActivity) getActivity()).fixviewpager(fix = false);
            }
            else{
                ((MainActivity) getActivity()).fixviewpager(fix = true);
            }
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