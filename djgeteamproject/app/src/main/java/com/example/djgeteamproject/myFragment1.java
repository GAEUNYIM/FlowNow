package com.example.djgeteamproject;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashSet;


public class myFragment1 extends Fragment {

    private ArrayList<ContactItem> contactslist = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my1, container, false);

        //recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new ContactAdapter(contactslist);

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
        getContactList();
    }

    private void getContactList() {

        contactslist.add(new ContactItem("gaeun", "01050552279"));

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
        contactslist.add(new ContactItem("dongjae", "01071661834"));
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, sortOrder);

        LinkedHashSet<ContactItem> hashlist = new LinkedHashSet<>();
        // Check condition
        if(cursor.moveToFirst()) {
            do {
                ContactItem contactItem = new ContactItem();
                contactItem.setUsername(cursor.getString(0));
                contactItem.setPhonenumber(cursor.getString(1));
                contactItem.setPhoto_id(cursor.getLong(2));
                contactItem.setPerson_id(cursor.getLong(3));
                contactItem.setId(cursor.getInt(4));

                hashlist.add(contactItem);
            } while (cursor.moveToNext());
        }
        ArrayList<ContactItem> contactItems = new ArrayList<ContactItem>(hashlist);
        for (int i=0; i < contactItems.size(); i++) {
            contactItems.get(i).setId(i);
        }
        contactslist = contactItems;
    }
}
