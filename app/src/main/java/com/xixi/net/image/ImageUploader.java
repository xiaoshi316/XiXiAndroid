package com.xixi.net.image;

import com.loopj.android.http.RequestParams;
import com.xixi.net.JSONReceiver;
import com.xixi.util.SafeJSON;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * upload multiple images in one time sequentially
 */
public class ImageUploader {

    ArrayList<String> localUrls;
    ArrayList<String> receivedUrls;

    int imageCount;

    OnUploadFinishListener listener;

    private static ImageUploader imageUploader = new ImageUploader();

    private ImageUploader() {}

    public static ImageUploader getInstance() {
        return imageUploader;
    }

    public interface OnUploadFinishListener {
        public void onFailure();
        public void onSuccess(ArrayList<String> receivedUrls);
    }

    JSONReceiver receiver = new JSONReceiver() {
        @Override
        public void onFailure(JSONObject obj) {
            listener.onFailure();
        }
        @Override
        public void onSuccess(JSONObject obj) {
            int checked = SafeJSON.getInt(obj, "checked", 1);
            if (checked == 1) {
                receivedUrls = null;
                return;
            }
            String url = obj.optString("headPic");
            receivedUrls.add(url);
            imageCount++;
            if (imageCount < localUrls.size()) {
                uploadImage(imageCount);
            } else {
                listener.onSuccess(receivedUrls);
            }
        }
    };

    public void setOnUploadFinishListener(OnUploadFinishListener listener) {
        this.listener = listener;
    }

    public void execute(ArrayList<String> localUrls) {
        imageCount = 0;
        this.localUrls = localUrls;
        receivedUrls = new ArrayList<>();
        uploadImage(imageCount);
    }

    private void uploadImage(int imageCount) {
        RequestParams params = new RequestParams();
        File file = new File(localUrls.get(imageCount));
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new ImageUploadTask(params, receiver).execute();
    }

}