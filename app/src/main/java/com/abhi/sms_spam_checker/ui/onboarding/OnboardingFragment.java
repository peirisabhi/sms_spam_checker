package com.abhi.sms_spam_checker.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.adapter.OnboardingAdapter;
import com.abhi.sms_spam_checker.databinding.FragmentOnboardingBinding;
import com.abhi.sms_spam_checker.model.OnboardingItem;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class OnboardingFragment extends Fragment {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout onboardingLayoutIndicator;
    private MaterialButton onboardingActionButton;

    private FragmentOnboardingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOnboardingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onboardingLayoutIndicator = binding.onboardingLayoutIndicator;
        onboardingActionButton = binding.buttonOnbardingAction;

        setUpOnboardingItems();

        ViewPager2 onboardingViewPager = binding.onBoardingViewPager;
        onboardingViewPager.setAdapter(onboardingAdapter);
        setUpOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });


        onboardingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                }else{

//                    OnboardingFragment

                }
            }
        });

    }


    private void setUpOnboardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem item1 = new OnboardingItem();
        item1.setTitle("Welcome");
        item1.setSubTitle("to DoctGo");
        item1.setDescription("Book an appointment with a right doctor..");
        item1.setImg(R.drawable.ic_onboarding_1);

        OnboardingItem item2 = new OnboardingItem();
        item2.setTitle("Ask a Doctor Online");
        item2.setSubTitle("Search Doctors");
        item2.setDescription("Get list of best doctor nearby you..");
        item2.setImg(R.drawable.ic_onboarding_2);


        OnboardingItem item3 = new OnboardingItem();
        item3.setTitle("Physician");
        item3.setSubTitle("on Your Doorstep");
        item3.setDescription("Book Your Physician..");
        item3.setImg(R.drawable.ic_onboarding_3);

        OnboardingItem item4 = new OnboardingItem();
        item4.setTitle("Medicine");
        item4.setSubTitle("on Your Doorstep");
        item4.setDescription("Order Your Medicines..");
        item4.setImg(R.drawable.ic_onboarding_4);

        onboardingItems.add(item1);
        onboardingItems.add(item2);
        onboardingItems.add(item3);
        onboardingItems.add(item4);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }

    private void setUpOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0, 8, 0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(requireContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    requireContext(), R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            onboardingLayoutIndicator.addView(indicators[i]);
        }
    }


    private void setCurrentOnboardingIndicator(int index){
        int childCount = onboardingLayoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) onboardingLayoutIndicator.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.onboarding_indicator_active)
                );
            }else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1){
            onboardingActionButton.setText("Start");
        }else{
            onboardingActionButton.setText("Next");
        }
    }
}