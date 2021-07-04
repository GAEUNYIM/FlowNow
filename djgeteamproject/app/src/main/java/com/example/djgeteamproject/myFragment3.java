package com.example.djgeteamproject;

import android.os.Bundle;

import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class myFragment3 extends Fragment {

    View v = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my3, container, false);
        View touchscreen= v.findViewById(R.id.frag3view);
        touchscreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float curX = event.getX();
                float curY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.e("touch", "손가락 눌림 : " + curX + ", " + curY);
                        return true;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        Log.e("touch", "손가락 움직임 : " + curX + ", " + curY);
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.e("touch", "손가락 뗌 : " + curX + ", " + curY);
                        return false;
                    }
                    default:
                        return false;
                }
            }});
        return v;
    }

}