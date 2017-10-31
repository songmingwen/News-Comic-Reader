package com.song.sunset.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.utils.DateTimeUtils;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.widget.TextSwitchView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.SoftReference;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    private ImageView dot, dotBack, ic_svg;
    private AnimatorSet mAnimatorSet;
    private boolean first;
    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private ArrayList<String> options1Items = new ArrayList<>();

    @IntDef({Days.MON, Days.TUE, Days.WEN, Days.THU, Days.FRI, Days.SAT, Days.SUN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Days {
        int MON = 1;
        int TUE = 2;
        int WEN = 3;
        int THU = 4;
        int FRI = 5;
        int SAT = 6;
        int SUN = 7;
    }

    //先定义 常量
    public static final String SUNDAY = "星期日";
    public static final String MONDAY = "星期一";
    public static final String TUESDAY = "星期二";
    public static final String WEDNESDAY = "星期三";
    public static final String THURSDAY = "星期四";
    public static final String FRIDAY = "星期五";
    public static final String SATURDAY = "星期六";

    //用 @IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
    @StringDef({SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeekDays {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);
        button = (FloatingActionButton) findViewById(R.id.button);
        dot = (ImageView) findViewById(R.id.image_dot);
        dotBack = (ImageView) findViewById(R.id.image_dot_back);
//        ic_svg = (ImageView) findViewById(R.id.ic_svg);
        textSwitchView = (TextSwitchView) findViewById(R.id.textswitch);
        initAnimator();
        first = true;
        initTimePicker();
        initOptionsPicker();
    }

    public void onTempTestFlowButtonClick(View view) {
//        textSwitchView.setTextStillTime(3000);

//        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_dot_scale_alpha);
//        dot.startAnimation(animationSet);

//        test();

//        playSvg();
//        playDot();
//        playOrStopAnim();

//        showMessageFormat();

//        pvTime.show();
//        pvOptions.show();


        String string = "我是通过软引用获取的值";
        SoftReference<String> softReference = new SoftReference<>(string);

        String string_1 = softReference.get();
        Snackbar.make(view, string_1, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void showMessageFormat() {
        Toast.makeText(this, MessageFormat.format("{0}的作者是{1}", "本App", "Song"), Toast.LENGTH_SHORT).show();
    }

    private void playOrStopAnim() {
        if (first) {
            first = false;
            mAnimatorSet.removeAllListeners();
        } else {
            first = true;
            addListener();
            mAnimatorSet.start();
        }
    }

    private void playSvg() {
        Drawable drawable = ic_svg.getDrawable();
        if (drawable instanceof Animatable) {
            Animatable able = (Animatable) drawable;
            able.start();
        }
    }

    private void initAnimator() {
        AnimatorSet dotSet = new AnimatorSet();
        ObjectAnimator scaleDotX = ObjectAnimator.ofFloat(dot, "scaleX", 1f, 1f, 1f, 1f, 0.7f, 1.3f, 1f);
        ObjectAnimator scaleDotY = ObjectAnimator.ofFloat(dot, "scaleY", 1f, 1f, 1f, 1f, 0.7f, 1.3f, 1f);
        scaleDotX.setDuration(1200);
        scaleDotY.setDuration(1200);
        dotSet.setInterpolator(new AccelerateDecelerateInterpolator());
        dotSet.playTogether(scaleDotX, scaleDotY);

        AnimatorSet backDotSet = new AnimatorSet();
        ObjectAnimator scaleBackDotX = ObjectAnimator.ofFloat(dotBack, "scaleX", 1f, 4f);
        ObjectAnimator scaleBackDotY = ObjectAnimator.ofFloat(dotBack, "scaleY", 1f, 4f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(dotBack, "alpha", 1.0f, 0.0f);
        scaleBackDotX.setRepeatCount(1);
        scaleBackDotY.setRepeatCount(1);
        alpha.setRepeatCount(1);
        scaleBackDotX.setDuration(600);
        scaleBackDotY.setDuration(600);
        alpha.setDuration(600);
        backDotSet.setInterpolator(new AccelerateDecelerateInterpolator());
        backDotSet.playTogether(scaleBackDotX, scaleBackDotY, alpha);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(dotSet, backDotSet);
        addListener();
        playDot();
    }

    private void addListener() {
        if (mAnimatorSet == null) return;
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i("song", "Cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i("song", "Repeat");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("song", "Start");
            }

            @Override
            public void onAnimationPause(Animator animation) {
                Log.i("song", "Pause");
            }

            @Override
            public void onAnimationResume(Animator animation) {
                Log.i("song", "Resume");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("song", "End");
                playDot();
            }
        });
    }

    /**
     * 执行白点动画
     */
    private void playDot() {
        if (mAnimatorSet == null) {
            return;
        }
        mAnimatorSet.start();
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

    private void initTimePicker() {
        pvTime = new TimePickerView(TempTestActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setRange(1900, 2016);
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setTime(new Date());
        pvTime.setTitle("请选择日期");
        pvTime.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_fade_in), AnimationUtils.loadAnimation(this, R.anim.anim_fade_out));
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
//                Toast.makeText(MainActivity.this, c.get(Calendar.YEAR) + "年" + (1 + c.get(Calendar.MONTH)) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, DateTimeUtils.getConstellation(1 + c.get(Calendar.MONTH), Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT).show();
                Toast.makeText(TempTestActivity.this, DateTimeUtils.getAge(date) + "岁", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initOptionsPicker() {
        pvOptions = new OptionsPickerView(this);
        options1Items.add("男");
        options1Items.add("女");
        pvOptions.setPicker(options1Items);
        pvOptions.setTitle("选择性别");
        pvOptions.setCyclic(true);
        pvOptions.setLabels("");
        pvOptions.setSelectOptions(1);
        pvOptions.setCancelable(true);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                Toast.makeText(TempTestActivity.this, options1 + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 将文本中的表情符号转换为表情图片
     *
     * @param text
     * @return
     */
    public CharSequence replace(Context context, CharSequence text, int upResId, int downResId) {
        // SpannableString连续的字符串，长度不可变，同时可以附加一些object;可变的话使用SpannableStringBuilder，参考sdk文档
        SpannableString ss = new SpannableString("[up_quote]" + text + "[down_quote]");
        // 得到要显示图片的资源
        Drawable upDrawable = context.getResources().getDrawable(upResId);
        Drawable downDrawable = context.getResources().getDrawable(downResId);
        // 设置高度
        upDrawable.setBounds(0, 0, upDrawable.getIntrinsicWidth(), upDrawable.getIntrinsicHeight());
        downDrawable.setBounds(0, 0, downDrawable.getIntrinsicWidth(), downDrawable.getIntrinsicHeight());
        // 跨度底部应与周围文本的基线对齐
        ImageSpan upSpan = new ImageSpan(upDrawable, ImageSpan.ALIGN_BASELINE);
        ImageSpan downSpan = new ImageSpan(downDrawable, ImageSpan.ALIGN_BASELINE);
        // 附加图片
        ss.setSpan(upSpan, 0, "[up_quote]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(downSpan, "[up_quote]".length() + text.length(), "[up_quote]".length() + text.length() + "[down_quote]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
