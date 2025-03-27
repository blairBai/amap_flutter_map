package com.amap.flutter.map;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.amap.flutter.map.utils.LogUtil;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;

/**
 * AmapFlutterMapPlugin
 */
public class AMapFlutterMapPlugin implements FlutterPlugin, ActivityAware {
    private static final String CLASS_NAME = "AMapFlutterMapPlugin";
    private static final String VIEW_TYPE = "com.amap.flutter.map";

    private FlutterPluginBinding pluginBinding;
    private Activity activity;
    private Lifecycle lifecycle;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        LogUtil.i(CLASS_NAME, "onAttachedToEngine==>");
        this.pluginBinding = binding;
        this.lifecycle = ProcessLifecycleOwner.get().getLifecycle(); // ✅ 使用 ProcessLifecycleOwner

        registerViewFactory(binding.getBinaryMessenger(), binding.getApplicationContext(), lifecycle);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        LogUtil.i(CLASS_NAME, "onDetachedFromEngine==>");
        this.pluginBinding = null;
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        LogUtil.i(CLASS_NAME, "onAttachedToActivity==>");
        this.activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
        LogUtil.i(CLASS_NAME, "onDetachedFromActivity==>");
        this.activity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        LogUtil.i(CLASS_NAME, "onReattachedToActivityForConfigChanges==>");
        onAttachedToActivity(binding);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        LogUtil.i(CLASS_NAME, "onDetachedFromActivityForConfigChanges==>");
        this.onDetachedFromActivity();
    }

    private void registerViewFactory(BinaryMessenger messenger, Context context, Lifecycle lifecycle) {
        pluginBinding.getPlatformViewRegistry().registerViewFactory(
                VIEW_TYPE,
                new AMapPlatformViewFactory(messenger, () -> lifecycle));
    }
}
