package com.monster.activiyback;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

/**
 * <p>
 * <h2>简述:</h2>
 * <ol>onActivityResult 的快速返回</ol>
 * <h2>功能描述:</h2>
 * <ol>无</ol>
 * <h2>修改历史:</h2>
 * <ol>无</ol>
 * </p>
 *
 * @author 11925
 * @date 2018/8/14/014
 */
public class OnActResultEventDispatcherFragment extends Fragment {
    public static final int ActivityNotFoundCode = -404;
    public static final String TAG = "on_act_result_event_dispatcher";

    private SparseArray<ActivityResultRequest.Callback> mCallbacks = new SparseArray<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startForResult(Intent intent, ActivityResultRequest.Callback callback) {
        mCallbacks.put(callback.hashCode(), callback);
        try {
            startActivityForResult(intent, callback.hashCode());
        } catch (ActivityNotFoundException e) {
            callback.onActivityResult(callback.hashCode(),ActivityNotFoundCode, null);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActivityResultRequest.Callback callback = mCallbacks.get(requestCode);


        if (callback != null) {
            callback.onActivityResult(callback.hashCode(),resultCode, data);
        }
        mCallbacks.remove(requestCode);
    }

}

