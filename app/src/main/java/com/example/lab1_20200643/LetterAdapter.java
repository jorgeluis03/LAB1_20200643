package com.example.lab1_20200643;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class LetterAdapter extends BaseAdapter {
    private String[] letters;
    private LayoutInflater letterInf;

    public LetterAdapter(Context context){
        letters= new String[26];
        for(int i=0;i<letters.length;i++){
            letters[i]=""+(char)(i+'A');
        }
        letterInf=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MaterialButton btnLetter; // Cambiado a MaterialButton
        if(view==null){
            btnLetter=(MaterialButton) letterInf.inflate(R.layout.letter, viewGroup, false);
        } else {
            btnLetter=(MaterialButton) view;
        }
        btnLetter.setText(letters[i]);
        return btnLetter;
    }
}
