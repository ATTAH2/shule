package com.example.firebase.shule.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.shule.R;
import com.example.firebase.shule.activity.QuestionActivity;
import com.example.firebase.shule.model.Question;
import com.example.firebase.shule.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.DealViewHolder> {

    ArrayList<Question> dealList;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public QuestionAdapter() {

        listenToFB();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Question question;
                try {
                    question = snapshot.getValue(Question.class);
                } catch (RuntimeException e) {
                    Log.e("error.initializing.traveldeal", e.getMessage(), e);
                    throw new RuntimeException(e.getMessage(), e);
                }

                Log.i("Deal: ", question.getTitle());
                question.setId(snapshot.getKey());
                dealList.add(question);
                notifyItemInserted(dealList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        try {
            view = LayoutInflater.from(context).inflate(R.layout.rv_row, parent, false);
        } catch (RuntimeException e) {
            Log.e("error.inflating.view", e.getMessage(), e);
            throw new RuntimeException("error.inflating.view", e);
        }
        return new DealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        Question deal = dealList.get(position);
        holder.bind(deal);
    }

    @Override
    public int getItemCount() {
        return dealList.size();
    }

    private void listenToFB() {
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        dealList = FirebaseUtil.deals;
    }

    public class DealViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView tvTitle;
        TextView tvPrice;
        TextView tvDescription;
        ImageView ivImageDeal;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.row_title);
            tvPrice = (TextView) itemView.findViewById(R.id.row_price);
            tvDescription = (TextView) itemView.findViewById(R.id.row_description);
            ivImageDeal = (ImageView) itemView.findViewById(R.id.row_image);

            itemView.setOnClickListener(this);
        }

        public void bind (Question question) {
            tvTitle.setText(question.getTitle());
            tvPrice.setText(question.getPrice());
            tvDescription.setText(question.getDescription());
            if (question.getImageUri() != null && !question.getImageUri().isEmpty()) {
                showImage(question.getImageUri());
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("Position: ", String.valueOf(position));
            Question question = dealList.get(position);
            Intent intent = new Intent(v.getContext(), QuestionActivity.class);
            intent.putExtra("Deal", question);
            v.getContext().startActivity(intent);
        }

        private void showImage(String url) {
            if (url != null && !url.isEmpty()) {
                Log.d("Image: ", url);
                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                Picasso.with(ivImageDeal.getContext())
                        .load(url)
                        .resize(120,120)
                        .centerCrop()
                        .into(ivImageDeal);
            }
        }
    }
}