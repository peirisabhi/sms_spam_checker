package com.abhi.sms_spam_checker.ui.spamDetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.common.ComLib;
import com.abhi.sms_spam_checker.databinding.FragmentSpamDetailsBinding;

import org.jetbrains.annotations.NotNull;


public class SpamDetailsFragment extends Fragment {

   FragmentSpamDetailsBinding binding;
   SpamDetailsFragmentArgs args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSpamDetailsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        args = SpamDetailsFragmentArgs.fromBundle(getArguments());

        try {

            binding.senderName.setText(args.getUrlSpam().getSenderName());
            binding.foundedAt.setText(ComLib.getDate(args.getUrlSpam().getFondedAt()));
            binding.senderNumber.setText(args.getUrlSpam().getSenderNumber());
            binding.maliciousCount.setText(String.valueOf(args.getUrlSpam().getMaliciousCount()));
            binding.harmlessCount.setText(String.valueOf(args.getUrlSpam().getHarmlessCount()));
            binding.suspiciousCount.setText(String.valueOf(args.getUrlSpam().getSuspiciousCount()));
            binding.undetectedCount.setText(String.valueOf(args.getUrlSpam().getUndetectedCount()));
            binding.timeoutCount.setText(String.valueOf(args.getUrlSpam().getTimeoutCount()));
            binding.title.setText(args.getUrlSpam().getTitle());
            binding.mobileValidity.setText(args.getUrlSpam().isMobileValidity() ? "True" : "False");
            binding.international.setText(args.getUrlSpam().getInternational());
            binding.mobileCountry.setText(args.getUrlSpam().getMobileCountry());
            binding.mobileCountryCode.setText(args.getUrlSpam().getMobileCountryCode());
            binding.mobileCountryPrefix.setText(args.getUrlSpam().getMobileCountryPrefix());
            binding.mobileLocation.setText(args.getUrlSpam().getMobileLocation());
            binding.mobileType.setText(args.getUrlSpam().getMobileType());
            binding.mobileCarrier.setText(args.getUrlSpam().getMobileCarrier());
            binding.email.setText(args.getUrlSpam().getEmail());
            binding.emailDeliverability.setText(args.getUrlSpam().isEmailDeliverability() ? "True" : "False");
            binding.email.setText(args.getUrlSpam().getEmail());
            binding.emailQuality.setText(args.getUrlSpam().getEmailQuality());
            binding.emailIsValidFormat.setText(args.getUrlSpam().isEmailIsValidFormat() ? "True" : "False");
            binding.emailIsFreeEmail.setText(args.getUrlSpam().isEmailIsFreeEmail() ? "True" : "False");
            binding.emailIdDisposable.setText(args.getUrlSpam().isEmailIdDisposable()? "True" : "False");
            binding.emailIsRole.setText(args.getUrlSpam().isEmailIsRole() ? "True" : "False");
            binding.emailIsCatchall.setText(args.getUrlSpam().isEmailIsCatchall() ? "True" : "False");
            binding.emailIsMxFound.setText(args.getUrlSpam().isEmailIsMxFound() ? "True" : "False");
            binding.emailIsSmtpValid.setText(args.getUrlSpam().isEmailIsSmtpValid() ? "True" : "False");

        }catch (Exception e ){
            e.printStackTrace();
        }

    }
}