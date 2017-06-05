package com.song.sunset.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.views.TextSwitchView;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Song on 2016/12/2.
 * E-mail:z53520@qq.com
 */

public class TempTestActivity extends BaseActivity {

    private FloatingActionButton button;
    private TextSwitchView textSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);
        button = (FloatingActionButton) findViewById(R.id.button);
        textSwitchView = (TextSwitchView) findViewById(R.id.textswitch);
    }

    public void onTempTestFlowButtonClick(View view) {
        textSwitchView.setTextStillTime(3000);
        test();
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
