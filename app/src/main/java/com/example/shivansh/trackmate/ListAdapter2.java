package com.example.shivansh.trackmate;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.MyViewHolder> {

    private ArrayList<Requests> mDataset;
    private Context mContext;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView status;
        TextView professor;
        TextView date;
        TextView time;
        TextView description;
        MyViewHolder(View v) {
            super(v);
            this.status = v.findViewById(R.id.status);
            this.professor = v.findViewById(R.id.pro_name);
            this.date = v.findViewById(R.id.date);
            this.time=v.findViewById(R.id.time);
            this.description=v.findViewById(R.id.rec_string);
        }
    }

    public ListAdapter2(ArrayList<Requests> myDataset, Context context) {
        mDataset = myDataset;
        mContext=context;
    }

    @Override
    public ListAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        String status = mDataset.get(i).getStatus();
        if(status.equals("pending")) {
            holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
            holder.status.setText("Status : Pending");
        } else if(status.equals("declined")) {
            holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.status.setText("Status : Declined");
        } else if(status.equals("approved")) {
            holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            holder.status.setText("Status : Approved");
        }
        holder.status.setText(mDataset.get(i).getStatus());
        holder.professor.setText(mDataset.get(i).getProfessor());
        holder.description.setText(mDataset.get(i).getReieve());
        holder.date.setText(mDataset.get(i).getSendDate());
        holder.time.setText(mDataset.get(i).getSendTime());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}