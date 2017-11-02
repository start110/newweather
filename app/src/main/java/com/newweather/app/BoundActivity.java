package com.newweather.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.newweather.app.utils.PrefUtils;

public class BoundActivity extends AppCompatActivity {


    private TextView mBtStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound);

        mBtStart = (TextView) findViewById(R.id.bt_start);
        PrefUtils.putBoolean(BoundActivity.this, "is_user_guide_showed", true);
        mBtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BoundActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
