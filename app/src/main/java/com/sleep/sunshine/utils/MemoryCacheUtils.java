package com.sleep.sunshine.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by lifan on 2017/9/5.
 * 三级缓存之内存缓存
 */

public class MemoryCacheUtils {

    //1.因为强引用,容易造成内存溢出，所以考虑使用下面弱引用的方法
    // private HashMap<String,Bitmap> mMemoryCache=new HashMap<>();
    //2.因为在Android2.3+后,系统会优先考虑回收弱引用对象,官方提出使用LruCache
    // private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<>();
    private LruCache<String,Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        //得到手机最大允许内存的1/8,即超过指定内存,则开始回收
        long maxMemory = Runtime.getRuntime().maxMemory()/8;
        mMemoryCache = new LruCache<String, Bitmap>((int)maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };
    }

    /**
     * 从内存中读取图片
     * @param url
     * @return
     */
    public Bitmap getBitMapFromMemory(String url) {
        //Bitmap bitmap = mMemoryCache.get(url);//1.强引用方法
        /**2.弱引用方法
        SoftReference<Bitmap> bitmapSoftReference = mMemoryCache.get(url);
        if(bitmapSoftReference != null){
            Bitmap bitmap = bitmapSoftReference.get();
            return bitmap
        }
         */
        Bitmap bitmap = mMemoryCache.get(url);
        return bitmap;
    }

    /**
     * 往内存中写图片
     * @param url
     * @param bitmap
     */
    public void setBitMapToMemory(String url, Bitmap bitmap) {
        //mMemoryCache.put(url, bitmap);//1.强引用方法
            /*2.弱引用方法
            mMemoryCache.put(url, new SoftReference<>(bitmap));
            */
        mMemoryCache.put(url,bitmap);
    }
}
