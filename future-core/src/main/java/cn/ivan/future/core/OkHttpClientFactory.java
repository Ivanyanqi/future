package cn.ivan.future.core;

import okhttp3.OkHttpClient;

/**
 * @author yanqi69
 * @date 2021/5/18
 */
public class OkHttpClientFactory {

    private volatile static OkHttpClient okHttpClient;

    public static OkHttpClient getInstance(){
        if(okHttpClient == null){
            synchronized (OkHttpClientFactory.class){
                if(okHttpClient == null){
                    return new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }



}
