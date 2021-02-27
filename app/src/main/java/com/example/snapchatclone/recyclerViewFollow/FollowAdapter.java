package com.example.snapchatclone.recyclerViewFollow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snapchatclone.R;
import com.example.snapchatclone.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowViewHolders> {

    private List<FollowObject> usersList;
    private Context context;

    public FollowAdapter(List<FollowObject> usersList, Context context){

        this.usersList=usersList;
        this.context=context;
    }

    @NonNull
    @Override
    public FollowViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_followers_item, null);
        FollowViewHolders rcv=new FollowViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull  final FollowViewHolders holder, int position) {
        holder.mEmail.setText(usersList.get(position).getEmail());

        if (UserInformation.listFollowing.contains(usersList.get(holder.getLayoutPosition()).getUId())){
            holder.mFollow.setText("following");
        }else{
            holder.mFollow.setText("follow");
        }

        holder.mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ambulanceId= FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (UserInformation.listFollowing.contains(usersList.get(holder.getLayoutPosition()).getUId())){
                    holder.mFollow.setText("following");
                    FirebaseDatabase.getInstance().getReference().child("users").child(ambulanceId).child("following").child(usersList.get(holder.getLayoutPosition()).getUId()).setValue(true);
                }else{
                    holder.mFollow.setText("follow");
                    FirebaseDatabase.getInstance().getReference().child("users").child(ambulanceId).child("following").child(usersList.get(holder.getLayoutPosition()).getUId()).removeValue();

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
