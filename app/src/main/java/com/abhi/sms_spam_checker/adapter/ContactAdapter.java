package com.abhi.sms_spam_checker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.model.ContactsInfo;
import com.abhi.sms_spam_checker.model.UrlSpam;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    List<ContactsInfo> contactsInfoList;
    Context context;


    public ContactAdapter(List<ContactsInfo> contactsInfoList, Context context) {
        this.contactsInfoList = contactsInfoList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.name.setText(contactsInfoList.get(position).getDisplayName());
        holder.number.setText(contactsInfoList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactsInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, number;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtName);
            number = itemView.findViewById(R.id.txtLastMessage);

            cardView = (CardView) itemView;
        }
    }
}
