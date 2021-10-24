package com.abhi.sms_spam_checker.adapter;

import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.model.UrlSpam;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpamAdapter extends RecyclerView.Adapter<SpamAdapter.ViewHolder> {

    List<UrlSpam> urlSpams;
    Context context;
    private SpamAdapter.Listener listener;


    public SpamAdapter(List<UrlSpam> urlSpams, Context context) {
        this.urlSpams = urlSpams;
        this.context = context;
    }

    public interface Listener {
        void cardOnClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_spam_message, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.contact.setText(urlSpams.get(position).getSenderNumber());
        holder.message.setText(urlSpams.get(position).getUrl());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener !=null){
                    listener.cardOnClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return urlSpams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView contact, message;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            contact = itemView.findViewById(R.id.txtName);
            message = itemView.findViewById(R.id.txtLastMessage);

            cardView = (CardView) itemView;
        }
    }
}
