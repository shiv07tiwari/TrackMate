package com.example.shivansh.trackmate;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private ArrayList<Requests> mDataset;
    private Context mContext;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView regarding;
        TextView name;
        TextView roll;
        TextView date;
        TextView time;
        TextView description;
        MyViewHolder(View v) {
            super(v);
            this.regarding = v.findViewById(R.id.regarding);
            this.name = v.findViewById(R.id.stu_name);
            this.roll = v.findViewById(R.id.roll_no);
            this.date = v.findViewById(R.id.date);
            this.time=v.findViewById(R.id.time);
            this.description=v.findViewById(R.id.send_string);
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
    public void onBindViewHolder(MyViewHolder holder, int i) {
        holder.regarding.setText(mDataset.get(i).getRegarding());
        holder.name.setText(mDataset.get(i).getStudent());
        holder.roll.setText(mDataset.get(i).getRoll());
        holder.date.setText(mDataset.get(i).getSendDate());
        holder.time.setText(mDataset.get(i).getSendTime());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}