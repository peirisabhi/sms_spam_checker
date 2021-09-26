package com.abhi.sms_spam_checker.ui.signIn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.databinding.FragmentSignInBinding;

import org.jetbrains.annotations.NotNull;


public class SignInFragment extends Fragment {

    FragmentSignInBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSignInBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections navDirections = SignInFragmentDirections.actionSignInFragmentToSignUpFragment22();
                Navigation.findNavController(binding.getRoot()).navigate(navDirections);
            }
        });
    }
}