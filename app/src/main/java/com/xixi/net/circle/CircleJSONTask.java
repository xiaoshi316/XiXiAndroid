package com.xixi.net.circle;

import com.loopj.android.http.RequestParams;
import com.xixi.net.base.JSONReceiver;
import com.xixi.net.base.JSONTask;
import com.xixi.net.base.RequestUrl;

/**
 * Created by LiHao on 2015-7-21.
 */
public class CircleJSONTask extends JSONTask {

    public CircleJSONTask(int circleId, JSONReceiver receiver) {

        String url = RequestUrl.CIRCLE;

        RequestParams params = new RequestParams();
        params.put("xqzID", circleId);

        init(url, params, receiver);
    }

}
