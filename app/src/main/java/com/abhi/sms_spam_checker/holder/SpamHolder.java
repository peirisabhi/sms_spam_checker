package com.abhi.sms_spam_checker.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;

import com.abhi.sms_spam_checker.R;

import org.jetbrains.annotations.NotNull;

public class SpamHolder extends RecyclerView.ViewHolder{

    public TextView contact, message;
    public CardView cardView;

    public SpamHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        contact = itemView.findViewById(R.id.txtName);
        message = itemView.findViewById(R.id.txtLastMessage);

        cardView = (CardView) itemView;
    }
}
