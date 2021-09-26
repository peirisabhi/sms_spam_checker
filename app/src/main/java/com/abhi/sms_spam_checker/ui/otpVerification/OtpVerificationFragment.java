package com.abhi.sms_spam_checker.ui.otpVerification;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abhi.sms_spam_checker.MainActivity;
import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.databinding.FragmentOtpVerificationBinding;
import com.abhi.sms_spam_checker.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public class OtpVerificationFragment extends Fragment {


    FirebaseFirestore db;
    FragmentOtpVerificationBinding binding;
    OtpVerificationFragmentArgs args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentOtpVerificationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        args = OtpVerificationFragmentArgs.fromBundle(getArguments());

        db = FirebaseFirestore.getInstance();


        binding.otp1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (binding.otp1.getText().toString().length() != 0) {
                    binding.otp2.requestFocus();
                }
                return false;
            }
        });

        binding.otp2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (binding.otp2.getText().toString().length() != 0) {
                    binding.otp3.requestFocus();
                }
                return false;
            }
        });

        binding.otp3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (binding.otp3.getText().toString().length() != 0) {
                    binding.otp4.requestFocus();
                }
                return false;
            }
        });

        binding.otp4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (binding.otp4.getText().toString().length() != 0) {
                    binding.otp5.requestFocus();
                }
                return false;
            }
        });

        binding.otp5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (binding.otp5.getText().toString().length() != 0) {
                    binding.otp6.requestFocus();
                }
                return false;
            }
        });


        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });
    }


    private void verifyOtp() {
        if (binding.otp1.getText().toString().trim().isEmpty() ||
                binding.otp2.getText().toString().trim().isEmpty() ||
                binding.otp3.getText().toString().trim().isEmpty() ||
                binding.otp4.getText().toString().trim().isEmpty() ||
                binding.otp5.getText().toString().trim().isEmpty() ||
                binding.otp6.getText().toString().trim().isEmpty()) {


            Toast.makeText(requireContext(), "OTP is not Valid!", Toast.LENGTH_SHORT).show();
        } else {
            if (args.getVerificationId() != null) {
                String code = binding.otp1.getText().toString().trim() +
                        binding.otp2.getText().toString().trim() +
                        binding.otp3.getText().toString().trim() +
                        binding.otp4.getText().toString().trim() +
                        binding.otp5.getText().toString().trim() +
                        binding.otp6.getText().toString().trim();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(args.getVerificationId(), code);
                FirebaseAuth
                        .getInstance()
                        .signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    User user = new User();
                                    user.setFullName(args.getName());
                                    user.setEmail(args.getEmail());
                                    user.setMobile(args.getMobile());
                                    user.setRegisteredAt(new Date());
                                    user.setStatus(true);

                                    db.collection("users")
                                            .add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(requireContext(), "Verification Success Welcome...", Toast.LENGTH_SHORT).show();
                                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                                                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    requireActivity().finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG", "Error adding document", e);
                                                }
                                            });


                                } else {

                                    Toast.makeText(requireContext(), "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        }

    }

}