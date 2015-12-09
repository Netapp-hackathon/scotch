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

public class WorkspaceListAdapter extends RecyclerView.Adapter<WorkspaceListAdapter.WorkspaceViewHolder> {

    List<Workspace> workspaces;

    WorkspaceListAdapter(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public static class WorkspaceViewHolder extends RecyclerView.ViewHolder {
        CardView workspaceListItem;
        TextView workspaceName;
        TextView workspaceId;

        WorkspaceViewHolder(View itemView) {
            super(itemView);
            workspaceListItem = (CardView) itemView.findViewById(R.id.workspace_list_item);
            workspaceName = (TextView) itemView.findViewById(R.id.workspace_name);
            workspaceId = (TextView) itemView.findViewById(R.id.workspace_id);
        }
    }

    @Override
    public WorkspaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workspace_list_item, parent, false);
        WorkspaceViewHolder workspaceViewHolder = new WorkspaceViewHolder(v);
        return workspaceViewHolder;
    }

    @Override
    public void onBindViewHolder(WorkspaceViewHolder holder, int position) {
        holder.workspaceName.setText(workspaces.get(position).workspaceName);
        holder.workspaceId.setText(String.valueOf(workspaces.get(position).workspaceId));
    }

    @Override
    public int getItemCount() {
        return workspaces.size();
    }

    public void updateWorkspacesList(List<Workspace> workspaces) {
        this.workspaces = workspaces;
        notifyDataSetChanged();
    }
}
