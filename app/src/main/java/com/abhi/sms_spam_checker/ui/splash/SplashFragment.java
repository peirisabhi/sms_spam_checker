package com.abhi.sms_spam_checker.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.databinding.FragmentSplashBinding;


public class SplashFragment extends Fragment {

    FragmentSplashBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


//                Intent intent = new Intent(requireActivity(), OnboardingActivity.class);
//                startActivity(intent);
//                getActivity().finish();

                NavDirections navDirections = SplashFragmentDirections.actionSplashFragmentToSignInFragment();
                Navigation.findNavController(view).navigate(navDirections);

            }
        }, 1000);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}