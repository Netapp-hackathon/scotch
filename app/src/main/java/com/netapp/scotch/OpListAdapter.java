package com.netapp.scotch;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shivamk on 09-Dec-15.
 */

public class OpListAdapter extends RecyclerView.Adapter<OpListAdapter.OpViewHolder> {

    List<Op> ops;

    OpListAdapter(List<Op> ops) {
        this.ops = ops;
    }

    public static class OpViewHolder extends RecyclerView.ViewHolder {
        CardView opListItem;
        TextView opName;
        TextView opDescription;
        TextView opId;

        OpViewHolder(View itemView) {
            super(itemView);
            opListItem = (CardView) itemView.findViewById(R.id.op_list_item);
            opName = (TextView) itemView.findViewById(R.id.op_name);
            opDescription = (TextView) itemView.findViewById(R.id.op_description);
            opId = (TextView) itemView.findViewById(R.id.op_id);
        }
    }

    @Override
    public OpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.op_list_item, parent, false);
        OpViewHolder opViewHolder = new OpViewHolder(v);
        return opViewHolder;
    }

    @Override
    public void onBindViewHolder(OpViewHolder holder, int position) {
        holder.opName.setText(ops.get(position).opName);
        holder.opDescription.setText(ops.get(position).opDescription);
        //holder.opId.setText(ops.get(position).opId);
    }

    @Override
    public int getItemCount() {
        return ops.size();
    }

    public void updateOpsList(List<Op> ops) {
        this.ops = ops;
        notifyDataSetChanged();
    }
}
