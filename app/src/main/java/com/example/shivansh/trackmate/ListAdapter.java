package com.example.shivansh.trackmate;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private ArrayList<Requests> mDataset;
    private Context mContext;
    private FirebaseAuth auth;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView regarding;
        TextView name;
        TextView roll;
        TextView date;
        TextView time;
        TextView description;
        Button accept;
        TextView status;
        Button reject;
        MyViewHolder(View v) {
            super(v);
            this.regarding = v.findViewById(R.id.regarding);
            this.name = v.findViewById(R.id.stu_name);
            this.roll = v.findViewById(R.id.stu_roll);
            this.status = v.findViewById(R.id.status);
            this.date = v.findViewById(R.id.date);
            this.time=v.findViewById(R.id.time);
            this.description=v.findViewById(R.id.send_string);
            this.accept = v.findViewById(R.id.button_accept);
            this.reject = v.findViewById(R.id.button_reject);
        }
    }

    public ListAdapter(ArrayList<Requests> myDataset, Context context) {
        mDataset = myDataset;
        mContext=context;
    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i) {
        final int a = i;
        auth = FirebaseAuth.getInstance();
        if (mDataset.get(i).getStatus().equals("pending")) {
            holder.reject.setVisibility(View.VISIBLE);
            holder.accept.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.GONE);
        } else if (mDataset.get(i).getStatus().equals("approved")) {
            holder.reject.setVisibility(View.GONE);
            holder.accept.setVisibility(View.GONE);
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setText("APPROVED");
            holder.status.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        } else if(mDataset.get(i).getStatus().equals("declined")) {
            holder.reject.setVisibility(View.GONE);
            holder.accept.setVisibility(View.GONE);
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setText("DECLINED");
            holder.status.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
        }
        holder.regarding.setText("Regarding :"+mDataset.get(i).getRegarding());
        holder.name.setText("From :"+mDataset.get(i).getStudent());
        holder.roll.setText("Roll : IIT2017097");
        holder.date.setText("Date : "+mDataset.get(i).getSendDate());
        holder.time.setText("Time : "+mDataset.get(i).getSendTime());
        holder.description.setText(mDataset.get(i).getSend());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.reject.setVisibility(View.GONE);
                holder.accept.setVisibility(View.GONE);
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText("APPROVED");
                holder.status.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("request");
                Log.e("dadajeehere",auth.getCurrentUser().getEmail());
                mDatabase.orderByChild("professor").equalTo(auth.getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Log.e("check total", String.valueOf(snapshot.getChildrenCount()));
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Requests trans = postSnapshot.getValue(Requests.class);
                            if(trans.getSend().equals(mDataset.get(a).getSend())) {
                                postSnapshot.getRef().child("status").setValue("approved");
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                    }
                });
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.reject.setVisibility(View.GONE);
                holder.accept.setVisibility(View.GONE);
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText("DECLINED");
                holder.status.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("request");
                mDatabase.orderByChild("professor").equalTo(auth.getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            postSnapshot.getRef().child("status").setValue("declined");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}