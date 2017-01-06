package com.song.sunset.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.song.sunset.R;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by Song on 2016/9/14 0014.
 * Email:z53520@qq.com
 */
public class FirstActivity extends Activity {

    private RelativeLayout first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        first = (RelativeLayout) findViewById(R.id.id_first);
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                FirstActivity.this.startActivity(new Intent(FirstActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                FirstActivity.this.finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(1000);
        animation.start();
        first.setAnimation(animation);
    }
}
