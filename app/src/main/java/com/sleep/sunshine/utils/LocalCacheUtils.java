package com.sleep.sunshine.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by lifan on 2017/9/5.
 * 三级缓存之本地缓存
 */

public class LocalCacheUtils {

    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/webNews";

    /**
     * 从本地读取图片
     * @param url
     * @return
     */
    public Bitmap getBitMapFromLocal(String url) {
        String fileName = null;//把图片的url当做文件名，并进行MD5加密
        try {
            fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH,fileName);

            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存图片至本地
     * @param url
     * @param bitmap
     */
    public void setBitMapToLocal(String url, Bitmap bitmap) {
        try {
            String fileName = MD5Encoder.encode(url);//把图片的url当做文件名，并进行MD5加密
            File file = new File(CACHE_PATH,fileName);

            //通过得到文件的父文件，判断父文件是否存在
            File parentFile = file.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
