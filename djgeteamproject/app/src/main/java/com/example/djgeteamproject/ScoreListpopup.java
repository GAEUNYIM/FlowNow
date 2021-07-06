package com.example.djgeteamproject;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreListpopup extends DialogFragment implements View.OnClickListener{
    private ArrayList<SaveScore> contactslist = new ArrayList<>();
    private RecyclerView recyclerView;
    private ScoreAdapter mAdapter;
    public static ScoreListpopup getInstance(){
        ScoreListpopup p = new ScoreListpopup();
        return p;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.showscorespopup, container);

        Bundle bd = getArguments();
        ArrayList<SaveScore> contactslist = bd.getParcelableArrayList("list");


        recyclerView = (RecyclerView) v.findViewById(R.id.scorepopuprecyclerview);
        recyclerView.setHasFixedSize(true);
        mAdapter = new ScoreAdapter(contactslist);
        Button closebutton = (Button) v.findViewById(R.id.scorelistclosebutton);
        closebutton.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scorelistclosebutton:
                dismiss();
        }
    }
}
