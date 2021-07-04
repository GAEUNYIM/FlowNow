package com.example.djgeteamproject;

import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.LinkedHashSet;


public class myFragment1 extends Fragment implements View.OnClickListener{

    private ArrayList<ContactItem> contactslist = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactAdapter mAdapter;
    private Button buttonview;
    private SwipeRefreshLayout refreshview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my1, container, false);

        //recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new ContactAdapter(contactslist);
        buttonview = (Button) v.findViewById(R.id.btn_product_add_product);
        buttonview.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        refreshview = v.findViewById(R.id.refresh_layout);
        refreshview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh() {
                prepareData();
                mAdapter = new ContactAdapter(contactslist);
                recyclerView.setAdapter(mAdapter);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshview.setRefreshing(false);
                    }
                }, 500);
                ((MainActivity)getActivity()).refreshfrag1();

            }
        });

        return v;
    }
    public void onClick(View view){
        int id = view.getId();
        if(id==R.id.btn_product_add_product){
            Log.e("pressed","pressed");
            }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareData();
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void prepareData() {
        getContactList();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getContactList() {
        // Initialize uri
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // String projection
        String[] projection = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone._ID
        };
        // Selection
//        String[] selectionArgs = null;
        // Sort by ascending
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        // Initialize cursor
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, sortOrder);
        // Check condition
        contactslist.clear();
        if(cursor.moveToFirst()) {
            do {
                ContactItem contactItem = new ContactItem();
                contactItem.setUsername(cursor.getString(0));
                contactItem.setPhonenumber(cursor.getString(1));
                contactItem.setPhoto_id(cursor.getLong(2));
                contactItem.setPerson_id(cursor.getLong(3));
                contactItem.setId(cursor.getInt(4));
                contactslist.add(contactItem);
            } while (cursor.moveToNext());
        }
        ArrayList<ContactItem> contactItems = new ArrayList<ContactItem>(contactslist);
        Log.e("Num of contacts", Integer.toString(contactItems.size()));
        for (int i=0; i < contactItems.size(); i++) {
            contactItems.get(i).setId(i);
        }
        contactslist = contactItems;
    }
}
