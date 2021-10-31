package com.codeinflow.timely;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class home extends Fragment {

    ImageButton scanbtn;
    public static TextView scantext;
    String scannedTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        scanbtn = v.findViewById(R.id.btn_scan);
        scantext = v.findViewById(R.id.checkPoint);


        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), scan.class));

            }
        });
        scannedTxt = (String) scantext.getText();
        Log.d("Working", scannedTxt);
        return v;
    }
}