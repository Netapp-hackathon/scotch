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

public class VsimListAdapter extends RecyclerView.Adapter<VsimListAdapter.VsimViewHolder> {

    List<Vsim> vsims;

    VsimListAdapter(List<Vsim> vsims) {
        this.vsims = vsims;
    }

    public static class VsimViewHolder extends RecyclerView.ViewHolder {
        CardView vsimListItem;
        TextView vsimName;
        TextView vsimId;

        VsimViewHolder(View itemView) {
            super(itemView);
            vsimListItem = (CardView) itemView.findViewById(R.id.vsim_list_item);
            vsimName = (TextView) itemView.findViewById(R.id.vsim_name);
            vsimId = (TextView) itemView.findViewById(R.id.vsim_id);
        }
    }

    @Override
    public VsimViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vsim_list_item, parent, false);
        VsimViewHolder vsimViewHolder = new VsimViewHolder(v);
        return vsimViewHolder;
    }

    @Override
    public void onBindViewHolder(VsimViewHolder holder, int position) {
        holder.vsimName.setText(vsims.get(position).vsimName);
        holder.vsimId.setText(String.valueOf(vsims.get(position).vsimId));
    }

    @Override
    public int getItemCount() {
        return vsims.size();
    }

    public void updateVsimsList(List<Vsim> vsims) {
        this.vsims = vsims;
        notifyDataSetChanged();
    }
}
