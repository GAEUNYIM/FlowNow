package com.example.djgeteamproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DrawingView extends View implements View.OnTouchListener{
    private ArrayList<PaintPoint> points = new ArrayList<PaintPoint>();
    private ArrayList<PaintPoint> cursor = new ArrayList<PaintPoint>();
    private int color = Color.BLACK; // 선 색
    private int lineWith = 3; // 선 두께
    private int cursorwith = 20; //커서 사이즈
    private float[] paintvector = {(float)0.0, (float)0.0};
    private SensorManager sensorManager;
    private Sensor accsensor;
    private SensorEventListener acclistener;
    private float acc=10;
    private boolean isGmode = false;
    // View 를 Xml 에서 사용하기 위해선 3가지 생성자를 모두 정의 해주어야함
    // 정의 x -> Runtime Error
    @SuppressLint("ClickableViewAccessibility")
    public DrawingView(Context context) {
        super(context);
        this.setOnTouchListener(this);
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        accsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        acclistener = new accListener();
        sensorManager.registerListener(acclistener,accsensor,sensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("ClickableViewAccessibility")
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        accsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        acclistener = new accListener();
        sensorManager.registerListener(acclistener,accsensor,sensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("ClickableViewAccessibility")
    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnTouchListener(this);
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        accsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        acclistener = new accListener();
        sensorManager.registerListener(acclistener,accsensor,sensorManager.SENSOR_DELAY_FASTEST);
    }

    private class accListener implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (isGmode) {
                //Log.i("SENSOR", "Acceleration changed.");
                //Log.i("SENSOR", "  X: " + paintvector[0]
                //        + ", Y: " + paintvector[1]);
                float accx = -event.values[0];
                float accy = event.values[1];
                paintvector[0] += accx*0.18*acc;
                paintvector[1] += accy*0.18*acc;
                if(paintvector[0]<0) {
                    paintvector[0] = 0;
                }
                else if(paintvector[0]>1000){
                    paintvector[0]=1000;
                }
                if(paintvector[1]<0){
                    paintvector[1] = 0;
                }
                else if(paintvector[1]>800){
                    paintvector[1] = 800;
                }
                Paint p = new Paint();
                p.setColor(color);
                p.setStrokeWidth(lineWith);
                points.add(new PaintPoint(paintvector[0], paintvector[1], true, p));
                cursor.clear();
                Paint cursorp = new Paint();
                cursorp.setColor(color);
                cursorp.setStrokeWidth(cursorwith);
                cursor.add(new PaintPoint(paintvector[0]-10, paintvector[1]-10, true, cursorp));
                cursor.add(new PaintPoint(paintvector[0]+10, paintvector[1]+10, true, cursorp));
                invalidate();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (isGmode) {
            switch (motionEvent.getAction()) {

                case MotionEvent.ACTION_MOVE:
                    // 아래 작업을 안하게 되면 선이 어색하게 그려지는 현상 발생
                    // ex) 점을 찍고 이동한 뒤에 점을 찍는 경우
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN:
                    paintvector[0] = motionEvent.getX();
                    paintvector[1] = motionEvent.getY();
                    cursor.clear();
                    Paint p = new Paint();
                    p.setColor(color);
                    p.setStrokeWidth(cursorwith);
                    cursor.add(new PaintPoint(paintvector[0]-10, paintvector[1]-10, true, p));
                    cursor.add(new PaintPoint(paintvector[0]+10, paintvector[1]+10, true, p));
                    points.add(new PaintPoint(paintvector[0], paintvector[1], false, null));
                    invalidate();
            }
            // System.out.println("DrawingView.onTouch - " + points);
            // return false 로 하게되면 이벤트가 한번 발생하고 종료 -> 점을 그림
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 1; i < points.size(); i++) {
            if (!points.get(i).isDraw()) continue;
            // 선을 그려줌
            // canvas.drawLine( 이전 좌표, 현재 좌표, 선 속성 );
            canvas.drawLine(points.get(i - 1).getX(), points.get(i - 1).getY(), points.get(i).getX(), points.get(i).getY(), points.get(i).getPaint());
        }
        for (int i = 1; i < cursor.size(); i++) {
            if (!cursor.get(i).isDraw()) continue;
            // 선을 그려줌
            // canvas.drawLine( 이전 좌표, 현재 좌표, 선 속성 );
            canvas.drawLine(cursor.get(i - 1).getX(), cursor.get(i - 1).getY(), cursor.get(i).getX(), cursor.get(i).getY(), cursor.get(i).getPaint());
        }
    }

    // Reset Function
    public void reset() {
        points.clear(); // PaintPoint ArrayList Clear
        paintvector[0]=0;
        paintvector[1]=0;
        invalidate(); // 화면을 갱신함 -> onDraw()를 호출
    }

    // Save Function
    public void save(Context context) {
        ArrayList<PaintPoint> savecursor = new ArrayList<PaintPoint>();
        savecursor.add(0, cursor.get(0));
        savecursor.add(1, cursor.get(1));
        cursor.clear();
        invalidate();

        this.setDrawingCacheEnabled(true);
        Bitmap screenshot = this.getDrawingCache();

        // 현재 날짜로 파일을 저장하기
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        String filename = dateString + "_drawImage.png";
        try {
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            if (file.createNewFile())
                Log.d("save", "파일 생성 성공");
            OutputStream outStream = new FileOutputStream(file);
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

            // 갤러리에 변경을 알려줌
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 안드로이드 버전이 Kitkat 이상 일때
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                context.sendBroadcast(mediaScanIntent);
            } else {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            }

            Toast.makeText(context.getApplicationContext(), "저장완료", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cursor.add(savecursor.get(0));
        cursor.add(savecursor.get(1));
        savecursor.clear();
        this.setDrawingCacheEnabled(false);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getLineWith() {
        return lineWith;
    }

    public void setLineWith(int lineWith) {
        this.lineWith = lineWith;
    }

    public void setacc(float acc){
        this.acc = acc;
    }

    public void setIsGmode(boolean flag) { this.isGmode = flag; }
}