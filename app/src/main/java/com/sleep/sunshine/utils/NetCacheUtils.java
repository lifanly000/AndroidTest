package com.sleep.sunshine.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lifan on 2017/9/5.
 * 三级缓存之网络缓存
 */

public class NetCacheUtils {

    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    /**
     * 从网络下载图片
     * @param ivPic
     * @param url
     */
    public void getBitMapFromNet(ImageView ivPic, String url) {
        new BitMapTask().execute(ivPic,url);
    }

    class BitMapTask extends AsyncTask<Object,Void,Bitmap>{

        private ImageView ivPic;
        private String url;

        /**
         * 后台耗时操作，存在于子线程中
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object... params) {
            ivPic = (ImageView) params[0];
            url = (String) params[1];
            return downLoadBitmap(url);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 耗时方法结束后执行该方法，主线程中
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap !=null){
                ivPic.setImageBitmap(bitmap);
                System.out.println("从网络缓存图片啦.....");
                //从网络获取图片后,保存至本地缓存
                mLocalCacheUtils.setBitMapToLocal(url, bitmap);
                //从网络获取图片后，保存至内存中
                mMemoryCacheUtils.setBitMapToMemory(url,bitmap);
            }
        }

        /**
         * 网络下载图片
         * @param url
         * @return
         */
        private Bitmap downLoadBitmap(String url) {
            HttpURLConnection conn =null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if(responseCode ==200){
                    //图片压缩
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;//宽高压缩为原来的1/2
                    options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                    Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(),null,options);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(conn !=null)
                    conn.disconnect();
            }
            return null;
        }
    }
}
