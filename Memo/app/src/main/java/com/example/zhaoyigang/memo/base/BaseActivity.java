package com.example.zhaoyigang.memo.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by zhaoyigang on 2017/5/8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 初始化布局
     */
    public abstract void initContentView();

    /**
     * 初始化
     */
    public abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContentView();
        //butterknife注入
        ButterKnife.bind(this);
        init();
    }
}
