package com.baway_04.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.baway_04.R;
import com.baway_04.bean.UserInfo;
import com.baway_04.databinding.MvvmActivityBinding;

public class MVVMActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MvvmActivityBinding mvvmActivityBinding = DataBindingUtil.setContentView(this, R.layout.mvvm_activity);
        UserInfo userInfo = new UserInfo();
        userInfo.setName("IIIII");

        mvvmActivityBinding.setUser(userInfo);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo();
                userInfo.setName("TTTTT");
                mvvmActivityBinding.setUser(userInfo);
            }
        });
    }
}
