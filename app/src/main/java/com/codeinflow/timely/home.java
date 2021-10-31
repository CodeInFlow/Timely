package com.codeinflow.timely;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
    public static TextView scantext , currentTime;
    String scannedTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        scanbtn = v.findViewById(R.id.btn_scan);
        scantext = v.findViewById(R.id.checkPoint);
        currentTime = v.findViewById(R.id.currentTime);


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