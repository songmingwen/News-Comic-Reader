/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.subscaleview.sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.song.sunset.R;
import com.song.sunset.activitys.BaseActivity;
import com.subscaleview.sample.animation.AnimationActivity;
import com.subscaleview.sample.basicfeatures.BasicFeaturesActivity;
import com.subscaleview.sample.configuration.ConfigurationActivity;
import com.subscaleview.sample.eventhandling.EventHandlingActivity;
import com.subscaleview.sample.eventhandlingadvanced.AdvancedEventHandlingActivity;
import com.subscaleview.sample.extension.ExtensionActivity;
import com.subscaleview.sample.imagedisplay.ImageDisplayActivity;
import com.subscaleview.sample.viewpager.ViewPagerActivity;

public class SubScaleViewActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setTitle("Subsampling Scale Image View");
        setContentView(R.layout.main);
        findViewById(R.id.basicFeatures).setOnClickListener(this);
        findViewById(R.id.imageDisplay).setOnClickListener(this);
        findViewById(R.id.eventHandling).setOnClickListener(this);
        findViewById(R.id.advancedEventHandling).setOnClickListener(this);
        findViewById(R.id.viewPagerGalleries).setOnClickListener(this);
        findViewById(R.id.animation).setOnClickListener(this);
        findViewById(R.id.extension).setOnClickListener(this);
        findViewById(R.id.configuration).setOnClickListener(this);
        findViewById(R.id.github).setOnClickListener(this);
        findViewById(R.id.self).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.basicFeatures) {
            Intent intent = new Intent(this, BasicFeaturesActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.imageDisplay) {
            Intent intent = new Intent(this, ImageDisplayActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.eventHandling) {
            Intent intent = new Intent(this, EventHandlingActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.advancedEventHandling) {
            Intent intent = new Intent(this, AdvancedEventHandlingActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.viewPagerGalleries) {
            Intent intent = new Intent(this, ViewPagerActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.animation) {
            Intent intent = new Intent(this, AnimationActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.extension) {
            Intent intent = new Intent(this, ExtensionActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.configuration) {
            Intent intent = new Intent(this, ConfigurationActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.github) {
            String url = "https://github.com/davemorrissey/subsampling-scale-image-view";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (view.getId() == R.id.self) {
            String url = "http://www.davemorrissey.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

    }

}
