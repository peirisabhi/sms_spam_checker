package com.abhi.sms_spam_checker.ui.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.abhi.sms_spam_checker.AuthenticateActivity;
import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.databinding.FragmentNotificationsBinding;
import com.abhi.sms_spam_checker.db.UserStore;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default";

    UserStore userStore;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (userStore == null) {
            userStore = new UserStore(getActivity());
        }

//        binding.textView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                System.out.println("clicked");
//
//                try {
//                    NotificationManager notificationManager;
//                    notificationManager = (NotificationManager) requireActivity().getSystemService(NOTIFICATION_SERVICE );
//                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(requireContext(), default_notification_channel_id ) ;
//                    mBuilder.setContentTitle("New Appointement");
//                    mBuilder.setContentText("Test");
//                    mBuilder.setSmallIcon(R.mipmap.ic_launcher) ;
//                    mBuilder.setAutoCancel( true ) ;
//                    if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
//                        int importance = NotificationManager. IMPORTANCE_HIGH ;
//                        NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
//                        mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
//                        assert notificationManager != null;
//                        notificationManager.createNotificationChannel(notificationChannel) ;
//                    }
//                    assert notificationManager != null;
//                    notificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
//
//
//                } catch (Exception e) {
//                    Log.e("TAG", "onReceive error: " + e.getLocalizedMessage());
//                    e.printStackTrace();
//                }
//            }
//        });


        binding.box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userStore.open();
                userStore.drop();
                userStore.close();

                Intent intent = new Intent(requireActivity(), AuthenticateActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}