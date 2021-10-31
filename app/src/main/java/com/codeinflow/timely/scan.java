package com.codeinflow.timely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class scan extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    private FirebaseFirestore db;
    String name, img, shouid, siuid, desig, DCode;
    Map<String, Object> data = new HashMap<>();

    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    String date = df.format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        db = FirebaseFirestore.getInstance();


        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


    }

    @Override
    public void handleResult(Result rawResult) {
        home.scantext.setText(rawResult.getText());
        home.currentTime.setText(date);
        getdata(rawResult);
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    private void addDataToFirestore(Result rawResult) {



        data.put("name", name);
        data.put("DCode", DCode);
        data.put("img", img);
        data.put("place", rawResult.getText().toString());
        data.put("date", date);


        db = FirebaseFirestore.getInstance();
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference dbCourses = db.collection("/Main/" + shouid + "/subinsp/" + siuid + "/const/").document(currentuser);

        dbCourses.set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void getdata(final Result rawResult) {
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
                            img = document.getString("img");
                            shouid = document.getString("shouid");
                            siuid = document.getString("siuid");
                            desig = document.getString("desig");
                            if (desig.length() == 9) {
                                DCode = "1";
                            }
                            if (desig.length() == 14) {
                                DCode = "2";
                            }

                            addDataToFirestore(rawResult);
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