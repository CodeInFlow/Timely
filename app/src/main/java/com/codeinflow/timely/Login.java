package com.codeinflow.timely;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends Fragment {

    EditText et_email, et_pss;
    Button btnLogin;


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        et_email = v.findViewById(R.id.et_email);
        et_pss = v.findViewById(R.id.et_pss);
        btnLogin = v.findViewById(R.id.btn_login);
        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUSer();
            }
        });

        return v;
    }

    private void loginUSer() {
        String loginEmail = et_email.getText().toString();
        String loginPss = et_pss.getText().toString();

        if (loginEmail.isEmpty()) {
            et_email.setError("Email is Required");
            et_pss.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
            et_email.setError("Please Provide valid email");
            et_email.requestFocus();
            return;
        }

        if (loginPss.isEmpty()) {
            et_pss.setError("Password is required");
            et_pss.requestFocus();
        }

        if (loginPss.length() < 6) {
            et_pss.setError("Min Password length should be 6 characters! ");
            et_pss.requestFocus();
            return;
        } else {
            mAuth.signInWithEmailAndPassword(loginEmail, loginPss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        Intent intent = new Intent(getActivity(), Main2Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    } else {
                        Toast.makeText(getActivity(), "Failed to login! Please chech your credentials", Toast.LENGTH_LONG).show();

                    }

                }
            });
        }
    }


}