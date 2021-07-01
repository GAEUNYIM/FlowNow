package com.example.djgeteamproject;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Dictionary;


public class myFragment1 extends Fragment {

    private ArrayList<Dictonary> contactslist = new ArrayList<>();
    private RecyclerView recyclerView;
    private DicAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my1, container, false);

        //recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new DicAdapter(contactslist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareData();
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData() {
        contactslist = getContactList();

        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
        contactslist.add(new Dictonary("A","12341234"));
    }

    public ArrayList getContactList(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "COLLATE LOCALIZED ASC";

        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);

        if(cursor.moveToFirst()){
            do{
                Dictonary dictonary = new Dictonary(projection[0], projection[1]);
                contactslist.add(dictonary);
            } while (cursor.moveToNext());
        }
        ArrayList<Dictonary> dictonarys = new ArrayList<>();
        for (int i=0; i< dictonarys.size(); i++){
            dictonarys.get(i).setId(i);
        }
        return dictonarys;
    };
}
