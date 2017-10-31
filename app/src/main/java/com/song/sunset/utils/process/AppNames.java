/*
 * Copyright (C) 2015. Jared Rummler <jared.rummler@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.song.sunset.utils.process;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.song.sunset.utils.process.models.AndroidAppProcess;

import java.util.Hashtable;

public class AppNames {

    static final Hashtable<String, String> APP_NAME_CACHE = new Hashtable<>();

    private static String getLabel(PackageManager pm, PackageInfo packageInfo) {
        if (APP_NAME_CACHE.containsKey(packageInfo.packageName)) {
            return APP_NAME_CACHE.get(packageInfo.packageName);
        }
        String label = packageInfo.applicationInfo.loadLabel(pm).toString();
        APP_NAME_CACHE.put(packageInfo.packageName, label);
        return label;
    }

    public static String getName(Context context, AndroidAppProcess process) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = process.getPackageInfo(context, 0);
            return AppNames.getLabel(pm, packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            return process.name;
        }
    }

    public static String getPackageName(Context context, AndroidAppProcess process){
        try {
            PackageInfo packageInfo = process.getPackageInfo(context, 0);
            return packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            return process.name;
        }
    }

}
