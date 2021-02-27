package com.example.snapchatclone.recyclerViewReceiver;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snapchatclone.R;

public class ReceiverViewHolders extends RecyclerView.ViewHolder {
    public TextView mEmail;
    public CheckBox mReceive;

    public ReceiverViewHolders(@NonNull View itemView) {
        super(itemView);

        mEmail=itemView.findViewById(R.id.email);
        mReceive=itemView.findViewById(R.id.receive);
    }
}

