package com.abhi.sms_spam_checker.ui.otpVerification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi.sms_spam_checker.R;
import com.google.firebase.auth.FirebaseAuth;


public class OtpVerificationFragment extends Fragment {

    private String verificationId;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp_verification, container, false);
    }
}