package com.abhi.sms_spam_checker.ui.otpVerification;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abhi.sms_spam_checker.MainActivity;
import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.config.Config;
import com.abhi.sms_spam_checker.databinding.FragmentOtpVerificationBinding;
import com.abhi.sms_spam_checker.db.UserStore;
import com.abhi.sms_spam_checker.model.UrlSpam;
import com.abhi.sms_spam_checker.model.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OtpVerificationFragment extends Fragment {


    FirebaseFirestore db;
    FragmentOtpVerificationBinding binding;
    OtpVerificationFragmentArgs args;

    UserStore userStore;

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

        if (userStore == null) {
            userStore = new UserStore(getActivity());
        }

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


        binding.resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("sendVirusTotalRequest ---- ");



//                RequestQueue queue = Volley.newRequestQueue(requireContext());
//
//                JSONObject jsonRequest = new JSONObject();
//                try {
//                    jsonRequest.put("url", "password-buster.com");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(jsonRequest.toString());
//
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.VIRUSTOTAL_CHECK_URL, jsonRequest, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i("Response", "Response is: " + response);
//                        System.out.println(response);
//
//                        String requestId = "";
//
//                        try {
//                            String id = response.getJSONObject("data").getString("id");
//
//                            requestId = id.split("-")[1];
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        if (requestId != "") {
//                            getVirusTotalRequestData(requestId);
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("Response", error.toString());
//                    }
//                });
//
//                queue.add(jsonObjectRequest);

            }
        });

    }


//    private void getVirusTotalRequestData(String requestId) {
//        RequestQueue queue = Volley.newRequestQueue(requireContext());
//
//        JSONObject jsonRequest = new JSONObject();
//        try {
//            jsonRequest.put("request_id", requestId);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(jsonRequest.toString());
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.VIRUSTOTAL_GET_DATA_URL, jsonRequest, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("Response", "Response is: " + response);
//                System.out.println(response);
//
//                try {
//                    int maliciousCount = response.getJSONObject("data").getJSONObject("attributes").getJSONObject("last_analysis_stats").getInt("malicious");
//                    int suspiciousCount = response.getJSONObject("data").getJSONObject("attributes").getJSONObject("last_analysis_stats").getInt("suspicious");
//                    System.out.println("maliciousCount ---- " + maliciousCount);
//                    if(maliciousCount > 0){
//                        UrlSpam urlSpam = new UrlSpam();
//                        urlSpam.setFondedAt(new Date());
//                        urlSpam.setMaliciousCount(maliciousCount);
//                        urlSpam.setRequestId(response.getJSONObject("data").getString("id"));
//                        urlSpam.setTitle(response.getJSONObject("data").getJSONObject("attributes").getString("title"));
//                        urlSpam.setUrl(response.getJSONObject("data").getJSONObject("attributes").getString("url"));
//                        urlSpam.setSenderName("aaa");
//                        urlSpam.setSenderNumber("12121212121");
//
//
//                        saveSpamDetails("ped8xuvp7PdMuf5fU1dm", urlSpam);
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("Response", error.toString());
//            }
//        });
//
//        queue.add(jsonObjectRequest);
//    }
//
//
//    private void saveSpamDetails(String userDocId, UrlSpam urlSpam){
//        db.collection("users")
//                .document(userDocId)
//                .collection("url_spams")
//                .add(urlSpam)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                        System.out.println("url spam saved");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });
//    }


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

                                                    user.setUserDocumentId(documentReference.getId());
                                                    userStore.open();
                                                    userStore.insertUser(user);
                                                    userStore.close();

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