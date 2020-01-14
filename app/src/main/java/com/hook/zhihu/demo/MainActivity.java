package com.hook.zhihu.demo;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.song.sunset.R;
import com.hook.wsx.hookManager.Hook;

import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HookMethod();
                Test test = new Test();
                test.println();
                HookMethod();
                test.println();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }



    private void HookMethod() {
        try {
            Class<?> sourceClass = Class.forName("com.hook.zhihu.demo.Test");
            Class<?> targetClass = Class.forName("com.hook.zhihu.demo.Hook");
            Method sourceMethod = sourceClass.getDeclaredMethod("println");
            sourceMethod.setAccessible(true);
            Method targetMethod = targetClass.getDeclaredMethod("println");
            targetMethod.setAccessible(true);
            Hook.hook(sourceMethod,targetMethod);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
