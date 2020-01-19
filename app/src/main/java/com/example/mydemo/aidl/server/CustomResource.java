package com.example.mydemo.aidl.server;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 创建时间：2020-01-09
 * author: wuxiangyu.lc
 * describe:
 */
public class CustomResource extends Resources {
    private Application mApplication;

    public CustomResource(Application application, AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
        this.mApplication = application;
    }

    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     * @param assets  Previously created AssetManager.
     * @param metrics Current display metrics to consider when
     *                selecting/computing resource values.
     * @param config  Desired device configuration to consider when
     * @deprecated Resources should not be constructed by apps.
     * See {@link Context#createConfigurationContext(Configuration)}.
     */
    public CustomResource(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }
//
//    @Override
//    public int getColor(int id) throws NotFoundException {
//        int originColor = mApplication.getResources().getColor(id);
//        //是否没有下载皮肤或者当前使用默认皮肤
//        if (mResources == null) {
//            return originColor;
//        }
//        //根据resId值获取对应的xml的的@+id的String类型的值
//        String resName = mApplication.getResources().getResourceEntryName(id);
//        //更具resName在皮肤包的mResources中获取对应的resId
//        int trueResId = mResources.getIdentifier(resName, "color", "target.skin.backage");
//        int trueColor = 0;
//        try {
//            //根据resId获取对应的资源value
//            trueColor = mResources.getColor(trueResId);
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//            trueColor = originColor;
//        }
//
//        return trueColor;
//    }
}
