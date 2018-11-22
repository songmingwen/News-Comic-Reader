package com.song.sunset.activitys.temp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.song.sunset.R;

public class FrescoXMLActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, FrescoXMLActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_xml);

    }

}
