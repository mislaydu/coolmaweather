package com.mislaydu.coolmaweather.util;

/**
 * Created by mislay on 2016/7/15.
 */
public interface HttpCallbackListenner {
    void onFinish(String response);
    void onError(Exception e);
}
