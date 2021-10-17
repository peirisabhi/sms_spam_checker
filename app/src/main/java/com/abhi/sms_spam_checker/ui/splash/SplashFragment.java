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

import com.abhi.sms_spam_checker.MainActivity;
import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.databinding.FragmentSplashBinding;
import com.abhi.sms_spam_checker.db.AppStore;
import com.abhi.sms_spam_checker.db.UserStore;
import com.abhi.sms_spam_checker.model.User;

import java.util.ArrayList;


public class SplashFragment extends Fragment {

    FragmentSplashBinding binding;

    UserStore userStore;
    AppStore appStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);


        if(appStore == null){
            appStore  = new AppStore(getActivity());
        }

        if(userStore == null){
            userStore  = new UserStore(getActivity());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                appStore.open();
                int count = appStore.getCount();
                System.out.println("count " + count);
                appStore.close();

                if(count == 0){
                    appStore.open();
                    appStore.insertNew();
                    appStore.close();

                    NavDirections navDirections = SplashFragmentDirections.actionSplashFragmentToOnboardingFragment();
                    Navigation.findNavController(view).navigate(navDirections);
                }else {

                    userStore.open();
                    ArrayList<User> user = userStore.getUser();
                    userStore.close();

                    if(user.size() == 0) {
                        NavDirections action = SplashFragmentDirections.actionSplashFragmentToSignInFragment();
                        Navigation.findNavController(view).navigate(action);
                    }else{
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }




            }
        }, 3000);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}