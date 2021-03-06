package com.codeinflow.timely;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codeinflow.timely.Adapter.ConstableAdapter;
import com.codeinflow.timely.Model.ConstableModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeSubInsp extends Fragment {

    private RecyclerView ConstableRV;
    private ArrayList<ConstableModel> ConstableArrayList;
    private ConstableAdapter ConstableRVAdapter;
    private FirebaseFirestore db;
    String shouid, currentuser;
    Handler handler = new Handler();
    Runnable runnable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_sub_insp, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }


        ConstableRV = v.findViewById(R.id.rv_si_constlist);
        db = FirebaseFirestore.getInstance();


        ConstableRV.setHasFixedSize(true);
        ConstableRV.setLayoutManager(new LinearLayoutManager(getContext()));

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                getlist();
                handler.postDelayed(runnable, 10000);
            }
        }, 10000);


        getdata();

        return v;
    }

    private void getdata() {
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (currentuser.length() > 5) {
            DocumentReference docRef = db.collection("Users").document(currentuser);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            shouid = document.getString("shouid");
                            getlist();
                        } else {
                            Log.d("LOGGER", "Error");
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    private void getlist() {
        ConstableArrayList = new ArrayList<>();
        ConstableRVAdapter = new ConstableAdapter(ConstableArrayList, getActivity());
        ConstableRV.setAdapter(ConstableRVAdapter);

        db.collection("/Main/RStsMrzO5URIknJJzwybBJTwGvh1/subinsp/sVCCVlElfnNr2tRGr1R76Rj2pGw2/const/").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            Log.d("LOGGER", "jjj");
                            for (DocumentSnapshot d : list) {

                                ConstableModel c = d.toObject(ConstableModel.class);

                                ConstableArrayList.add(c);
                            }
                            ConstableRVAdapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}