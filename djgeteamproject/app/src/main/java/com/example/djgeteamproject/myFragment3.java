package com.example.djgeteamproject;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.annotation.SuppressLint;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class myFragment3 extends Fragment implements View.OnClickListener {

    View v = null;
    DrawingView drawingView;
    boolean fix = true;
    private boolean isGmode = false; // true at gyro Mode; false at pencil Mode
    private Bitmap selectedphoto = null;
    TextView startandstop;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my3, container, false);
        startandstop = v.findViewById(R.id.gyromode);
        Button evalbutton = (Button) v.findViewById(R.id.evaluation);
        evalbutton.setOnClickListener(this);
        Button gModeButton = (Button) v.findViewById(R.id.gyromode);
        gModeButton.setOnClickListener(this);
        Button showscorelistbutton = (Button) v.findViewById(R.id.showscores);
        showscorelistbutton.setOnClickListener(this);
        ImageView imgview = v.findViewById(R.id.frag3imageview);
        FloatingActionButton selectbutton = (FloatingActionButton) v.findViewById(R.id.selectphotobutton);
        SeekBar acceleration = v.findViewById(R.id.acceleration);
        selectbutton.setOnClickListener(this);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch fixSwitch = (Switch) v.findViewById(R.id.fix_switch);
        fixSwitch.setOnCheckedChangeListener(new visibilitySwitchListener());

        SeekBar seekbar = v.findViewById(R.id.seekbar);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                String result = bundle.getString("bundleKey");
                // Do something with the result..
                Log.e("Frag3", "Received message:" + result);
                Uri item = getUriFromPath(result);
                if (item != null) {
                    imgview.setImageURI(item);
                    BitmapDrawable d = (BitmapDrawable) imgview.getDrawable();
                    selectedphoto = d.getBitmap();
                    Log.e("Frag3", "Success");
                }
            }
        });
        seekbar.setProgress(50);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        acceleration.setProgress(50);
        acceleration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setAcceleration(progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setAcceleration(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setAcceleration(seekBar.getProgress());
            }
        });

        drawingView = v.findViewById(R.id.drawingView);

        Button resetButton = v.findViewById(R.id.resetBtn);
        FloatingActionButton saveButton;
        saveButton = (FloatingActionButton) v.findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        return v;
    }

    public void transparency(int trans) {
        v.findViewById(R.id.frag3imageview).setAlpha(1 - (float) trans / 100);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.selectphotobutton) {
            Log.e("Frag3", "ButtonPressed");
            ((MainActivity) getActivity()).gotoFrag2();
        }
        if (id == R.id.fix_switch) {
            if (fix) {
                ((MainActivity) getActivity()).fixviewpager(fix = false);
            } else {
                ((MainActivity) getActivity()).fixviewpager(fix = true);
            }
        }

        if (id == R.id.evaluation) {
            Log.e("Frag3", "Evaluation");
            if(selectedphoto != null){
                drawingView.buildDrawingCache();
                Bitmap drawbitmap = drawingView.getDrawingCache();
                double result = evalBitmap(drawbitmap, selectedphoto);
                Log.e("Evaluated : ", Double.toString(result));
                drawingView.destroyDrawingCache();
                ((MainActivity)getActivity()).makescorepopup(result);
            }
        }
        if (id == R.id.gyromode) {
            Log.e("Frag3", "Gyro Mode Button Pressed");
            if (isGmode) {
                isGmode = false;
                drawingView.setIsGmode(false);
                startandstop.setText("START");
            }else {
                isGmode = true;
                drawingView.setIsGmode(true);
                startandstop.setText("STOP");
            }
        }

        switch (id) {
            case R.id.resetBtn:
                drawingView.reset();
                break;
            case R.id.saveBtn:
                drawingView.save(getContext()); // may need to fix
                break;
            case R.id.showscores:
                ((MainActivity) getActivity()).makescorelistpopup();
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

    public void setAcceleration(int acc){
        float a = (float) acc/5;
        drawingView.setacc(a);
    }

    class visibilitySwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                ((MainActivity) getActivity()).fixviewpager(fix = false);
            } else {
                ((MainActivity) getActivity()).fixviewpager(fix = true);
            }
        }
    }

    public double evalBitmap(Bitmap bitmap1, Bitmap bitmap2){
        int width = bitmap1.getWidth() > bitmap2.getWidth() ? bitmap2.getWidth() : bitmap1.getWidth();
        int height = bitmap1.getHeight() > bitmap2.getHeight() ? bitmap2.getHeight() : bitmap1.getHeight();
        Bitmap b1 = Bitmap.createScaledBitmap(bitmap1, width, height, true);
        Bitmap b2 = Bitmap.createScaledBitmap(bitmap2, width, height, true);
        ArrayList<Integer> arr1 = new ArrayList<Integer>();
        ArrayList<Integer> arr2 = new ArrayList<Integer>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                arr1.add(b1.getPixel(x,y));
                arr2.add(b2.getPixel(x,y));
            }
        }
        Integer[] array1 = arr1.toArray(new Integer[0]);
        Integer[] array2 = arr2.toArray(new Integer[0]);
        double cos = GetCosineSimilarity(array1, array2);
        double Score = (1-cos)*10000;
        return Score;
    }
    public double GetCosineSimilarity(Integer[] array1, Integer[] array2){
        if(array1.length!= array2.length){
            Log.e("Diff Lengtherror", "error occured");
            return (float)0.0;
        }
        double dotproduct = 0;
        double array1rms = 0;
        double array2rms = 0;
        double cos = 0;
        double argb1 = 0;
        double argb2 = 0;
        for(int i=0; i<array1.length; i++) {
            for (int j = 0; j < 4; j++) {
                argb1 = (double) ((array1[i] & (0x000000ff << (j * 2 * 8))) >> (j * 2 * 8));
                argb2 = (double) ((array2[i] & (0x000000ff << (j * 2 * 8))) >> (j * 2 * 8));
                argb1 /= (double) 256;
                argb2 /= (double) 256;
                argb1 = 1 - argb1;
                argb2 = 1 - argb2;
                dotproduct += argb1 * argb2;
                array1rms += argb1 * argb1;
                array2rms += argb2 * argb2;
//                System.out.println(argb1 + ", " + argb2 + ", " + dotproduct + ", " + array1rms + ", " + array2rms);
            }

        }
        System.out.println(dotproduct + ", " + array1rms + ", " + array2rms);
        array1rms = Math.sqrt(array1rms);
        array2rms = Math.sqrt(array2rms);
        cos = (double)dotproduct / (array1rms * array2rms);
        return cos;
    }
}