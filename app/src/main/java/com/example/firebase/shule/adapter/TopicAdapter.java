package com.example.firebase.shule.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
