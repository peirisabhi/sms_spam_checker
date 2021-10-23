package com.abhi.sms_spam_checker.ui.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi.sms_spam_checker.adapter.ContactAdapter;
import com.abhi.sms_spam_checker.adapter.SpamAdapter;
import com.abhi.sms_spam_checker.databinding.FragmentContactBinding;
import com.abhi.sms_spam_checker.db.AppStore;
import com.abhi.sms_spam_checker.db.UserStore;
import com.abhi.sms_spam_checker.model.ContactsInfo;
import com.abhi.sms_spam_checker.model.SpamWord;
import com.abhi.sms_spam_checker.model.UrlSpam;
import com.abhi.sms_spam_checker.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    private ContactViewModel contactViewModel;
    private FragmentContactBinding binding;
    FirebaseFirestore db;

    UserStore userStore;
    AppStore appStore;
    User loggedUser;

    RecyclerView contactRecycler;

    private ContactAdapter contactAdapter;

    List<ContactsInfo> contactsInfos = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactViewModel =
                new ViewModelProvider(this).get(ContactViewModel.class);

        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//
//        final TextView textView = binding.textDashboard;
//        contactViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        db = FirebaseFirestore.getInstance();

        if (appStore == null) {
            appStore = new AppStore(getActivity());
        }

        if (userStore == null) {
            userStore = new UserStore(getActivity());
        }

        userStore.open();
        ArrayList<User> users = userStore.getUser();
        userStore.close();

        if (users.iterator().hasNext()) {
            loggedUser = users.iterator().next();
        }

        contactRecycler = binding.contactRecycler;
        contactRecycler.setHasFixedSize(true);
        contactRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));

        contactAdapter = new ContactAdapter(contactsInfos, requireActivity());
        contactRecycler.setAdapter(contactAdapter);

        checkContactAlreadyExported();

//        readFile();

//        String[] textList = "congrats! congratulations! selected visit stop thanks gift card hours Don't miss Join terms apply no-cost discount lucky login customer account credit received receive buy Send ringtone text tone free sms reply mobile Accident entitled records pension pounds dollars claim msg compensation opt Txt win won uk voucher cash 150p send entry prize guaranteed urgent todays today valid draw Please message voicemail waiting call delivery immediately Dating service contacted find guess statement points private Mins video camera orange latest phone camcorder Help debt credit info government loans solution bills Naughty ring alone chat heard luv home Find secret admirer special looking 1st 2nd 3rd week top winner award awarded draw contact weekly holiday collect t&c offers offer sexy rate services plz pls good click bonus subscription charge charges charged must vouchers ".split(" ");
//
//
//        int i = 1;
//        for (String s : textList){
//            System.out.println(s);
//
//            SpamWord spamWord = new SpamWord();
//            spamWord.setId(i);
//            spamWord.setStatus(true);
//            spamWord.setWord(s);
//
//            db.collection("spam_words")
//                    .add(spamWord)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//
//                            System.out.println("spam word saved");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w("TAG", "Error adding document", e);
//                        }
//                    });
//        }
//
//        i++;

    }


    private void checkContactAlreadyExported() {
        db.collection("users")
                .document(loggedUser.getUserDocumentId())
                .collection("contacts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ContactsInfo> contactsInfoList = queryDocumentSnapshots.toObjects(ContactsInfo.class);
                        System.out.println("contactsInfoList --" + contactsInfoList.size());
                        if (contactsInfoList.size() == 0) {
                            exportContacts();
                        } else {
                            contactsInfos.clear();
                            contactsInfos.addAll(contactsInfoList);
                            contactAdapter.notifyDataSetChanged();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void exportContacts() {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        String contactId = null;
        String displayName = null;
        List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();
        Cursor cursor = requireActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        System.out.println("cursor.getCount() -- " + cursor.getCount());

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    ContactsInfo contactsInfo = new ContactsInfo();
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactsInfo.setContactId(contactId);
                    contactsInfo.setDisplayName(displayName);

                    Cursor phoneCursor = requireActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactsInfo.setPhoneNumber(phoneNumber);
                    }

                    System.out.println(contactsInfo.toString());

                    phoneCursor.close();

                    contactsInfoList.add(contactsInfo);
                }
            }
        }
        cursor.close();


        for (ContactsInfo contactsInfo : contactsInfoList) {
            db.collection("users")
                    .document(loggedUser.getUserDocumentId())
                    .collection("contacts")
                    .add(contactsInfo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            System.out.println("contact saved");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error adding document", e);
                        }
                    });
        }

        contactsInfos.clear();
        contactsInfos.addAll(contactsInfoList);
        contactAdapter.notifyDataSetChanged();

    }


//    private ArrayList getAllContacts() {
//        ArrayList<String> nameList = new ArrayList<>();
//        ContentResolver cr = requireActivity().getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, null);
//        if ((cur != null ? cur.getCount() : 0) > 0) {
//            while (cur != null && cur.moveToNext()) {
//                String id = cur.getString(
//                        cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(
//                        ContactsContract.Contacts.DISPLAY_NAME));
//                nameList.add(name);
//                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
//                    Cursor pCur = cr.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//                    while (pCur.moveToNext()) {
//                        String phoneNo = pCur.getString(pCur.getColumnIndex(
//                                ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    }
//                    pCur.close();
//                }
//            }
//        }
//        if (cur != null) {
//            cur.close();
//        }
//        return nameList;
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}