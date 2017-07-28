package com.song.sunset.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.widget.TextSwitchView;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Song on 2016/12/2.
 * E-mail:z53520@qq.com
 */

public class TempTestActivity extends BaseActivity {

    private FloatingActionButton button;
    private TextSwitchView textSwitchView;
    private ImageView dot, dotBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);
        button = (FloatingActionButton) findViewById(R.id.button);
        dot = (ImageView) findViewById(R.id.image_dot);
        dotBack = (ImageView) findViewById(R.id.image_dot_back);
        textSwitchView = (TextSwitchView) findViewById(R.id.textswitch);
    }

    public void onTempTestFlowButtonClick(View view) {
        textSwitchView.setTextStillTime(3000);

        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_dot_scale_alpha);
        dot.startAnimation(animationSet);

        test();

        playDot();

//        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
//                R.anim.property_animator);
//        set.setTarget(myObject);
//        set.start();

    }

    private void playDot() {
        Log.i("song", "playDot");
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(dot, "scaleX", 1f, 0.7f, 1.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(dot, "scaleY", 1f, 0.7f, 1.3f, 1f);
        scaleX.setDuration(700);
        scaleY.setDuration(700);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                playBack();
            }
        });
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(scaleX, scaleY);
        set.start();
    }

    private void playBack() {
        Log.i("song", "playDot");
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(dotBack, "scaleX", 1f, 4f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(dotBack, "scaleY", 1f, 4f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(dotBack, "alpha", 0.7f, 0.0f);
        scaleX.setRepeatCount(1);
        scaleY.setRepeatCount(1);
        alpha.setRepeatCount(1);
        scaleX.setDuration(600);
        scaleY.setDuration(600);
        alpha.setDuration(600);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dotBack.setVisibility(View.GONE);
                playDot();
            }
        });
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(scaleX, scaleY, alpha);
        set.start();
        dotBack.setVisibility(View.VISIBLE);
    }

    public void test() {
        Integer[] int01 = {1, 22};
        Integer[] int02 = {333, 4444};
        Integer[] int03 = {55555, 666666};
        ArrayList<Integer[]> list = new ArrayList<>();
        list.add(int01);
        list.add(int02);
        list.add(int03);

        Observable.from(list)
                .flatMap(new Func1<Integer[], Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer[] strings) {
                        return Observable.from(strings);
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer i) {
                        return i.toString();
                    }
                })
                .compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(TempTestActivity.this, "完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(TempTestActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
