package com.abhi.sms_spam_checker.ui.signUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.databinding.FragmentSignUpBinding;
import com.abhi.sms_spam_checker.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class SignUpFragment extends Fragment {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private FragmentSignUpBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        binding.linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections navDirections = SignUpFragmentDirections.actionSignUpFragment2ToSignInFragment();
                Navigation.findNavController(binding.getRoot()).navigate(navDirections);
            }
        });


        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString().trim();
                String mobile = binding.editTextMobile.getText().toString().trim();

                if(name.equals("") || name == null){
                    Toast.makeText(requireContext(), "Invalid Name", Toast.LENGTH_SHORT).show();
                }else if(email.equals("") || email == null){
                    Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                }else if(mobile.equals("") || mobile == null){
                    Toast.makeText(requireContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                }else {

                    db.collection("users")
                            .whereEqualTo("email", email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        if(task.getResult().size() > 0){
                                            Toast.makeText(requireContext(), "This Email Already Exists", Toast.LENGTH_SHORT).show();
                                        }else {

                                            db.collection("users")
                                                    .whereEqualTo("mobile", mobile)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                if(task.getResult().size() > 0){
                                                                    Toast.makeText(requireContext(), "This Mobile Number Already Exists", Toast.LENGTH_SHORT).show();
                                                                }else {
                                                                    otpSend(name, email, mobile);
                                                                }
                                                            }
                                                        }
                                                    });

                                        }

                                    } else {
                                        Log.w("LOG", "Error getting documents.", task.getException());

                                        System.out.println("Error getting documents.  " + task.getException());

                                        Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

//                    User user = new User();
//                    user.setFullName(name);
//                    user.setEmail(email);
//                    user.setMobile(mobile);
//                    user.setRegisteredAt(new Date());
//
//                    db.collection("users")
//                            .add(user)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w("TAG", "Error adding document", e);
//                                }
//                            });

                }

//        otpSend();
            }
        });

    }



    private void otpSend(String name, String email, String mobile) {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(requireActivity(), "OTP is successfully send.", Toast.LENGTH_SHORT).show();

                SignUpFragmentDirections.ActionSignUpFragment2ToOtpVerificationFragment verificationFragment = SignUpFragmentDirections.actionSignUpFragment2ToOtpVerificationFragment(name, email, mobile, verificationId);

                Navigation.findNavController(binding.getRoot()).navigate(verificationFragment);

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+94"+mobile)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(requireActivity())
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

}