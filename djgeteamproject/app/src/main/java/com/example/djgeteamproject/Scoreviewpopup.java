package com.example.djgeteamproject;

import android.os.Bundle;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Scoreviewpopup extends DialogFragment implements View.OnClickListener{
    Double score;
    EditText et;
    public static Scoreviewpopup getInstance(){
        Scoreviewpopup p = new Scoreviewpopup();
        return p;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.scoreviewpopup, container);
        et = (EditText) v.findViewById(R.id.userid);
        Bundle sc = getArguments();
        Button savebutton = v.findViewById(R.id.savescore);
        savebutton.setOnClickListener(this);
        score = sc.getDouble("score");
        if(score==null) score = 0.0;
        TextView tv = v.findViewById(R.id.viewscore);
        String s = Double.toString(score);
        tv.setText(s);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.savescore:
                et.setEnabled(false);
                Editable id = et.getText();
                String stid;
                if(id==null) stid="";
                else stid = id.toString();
                ((MainActivity)getActivity()).savescore(score, stid);
                et.setEnabled(true);
                dismiss();
        }
    }
}
