package com.example.djgeteamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
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
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PermissionSupport permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionCheck();

        FragmentManager fm = getSupportFragmentManager();
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        final ViewPager2 pa = findViewById(R.id.viewPager);
        ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle());

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

        public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Hardcoded in this order, you'll want to use lists and make sure the titles match
            switch (position) {
                case 1:
                    return new myFragment2();
                case 2:
                    return new myFragment3();
                default:
                    return new myFragment1();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(!permission.permissionResult(requestCode, permissions, grantResults)) {
            permission.requestPermission();
        }
    }
}