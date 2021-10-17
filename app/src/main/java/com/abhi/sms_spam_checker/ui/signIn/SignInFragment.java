package com.abhi.sms_spam_checker.ui.signIn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abhi.sms_spam_checker.MainActivity;
import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.databinding.FragmentSignInBinding;
import com.abhi.sms_spam_checker.db.UserStore;
import com.abhi.sms_spam_checker.model.User;
import com.abhi.sms_spam_checker.ui.signUp.SignUpFragmentDirections;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SignInFragment extends Fragment {

    FragmentSignInBinding binding;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String verifyId;

    UserStore userStore;

    String userDocId;
    User loggedUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        binding = FragmentSignInBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (userStore == null) {
            userStore = new UserStore(getActivity());
        }

        binding.linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections navDirections = SignInFragmentDirections.actionSignInFragmentToSignUpFragment22();
                Navigation.findNavController(binding.getRoot()).navigate(navDirections);
            }
        });


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


        binding.textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = binding.editTextMobile.getText().toString().trim();

                if (mobile.equals("") || mobile == null) {
                    Toast.makeText(requireContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("users")
                            .whereEqualTo("mobile", mobile)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().size() > 0) {
                                            List<DocumentSnapshot> documents = task.getResult().getDocuments();

                                            List<User> users = task.getResult().toObjects(User.class);

                                            if (documents.iterator().hasNext()) {
                                                userDocId = documents.iterator().next().getId();
                                            }

                                            if (users.iterator().hasNext()) {
                                                loggedUser = users.iterator().next();
                                            }

                                            System.out.println("userDocId -- " + userDocId);

                                            otpSend(mobile);
                                        } else {
                                            Toast.makeText(requireContext(), "This mobile not registers. Please Register!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });

    }


    private void otpSend(String mobile) {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                e.printStackTrace();


                Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                verifyId = verificationId;
                Toast.makeText(requireActivity(), "OTP is successfully send.", Toast.LENGTH_LONG).show();

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+94" + mobile)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(requireActivity())
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
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
            if (verifyId != null) {
                String code = binding.otp1.getText().toString().trim() +
                        binding.otp2.getText().toString().trim() +
                        binding.otp3.getText().toString().trim() +
                        binding.otp4.getText().toString().trim() +
                        binding.otp5.getText().toString().trim() +
                        binding.otp6.getText().toString().trim();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyId, code);
                FirebaseAuth
                        .getInstance()
                        .signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    userStore.open();
                                    User user = new User();
                                    user.setUserDocumentId(userDocId);

                                    if (loggedUser != null) {
                                        user.setEmail(loggedUser.getEmail());
                                        user.setFullName(loggedUser.getFullName());
                                        user.setMobile(loggedUser.getMobile());
                                        user.setRegisteredAt(loggedUser.getRegisteredAt());

                                    }

                                    userStore.insertUser(user);
                                    userStore.close();

                                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    requireActivity().finish();


                                } else {

                                    Toast.makeText(requireContext(), "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        }

    }
}