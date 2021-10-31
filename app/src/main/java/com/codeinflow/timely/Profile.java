package com.codeinflow.timely;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


public class Profile extends Fragment {

    private FirebaseFirestore db;
    TextView Name, desig, pnoNum, dob, issueDate, area, p_name;
    ImageView imgPP, sign;
    String a, b, c, d, e, f, g, h, i;
    String currentuser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(Color.parseColor("#66FDCA3E"));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }

        Name = v.findViewById(R.id.pName);
        desig = v.findViewById(R.id.desig);
        pnoNum = v.findViewById(R.id.ph_nos);
        dob = v.findViewById(R.id.dob_nos);
        issueDate = v.findViewById(R.id.issue_date);
        p_name = v.findViewById(R.id.p_name);
        area = v.findViewById(R.id.area);
        imgPP = v.findViewById(R.id.pp_img);
        sign = v.findViewById(R.id.sign);

        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        if (currentuser.length() > 5) {
            DocumentReference docRef = db.collection("Users").document(currentuser);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            a = document.getString("name");
                            b = document.getString("desig");
                            c = document.getString("img");
                            d = document.getString("dob");
                            e = document.getString("issuedate");
                            f = document.getString("shosigdetailsname");
                            g = document.getString("shosigdetails");
                            h = document.getString("pnonum");
                            i = document.getString("sigimg");


                            Name.setText(a);
                            desig.setText(b);
                            pnoNum.setText(h);
                            dob.setText(d);
                            issueDate.setText(e);
                            p_name.setText(f);
                            area.setText(g);
                            Picasso.get().load(c).into(imgPP);
                            Picasso.get().load(i).into(sign);

                        } else {
                            Log.d("LOGGER", "Error");
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });
        }


        return v;
    }
}