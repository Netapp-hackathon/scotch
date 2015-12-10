package com.netapp.scotch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.netapp.scotch.Utils.getEndpoint;

/**
 * Created by shivamk on 09-Dec-15.
 */

public class WorkspaceListAdapter extends RecyclerView.Adapter<WorkspaceListAdapter.WorkspaceViewHolder> {

    private static String TAG = "WorkspaceListAdapter";

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

        holder.workspaceListItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());

                Intent i = ((Activity) v.getContext()).getIntent();
                final String opName = i.getStringExtra("opName");
                final int opId = i.getIntExtra("opId", 0);
                String workspaceName = ((TextView) v.findViewById(R.id.workspace_name)).getText().toString();
                final int workspaceId = Integer.parseInt(((TextView) v.findViewById(R.id.workspace_id)).getText().toString());
                builder.setTitle("Op Summary");
                builder.setMessage("Are you sure you wanna " + opName + " on " + workspaceName + "?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject opRequest = new JSONObject();
                        try {
                            opRequest.put("opId", opId);
                            opRequest.put("wsId", workspaceId);
                            opRequest.put("token", sharedPreferences.getString("authToken", ""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                        String url = "http://" + getEndpoint(v.getContext()) + "/api/ops";

                        JsonObjectRequest jsonRequest = new JsonObjectRequest
                                (Request.Method.POST, url, opRequest, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // the response is already constructed as a JSONObject!
                                        try {
                                            Log.d(TAG, response.toString());
                                            JSONObject err = response.getJSONObject("err");

                                            String errMsg = err.getString("errMsg");
                                            Integer errNo = err.getInt("errNo");

                                            if(errNo == 0) {
                                                Toast t = Toast.makeText(v.getContext(), "Op submitted successfully!", Toast.LENGTH_LONG);
                                                t.show();
                                                sharedPreferences.edit().putString("opSubmitted", String.valueOf(response.getString("eId"))).commit();

                                                Log.d(TAG, "Inserting in db");
                                                OpActivity.db.addOp(new OpExtended(opId, opName,
                                                        Integer.parseInt(String.valueOf(response.getString("eId"))),
                                                        Utils.constant.IN_PROGRSS));

                                                Intent i = new Intent(v.getContext(), ResultActivity.class);
                                                v.getContext().startActivity(i);

                                            } else {
                                                Toast t = Toast.makeText(v.getContext(), errMsg, Toast.LENGTH_LONG);
                                                t.show();
                                            }
                                            Intent i = new Intent(v.getContext(), OpActivity.class);
                                            v.getContext().startActivity(i);
                                            ((Activity) v.getContext()).finish();
                                        } catch (JSONException e) {
                                            Log.e(TAG, e.toString());
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e(TAG, error.toString());
                                        error.printStackTrace();
                                        Toast t = Toast.makeText(v.getContext(), "Network problem, try again later!", Toast.LENGTH_LONG);
                                        t.show();
                                    }
                                });
                        requestQueue.add(jsonRequest);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "User cancelled operation!" );
                    }
                }).show();
            }
        });
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
