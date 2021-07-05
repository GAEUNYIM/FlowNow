package com.example.djgeteamproject;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.annotation.SuppressLint;
import androidx.viewpager2.widget.ViewPager2;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class myFragment3 extends Fragment implements View.OnClickListener {

    View v = null;
    DrawingView drawingView;
    DrawingView cursor;
    boolean fix = true;
    private boolean isGmode = false; // true at gyro Mode; false at pencil Mode


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my3, container, false);

        Button pModeButton = (Button) v.findViewById(R.id.pencilmode);
        pModeButton.setOnClickListener(this);
        Button gModeButton = (Button) v.findViewById(R.id.gyromode);
        gModeButton.setOnClickListener(this);

        ImageView imgview = v.findViewById(R.id.frag3imageview);
        Button selectbutton = (Button) v.findViewById(R.id.selectphotobutton);
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

        ImageButton[] colorImageButtons = new ImageButton[3];

        colorImageButtons[0] = (ImageButton) v.findViewById(R.id.blackColorBtn);
        colorImageButtons[1] = (ImageButton) v.findViewById(R.id.redColorBtn);
        colorImageButtons[2] = (ImageButton) v.findViewById(R.id.blueColorBtn);
        for (ImageButton colorImageButton : colorImageButtons) {
            colorImageButton.setOnClickListener(this);
        }

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

        if (id == R.id.pencilmode) {
            Log.e("Frag3", "Pencil Mode Button Pressed");
            isGmode = false;
        }
        if (id == R.id.gyromode) {
            Log.e("Frag3", "Gyro Mode Button Pressed");
            if (isGmode) {
                isGmode = false;
                drawingView.setIsGmode(false);
            }else {
                isGmode = true;
                drawingView.setIsGmode(true);
            }

        }

        switch (id) {
            case R.id.blackColorBtn:
                drawingView.setColor(Color.BLACK);
                break;
            case R.id.redColorBtn:
                drawingView.setColor(Color.RED);
                break;
            case R.id.blueColorBtn:
                drawingView.setColor(Color.BLUE);
                break;
            case R.id.resetBtn:
                drawingView.reset();
                break;
            case R.id.saveBtn:
                drawingView.save(getContext()); // may need to fix
                break;
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
                ((MainActivity) getActivity()).fixView();
            } else {
                ((MainActivity) getActivity()).fixviewpager(fix = true);
            }
        }
    }
}