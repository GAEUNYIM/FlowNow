package com.example.djgeteamproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private PermissionSupport permission;
    private ViewStateAdapter sa;
    private ViewPager2 pa;
    private myFragment1 frag1;
    private myFragment2 frag2;
    private myFragment3 frag3;
    private int pos=0;
    private boolean isSelect=false;
    private DBHelper helper;
    private SQLiteDatabase db;
    private ArrayList<SaveScore> scorelist= new ArrayList<>();
    String USERID = "userid";
    String SCORE = "score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionCheck();
        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);
        FragmentManager fm = getSupportFragmentManager();
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        pa = findViewById(R.id.viewPager);
        sa = new ViewStateAdapter(fm, getLifecycle());
        pa.setAdapter(sa);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pa.setCurrentItem(tab.getPosition());
                Log.d("Tab","Selected"+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("Tab","Unselected"+tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("Tab","Reselected"+tab.getPosition());

            }
        });

        pa.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });
        pa.setCurrentItem(pos);


        helper = new DBHelper(MainActivity.this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);
    }

    private void permissionCheck() {
        if(Build.VERSION.SDK_INT >= 23){
            permission = new PermissionSupport(this, this);

            if(!permission.checkPermission()){
                permission.requestPermission();
            }
        }
    }

    private class ViewStateAdapter extends FragmentStateAdapter {

        public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle){
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Hardcoded in this order, you'll want to use lists and make sure the titles match
            switch (position) {
                case 1:
                    return frag2 = new myFragment2();
                case 2:
                    return frag3 = new myFragment3();
                default:
                    return frag1 = new myFragment1();
            }
        }


        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(!permission.permissionResult(requestCode, permissions, grantResults)) {
            permission.requestPermission();
        }
    }

    public void gotoFrag2(){
        setisSelect(true);
        fixviewpager(false);
        pa.setCurrentItem(1);
    }
    public void gotoFrag3(){
        setisSelect(false);
        fixviewpager(true);
        pa.setCurrentItem(2);
    }
    public boolean getisSelect(){
        return isSelect;
    }

    public void setisSelect(boolean s){
        isSelect = s;
    }

    public void fixviewpager(boolean f){
        pa.setUserInputEnabled(f);
    }

    public void makepopup(String path){
        Bundle filepath = new Bundle();
        filepath.putString("data", path);
        Photoremovefragment pf = Photoremovefragment.getInstance();
        pf.setArguments(filepath);
        pf.show(getSupportFragmentManager(), "photoremove");
    }

    public void makescorepopup(double score){
        Bundle scbundle = new Bundle();
        scbundle.putDouble("score", score);
        Scoreviewpopup pf = Scoreviewpopup.getInstance();
        pf.setArguments(scbundle);
        pf.show(getSupportFragmentManager(), "scoreshow");
    }

    public void makescorelistpopup(){
        Bundle scbundle = new Bundle();
        getlistofscore();
        scbundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) scorelist);
        ScoreListpopup pf = ScoreListpopup.getInstance();
        pf.setArguments(scbundle);
        pf.show(getSupportFragmentManager(), "scorelistpopup");
    }

    public void savescore(double score, String id){
       writeNewUser(id, score);
    }

    private void writeNewUser(String userId, Double score) {
        ContentValues values = new ContentValues();
        values.put(USERID, userId);
        values.put(SCORE, score);
        db.insert("mytable", null, values);
    }

    public void getlistofscore(){
        String[] projection = {
                BaseColumns._ID,
                USERID,
                SCORE
        };
        String sortOrder = SCORE + " DESC";
        scorelist.clear();
        Cursor cursor = db.query(
                "mytable",
                projection,   // 값을 가져올 column name의 배열
                null,   // where 문에 필요한 column
                null,   // where 문에 필요한 value
                null,   // group by를 적용할 column
                null,   // having 절
                sortOrder   // 정렬 방식
        );
        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            Double score = cursor.getDouble(2);
            SaveScore s = new SaveScore(name, score);
            scorelist.add(s);
        }
        cursor.close();
    }

    public void resetfrag1(){
        frag1.onDetach();
        frag1.onAttach(getApplicationContext());
    }

    public void resetfrag2(){
        frag2.onDetach();
        frag2.onAttach(getApplicationContext());
    }
}