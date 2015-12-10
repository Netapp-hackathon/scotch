package com.netapp.scotch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.netapp.scotch.Utils.getEndpoint;
import static com.netapp.scotch.Utils.getToken;

public class OpActivity extends AppCompatActivity {

    private final String TAG = "OpActivity";

    ArrayList<Op> ops = new ArrayList<Op>();

    private RecyclerView mOpList;
    private OpListAdapter mOpListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op);

        Toolbar toolbar = (Toolbar) findViewById(R.id.op_activity_toolbar);
        toolbar.setTitle("Choose your Op!");
        setSupportActionBar(toolbar);

        mOpList = (RecyclerView) findViewById(R.id.op_list);
        mOpList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mOpList.setLayoutManager(mLayoutManager);


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://" + getEndpoint(this) + "/api/ops?token=" + getToken(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONObject err = response.getJSONObject("err");
                            String errMsg = err.getString("errMsg");
                            Integer errNo = err.getInt("errNo");

                            if(errNo == 0) {
                                JSONArray jsonOpArray = response.getJSONArray("ops");
                                if (jsonOpArray != null) {
                                    int length = jsonOpArray.length();
                                    for (int i = 0; i < length; i++) {
                                        JSONObject jsonOp = jsonOpArray.getJSONObject(i);
                                        Op op = new Op(jsonOp.getString("opName"), jsonOp.getString("opDescr"), jsonOp.getInt("opId"));
                                        Log.d(TAG, op.toString());
                                        ops.add(op);
                                    }
                                    Log.d(TAG, "Got " + ops.size() + " ops!");
                                    mOpListAdapter = new OpListAdapter(ops);
                                    mOpList.setAdapter(mOpListAdapter);
                                    progressDialog.hide();
                                }
                            } else {
                                progressDialog.hide();
                                Toast t = Toast.makeText(getBaseContext(), errMsg, Toast.LENGTH_LONG);
                                t.show();
                            }
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
                    }
                });
        requestQueue.add(jsonRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
