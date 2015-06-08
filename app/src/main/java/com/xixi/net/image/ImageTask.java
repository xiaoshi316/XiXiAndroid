package com.xixi.net.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.xixi.net.API;
import com.xixi.util.BitmapReceiver;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageTask {

    public int count = 0;

    private String url;

    private String absUrl;

    private BitmapReceiver bitmapReceiver;

    private AsyncHttpResponseHandler asyncHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            Log.i(getClass().toString(), "onFailure");
            bitmapReceiver.onFailure();
            count--;
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Log.i(getClass().toString(), "onSuccess");
            if (arg2 == null) {
                bitmapReceiver.onFailure();
                return;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeByteArray(arg2, 0, arg2.length);
            bitmapReceiver.onSuccess(url, bitmap);
            count--;
        }

    };

    public ImageTask(String url, BitmapReceiver bitmapReceiver) {
        this.url = url;
        this.absUrl = API.HOST + url;
        this.bitmapReceiver = bitmapReceiver;
        count++;
    }

    public void execute() {
        new AsyncHttpClient().get(absUrl, asyncHandler );
    }

}