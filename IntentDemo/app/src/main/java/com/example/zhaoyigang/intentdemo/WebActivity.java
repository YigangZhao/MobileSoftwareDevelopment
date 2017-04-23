package com.example.zhaoyigang.intentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zhaoyigang on 2017/4/16.
 */

public class WebActivity extends AppCompatActivity{

    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();

        webView.getSettings().setJavaScriptEnabled(true);
        //String string = intent.getStringExtra("uri");
        webView.loadUrl(intent.getStringExtra("uri"));
        webView.setWebViewClient(new WebViewClient());
    }

    public static void actionStart(Context context, String uri){
        Intent intent = new Intent(context, WebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("uri", uri);
        context.startActivity(intent);
    }

}
