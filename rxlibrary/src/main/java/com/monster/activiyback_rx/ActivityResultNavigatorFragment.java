package com.monster.activiyback_rx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

import static android.app.Activity.RESULT_OK;

public class ActivityResultNavigatorFragment extends Fragment {
    public static final String KEY_RETURN_VALUE = "return_value";
    private static final String TAG = "ActivityResultNavigatorFragment";

    private PublishSubject<Boolean> resultSubject;
    private PublishSubject<Boolean> cancelSubject;
    private PublishSubject<Boolean> attachSubject = PublishSubject.create();

    public Single<Boolean> startLoginForResult(Intent intent,FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(this, TAG).commitAllowingStateLoss();
            return startLoginSingle(intent);
        } else {
            return ((ActivityResultNavigatorFragment) fragment).startLoginSingle(intent);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachSubject.onNext(true);
        attachSubject.onComplete();
    }

    public Single<Boolean> startLoginSingle(Intent intent) {
        resultSubject = PublishSubject.create();
        cancelSubject = PublishSubject.create();
        startLogin(intent);
        return resultSubject
                .takeUntil(cancelSubject)
                .single(false);
    }

    @SuppressLint("CheckResult")
    public void startLogin(final Intent intent) {
        if (!isAdded()) {
            attachSubject.subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    startLoginForResult(intent);
                }
            });
        } else {
            startLoginForResult(intent);
        }
    }

    private void startLoginForResult(final Intent intent) {
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            boolean isLogin = data.getBooleanExtra(KEY_RETURN_VALUE, false);
            resultSubject.onNext(isLogin);
            resultSubject.onComplete();
        } else {
            cancelSubject.onNext(true);
        }
    }
}
