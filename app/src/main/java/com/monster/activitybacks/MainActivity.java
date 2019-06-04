package com.monster.activitybacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.monster.activiyback.ActivityResultRequest;
import com.monster.activiyback_rx.ActivityResultNavigatorFragment;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ActivityResultRequest(this).startForResult(null, new ActivityResultRequest.Callback() {
            @java.lang.Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {

            }
        });

        new ActivityResultNavigatorFragment().startLoginForResult(this, null)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });

    }
}
