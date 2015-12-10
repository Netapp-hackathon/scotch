package com.netapp.scotch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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

/**
 * Created by jay3 on 12/10/2015.
 */

public class ResultActivity extends AppCompatActivity {

    private final String TAG = "ResultActivity";

    ArrayList<Result> results = new ArrayList<Result>();

    private RecyclerView mResultList;
    private ResultListAdapter mResultListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.result_activity_toolbar);
        toolbar.setTitle("Results!");
        setSupportActionBar(toolbar);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mResultList.setLayoutManager(mLayoutManager);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // Reading all ops
        Log.d(TAG, "Reading all ops..");
        List<OpExtended> ops = OpActivity.db.getAllOps();

        for (OpExtended op : ops) {
            Log.d(TAG, op.toString());
            Result res = new Result(op.getopName(), op.getresult(), op.getCreated(), op.getopId());
            results.add(res);
            Log.d(TAG, res.toString());
        }

        mResultListAdapter = new ResultListAdapter(results);
        mResultList.setAdapter(mResultListAdapter);
        progressDialog.hide();

        /*String url = "http://" + getEndpoint(this) + "/api/vsims?token=" + getToken(this);
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
                                JSONArray jsonVsimArray = response.getJSONArray("vsims");
                                if (jsonVsimArray != null) {
                                    int length = jsonVsimArray.length();
                                    for (int i = 0; i < length; i++) {
                                        JSONObject jsonVsim = jsonVsimArray.getJSONObject(i);
                                        Vsim vsim = new Vsim(jsonVsim.getString("vsimName"), jsonVsim.getInt("vsimId"));
                                        Log.d(TAG, vsim.toString());
                                        vsims.add(vsim);
                                    }
                                    Log.d(TAG, "Got " + vsims.size() + " vsims!");
                                    mVsimListAdapter = new VsimListAdapter(vsims);
                                    mVsimList.setAdapter(mVsimListAdapter);
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
        requestQueue.add(jsonRequest);*/
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
