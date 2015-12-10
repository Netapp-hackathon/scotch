package com.netapp.scotch;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jay3 on 12/10/2015.
 */
public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder> {

    List<Result> resultList;

    ResultListAdapter(List<Result> results) {
        this.resultList = results;
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        CardView resultListItem;

        TextView resultOpName;
        TextView result;
        TextView resultCreated;
        TextView resultId;

        ResultViewHolder(View itemView) {
            super(itemView);
            resultListItem = (CardView) itemView.findViewById(R.id.result_list_item);

            resultOpName = (TextView) itemView.findViewById(R.id.result_op_name);
            result = (TextView) itemView.findViewById(R.id.result_result);
            resultCreated = (TextView) itemView.findViewById(R.id.result_created);
            resultId = (TextView) itemView.findViewById(R.id.result_id);
        }
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_item, parent, false);
        ResultViewHolder resViewHolder = new ResultViewHolder(v);
        return resViewHolder;
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        holder.resultOpName.setText(resultList.get(position).mresultOpName);
        holder.result.setText(String.valueOf(resultList.get(position).mresult));
        holder.resultCreated.setText(resultList.get(position).mresultCreated);
        holder.resultId.setText(String.valueOf(resultList.get(position).mresultId));
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public void updateResultList(List<Result> results) {
        this.resultList = results;
        notifyDataSetChanged();
    }
}
