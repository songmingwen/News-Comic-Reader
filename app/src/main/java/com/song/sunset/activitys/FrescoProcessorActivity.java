package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.song.sunset.R;
import com.song.sunset.adapters.FrescoProcessorAdapter;

import java.util.ArrayList;

import jp.wasabeef.fresco.processors.BlurPostprocessor;
import jp.wasabeef.fresco.processors.ColorFilterPostprocessor;
import jp.wasabeef.fresco.processors.GrayscalePostprocessor;
import jp.wasabeef.fresco.processors.MaskPostprocessor;
import jp.wasabeef.fresco.processors.gpu.BrightnessFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.ContrastFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.InvertFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.KuawaharaFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.PixelationFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SepiaFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SketchFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SwirlFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.ToonFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.VignetteFilterPostprocessor;

public class FrescoProcessorActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, FrescoProcessorActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_processor);

        RecyclerView recyclerView = findViewById(R.id.id_recyclerview_processor);
        FrescoProcessorAdapter adapter = new FrescoProcessorAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<ImageRequest> list = new ArrayList<>();

        list.add(getProcessorImageRequest(null));
        list.add(getProcessorImageRequest(new BlurPostprocessor(this, 20)));                     //高斯
        list.add(getProcessorImageRequest(new MaskPostprocessor(this, R.drawable.end)));                //蒙版处理
        list.add(getProcessorImageRequest(new ColorFilterPostprocessor(R.color.color_smooth_red)));             //颜色筛选
        list.add(getProcessorImageRequest(new GrayscalePostprocessor()));                                       //灰度处理

        list.add(getProcessorImageRequest(new BrightnessFilterPostprocessor(this, 0.5f)));
        list.add(getProcessorImageRequest(new ContrastFilterPostprocessor(this, 2.0f)));
        list.add(getProcessorImageRequest(new InvertFilterPostprocessor(this)));
        list.add(getProcessorImageRequest(new KuawaharaFilterPostprocessor(this)));
        list.add(getProcessorImageRequest(new PixelationFilterPostprocessor(this)));
        list.add(getProcessorImageRequest(new SepiaFilterPostprocessor(this, 2.0f)));
        list.add(getProcessorImageRequest(new SketchFilterPostprocessor(this)));
        list.add(getProcessorImageRequest(new SwirlFilterPostprocessor(this)));
        list.add(getProcessorImageRequest(new ToonFilterPostprocessor(this)));
        list.add(getProcessorImageRequest(new VignetteFilterPostprocessor(this)));

        adapter.setData(list);
    }

    private ImageRequest getProcessorImageRequest(Postprocessor postprocessor) {
        return getBuilder()
                .setPostprocessor(postprocessor)
                .build();
    }

    private ImageRequestBuilder getBuilder() {
        return ImageRequestBuilder.newBuilderWithSource(getRes());
    }

    private Uri getRes() {
        return new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.animals_eagle)).build();
    }
}
