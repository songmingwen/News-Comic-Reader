package com.song.sunset.utils;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/15
 */
public class ShaderReader {

    public static String readTextFileFromResource(Context context, int resourceId) {

        StringBuilder stringBuilder = new StringBuilder();
        try {

            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(nextLine);
                stringBuilder.append('\n');
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not open resource:" + resourceId, e);
        } catch (Resources.NotFoundException e) {
            throw new RuntimeException("resource not found" + resourceId, e);
        }
        return stringBuilder.toString();
    }

}
