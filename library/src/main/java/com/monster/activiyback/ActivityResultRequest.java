package com.monster.activiyback;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

/**
 * <p>
 * <h2>简述:</h2>
 * <ol>无</ol>
 * <h2>功能描述:</h2>
 * <ol>无</ol>
 * <h2>修改历史:</h2>
 * <ol>无</ol>
 * </p>
 *
 * @author 11925
 * @date 2018/8/14/014
 */
public class ActivityResultRequest {

    private OnActResultEventDispatcherFragment fragment;

    public ActivityResultRequest(Activity activity) {
        fragment = getEventDispatchFragment(activity);
    }

    private OnActResultEventDispatcherFragment getEventDispatchFragment(Activity activity) {
        final FragmentManager fragmentManager = activity.getFragmentManager();

        OnActResultEventDispatcherFragment fragment = findEventDispatchFragment(fragmentManager);
        if (fragment == null) {
            fragment = new OnActResultEventDispatcherFragment();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, OnActResultEventDispatcherFragment.TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    private OnActResultEventDispatcherFragment findEventDispatchFragment(FragmentManager manager) {
        return (OnActResultEventDispatcherFragment) manager.findFragmentByTag(OnActResultEventDispatcherFragment.TAG);
    }

    public void startForResult(Intent intent, Callback callback) {
        fragment.startForResult(intent, callback);
    }

    public interface Callback {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
