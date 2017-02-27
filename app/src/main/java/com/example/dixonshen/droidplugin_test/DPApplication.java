package com.example.dixonshen.droidplugin_test;

import android.app.Application;
import android.content.Context;

import com.morgoo.droidplugin.PluginHelper;

/**
 * Created by Z003R98D on 2/27/2017.
 */

public class DPApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // 这里必须在super.onCreate方法之后，顺序不能变
        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
    }
}
