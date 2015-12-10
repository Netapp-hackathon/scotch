package com.netapp.scotch;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by shivamk on 09-Dec-15.
 */

public class OpListAdapter extends RecyclerView.Adapter<OpListAdapter.OpViewHolder> {

    final private static String TAG = "OpListAdapter";
    List<Op> ops;

    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

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
    public void onBindViewHolder(final OpViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder" + 1);
        holder.opName.setText(ops.get(position).mOpName);
        holder.opDescription.setText(ops.get(position).mOpDescription);
        holder.opId.setText(String.valueOf(ops.get(position).mOpId));

        holder.opListItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), WorkspaceActivity.class);
                i.putExtra("opId", Integer.parseInt(((TextView) v.findViewById(R.id.op_id)).getText().toString()));
                i.putExtra("opName", ((TextView) v.findViewById(R.id.op_name)).getText().toString());
                v.getContext().startActivity(i);
            }
        });
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
