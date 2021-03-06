package com.example.sunny.fdgfg;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny on 2017/9/4.
 * activity 控制器
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAPP() {
        for (Activity activity: activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

}
