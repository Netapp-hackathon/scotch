package com.netapp.scotch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op);

        TextView textView = (TextView) findViewById(R.id.token_text);
        Intent i = getIntent();
        textView.setText(i.getStringExtra("token"));
    }
}
