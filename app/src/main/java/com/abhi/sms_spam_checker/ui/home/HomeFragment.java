package com.abhi.sms_spam_checker.ui.home;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.adapter.SpamAdapter;
import com.abhi.sms_spam_checker.databinding.FragmentHomeBinding;
import com.abhi.sms_spam_checker.db.UserStore;
import com.abhi.sms_spam_checker.model.UrlSpam;
import com.abhi.sms_spam_checker.model.User;
import com.abhi.sms_spam_checker.ui.splash.SplashFragmentDirections;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    KProgressHUD hud;

    RecyclerView spamRecycler;

    private SpamAdapter spamAdapter;

    //    private FirestoreRecyclerAdapter<UrlSpam, SpamHolder> adapter;
    ArrayList<UrlSpam> urlSpams = new ArrayList<>();

    UserStore userStore;
    User loggedUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        userStore.open();
        ArrayList<User> users = userStore.getUser();
        userStore.close();

        if (users.iterator().hasNext()) {
            loggedUser = users.iterator().next();
        }

        hud = KProgressHUD.create(requireActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        loadSpams();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void loadSpams() {
//        hud.show();


        spamRecycler = binding.spamRecycler;
        spamRecycler.setHasFixedSize(true);
        spamRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));

        spamAdapter = new SpamAdapter(urlSpams, requireActivity());
        spamRecycler.setAdapter(spamAdapter);

        spamAdapter.setListener(new SpamAdapter.Listener() {
            @Override
            public void cardOnClick(int position) {

                if (urlSpams.size() != 0) {
                    HomeFragmentDirections.ActionNavigationHomeToSpamDetailsFragment navigationHomeToSpamDetailsFragment = HomeFragmentDirections.actionNavigationHomeToSpamDetailsFragment(urlSpams.get(position));
                    Navigation.findNavController(binding.getRoot()).navigate(navigationHomeToSpamDetailsFragment);
                }

            }
        });

        db.collection("users").document(loggedUser.getUserDocumentId()).collection("url_spams")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<UrlSpam> dbUrlSpams = task.getResult().toObjects(UrlSpam.class);

                            System.out.println("data added");

                            urlSpams.clear();
                            urlSpams.addAll(dbUrlSpams);

                            System.out.println("urlSpams -- " + urlSpams.size());

                            spamAdapter.notifyDataSetChanged();
                        }
                    }
                });


        db.collection("users").document(loggedUser.getUserDocumentId()).collection("url_spams")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        List<UrlSpam> urlSpams = value.toObjects(UrlSpam.class);

                        System.out.println("data changed");


                        urlSpams.clear();
                        urlSpams.addAll(urlSpams);

                        System.out.println("urlSpams -- " + urlSpams.size());

                        spamAdapter.notifyDataSetChanged();
                    }
                });


    }

}