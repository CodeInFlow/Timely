package com.codeinflow.timely;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codeinflow.timely.Adapter.ConstableAdapter;
import com.codeinflow.timely.Model.ConstableModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeInspector extends Fragment {

    private RecyclerView ConstableRV1,ConstableRV2,ConstableRV3;
    private ArrayList<ConstableModel> ConstableArrayList1,ConstableArrayList2,ConstableArrayList3;
    private ConstableAdapter ConstableRVAdapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_inspector, container, false);


        ConstableRV1 = v.findViewById(R.id.rv_si1);
        ConstableRV2 = v.findViewById(R.id.rv_si2);
        ConstableRV3 = v.findViewById(R.id.rv_si3);
        db = FirebaseFirestore.getInstance();

        ConstableArrayList1 = new ArrayList<>();
        ConstableArrayList2 = new ArrayList<>();
        ConstableArrayList3 = new ArrayList<>();
        ConstableRV1.setHasFixedSize(true);
        ConstableRV1.setLayoutManager(new LinearLayoutManager(getContext()));
        ConstableRVAdapter = new ConstableAdapter(ConstableArrayList1, getActivity());
        ConstableRVAdapter = new ConstableAdapter(ConstableArrayList2, getActivity());
        ConstableRVAdapter = new ConstableAdapter(ConstableArrayList3, getActivity());

        ConstableRV1.setAdapter(ConstableRVAdapter);
        ConstableRV2.setAdapter(ConstableRVAdapter);
        ConstableRV3.setAdapter(ConstableRVAdapter);

        getdata1();
        getdata2();
        getdata3();

        return v;
    }

    private void getdata1() {
        db.collection("/Main/RStsMrzO5URIknJJzwybBJTwGvh1/subinsp/sVCCVlElfnNr2tRGr1R76Rj2pGw2/const").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ConstableModel c = d.toObject(ConstableModel.class);

                                ConstableArrayList1.add(c);
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

    private void getdata2() {
        db.collection("/Main/RStsMrzO5URIknJJzwybBJTwGvh1/subinsp/sVCCVlElfnNr2tRGr1R76Rj2pGw2/const").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ConstableModel c = d.toObject(ConstableModel.class);

                                ConstableArrayList2.add(c);
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

    private void getdata3() {
        db.collection("/Main/RStsMrzO5URIknJJzwybBJTwGvh1/subinsp/sVCCVlElfnNr2tRGr1R76Rj2pGw2/const").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ConstableModel c = d.toObject(ConstableModel.class);

                                ConstableArrayList3.add(c);
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