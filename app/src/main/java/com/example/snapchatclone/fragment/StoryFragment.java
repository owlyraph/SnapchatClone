package com.example.snapchatclone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snapchatclone.R;
import com.example.snapchatclone.UserInformation;
import com.example.snapchatclone.recyclerViewStory.StoryAdapter;
import com.example.snapchatclone.recyclerViewStory.StoryObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static StoryFragment newInstance(){
        StoryFragment fragment= new StoryFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_story ,container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StoryAdapter(getDataSet(), getContext());
        mRecyclerView.setAdapter(mAdapter);

        Button mRefresh = view.findViewById(R.id.refresh);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                listenForData();
            }
        });

        return view;
    }
    private void clear() {
        int size = this.results.size();
        this.results.clear();
        mAdapter.notifyItemRangeChanged(0, size);
    }

    private ArrayList<StoryObject> results=new ArrayList<>();
    private ArrayList<StoryObject> getDataSet() {
        listenForData();
        return results;
    }

    private void listenForData() {
            for (int i = 0; i < UserInformation.listFollowing.size(); i++) {
                DatabaseReference followingStoryDb = FirebaseDatabase.getInstance().getReference().child("users").child(UserInformation.listFollowing.get(i));
                followingStoryDb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {String email = snapshot.child("email").getValue().toString();
                        String uid = snapshot.getRef().getKey();
                        long timestampBeg = 0;
                        long timestampEnd = 0;
                        for(DataSnapshot storySnapshot : snapshot.child("story").getChildren()){
                            if(storySnapshot.child("timestampBeg").getValue() != null){
                                timestampBeg = Long.parseLong(storySnapshot.child("timestampBeg").getValue().toString());
                            }
                            if(storySnapshot.child("timestampEnd").getValue() != null){
                                timestampEnd = Long.parseLong(storySnapshot.child("timestampEnd").getValue().toString());
                            }
                            long timestampCurrent = System.currentTimeMillis();
                            if(timestampCurrent >= timestampBeg && timestampCurrent <= timestampEnd){
                                StoryObject obj = new StoryObject(email, uid, "story");
                                if(!results.contains(obj)){
                                    results.add(obj);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
    }
}