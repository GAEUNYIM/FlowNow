package com.example.djgeteamproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PermissionSupport permission;
    private ViewPager2 viewpager2;
    private ViewStateAdapter sa;

    private myFragment1 frag1;
    private myFragment2 frag2;
    private myFragment3 frag3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionCheck();

        FragmentManager fm = getSupportFragmentManager();
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        final ViewPager2 pa = findViewById(R.id.viewPager);
        viewpager2 = pa;
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

        @Override
        public boolean containsItem(long itemId) {
            return super.containsItem(itemId);
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(!permission.permissionResult(requestCode, permissions, grantResults)) {
            permission.requestPermission();
        }
    }

    public void refreshfrag1(){
        frag1.onDetach();
        frag1.onAttach(getApplicationContext());
    }
}