package com.codeinflow.timely;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinflow.timely.Adapter.AnnouncementAdapter;
import com.codeinflow.timely.Model.AnnouncementModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Announcement extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView AnnouncementRV;
    private ArrayList<AnnouncementModel> AnnouncementArrayList;
    private AnnouncementAdapter AnnouncementRVAdapter;
    EditText Msg;
    RoundedImageView sendbtn;
    String name;
    Map<String, Object> message = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_announcement, container, false);

        AnnouncementRV = v.findViewById(R.id.rv_announcements);
        db = FirebaseFirestore.getInstance();

        AnnouncementArrayList = new ArrayList<>();
        AnnouncementRV.setHasFixedSize(true);
        AnnouncementRV.setLayoutManager(new LinearLayoutManager(getContext()));

        AnnouncementRVAdapter = new AnnouncementAdapter(AnnouncementArrayList, getActivity());

        AnnouncementRV.setAdapter(AnnouncementRVAdapter);


        Msg = v.findViewById(R.id.msg);
        sendbtn = v.findViewById(R.id.send);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmessage();
            }
        });

        getMessage();

        return v;

    }

    private void sendmessage() {
        getdata();
        message.put("name", name);
        message.put("msg", Msg.getText().toString());
        CollectionReference collectionReference = db.collection("Announcements");
        collectionReference.add(message)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        getMessage();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void getMessage() {

        db.collection("Announcements").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                AnnouncementModel c = d.toObject(AnnouncementModel.class);
                                AnnouncementArrayList.add(c);
                            }
                            AnnouncementRVAdapter.notifyDataSetChanged();
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

    private void getdata() {
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (currentuser.length() > 5) {
            DocumentReference docRef = db.collection("Users").document(currentuser);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            name = document.getString("name");
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

}