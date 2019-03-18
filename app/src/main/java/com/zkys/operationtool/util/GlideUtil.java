package com.zkys.operationtool.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zkys.operationtool.R;

public class GlideUtil {
    private static GlideUtil instance;
    RequestOptions options;
    Context mContext;

    private GlideUtil(Context context){
        options = new RequestOptions();
        options.skipMemoryCache(false);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.priority(Priority.HIGH);
        options.error(R.mipmap.img_null_dsh);
        //设置占位符,默认
        options.placeholder(R.mipmap.img_null_dsh);
        //设置错误符,默认
        options.error(R.mipmap.img_null_dsh);
        mContext=context;
    }

    public static GlideUtil getInstance(Context context){
        if (instance==null){
            synchronized (GlideUtil.class){
                if (instance==null){
                    instance=new GlideUtil(context);
                }
            }
        }
        instance.mContext = context;
        return instance;
    }

    //设置占位符
    public void setPlaceholder(int id){
        options.placeholder(id);
    }
    public void setPlaceholder(Drawable drawable){
        options.placeholder(drawable);
    }

    //设置错误符
    public void setError(int id){
        options.error(id);
    }

    public void setError(Drawable drawable){
        options.error(drawable);
    }

    public void showImage(String url, ImageView imageView){

        GlideApp.with(mContext)
                .load(url)
                .apply(options)
                .into(imageView);

    }

    //以图片宽度为基准
    public void showImageWidthRatio(String url, final ImageView imageView, final int width){
        GlideApp.with(mContext)
                .asBitmap()
                .apply(options)
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth=resource.getWidth();
                        int imageHeight=resource.getHeight();
                        int height = width * imageHeight / imageWidth;
                        ViewGroup.LayoutParams params=imageView.getLayoutParams();
                        params.height=height;
                        params.width=width;
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    //以图片高度为基准
    public void showImageHeightRatio(String url, final ImageView imageView, final int height){
        GlideApp.with(mContext)
                .asBitmap()
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth=resource.getWidth();
                        int imageHeight=resource.getHeight();
                        int width = height * imageHeight / imageWidth;
                        ViewGroup.LayoutParams params=imageView.getLayoutParams();
                        params.height=height;
                        params.width=width;
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    //设置图片固定的大小尺寸
    public void showImageWH(String url, final ImageView imageView, int height,int width){

        options.override(width,height);
        GlideApp.with(mContext)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    //设置图片圆角，以及弧度
    public void showImageRound(String url, final ImageView imageView, int radius){

        options.transform(new CornersTranform(radius));
//        options.transform(new GlideCircleTransform());
        GlideApp.with(mContext)
                .load(url)
                .apply(options)
                .into(imageView);

    }

    public void showImageRound(String url, final ImageView imageView,int radius, int height,int width){
        //不一定有效，当原始图片为长方形时设置无效
        options.override(width,height);
        options.transform(new CornersTranform(radius));
//        options.centerCrop(); //不能与圆角共存
        GlideApp.with(mContext)
                .load(url)
                .apply(options)
                .into(imageView);

    }


    public void showImageRound(String url, final ImageView imageView){
        //自带圆角方法，显示圆形
        options.circleCrop();
        GlideApp.with(mContext)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
