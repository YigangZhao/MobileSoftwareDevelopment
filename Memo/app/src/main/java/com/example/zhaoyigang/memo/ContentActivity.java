package com.example.zhaoyigang.memo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaoyigang.memo.base.BaseActivity;
import com.example.zhaoyigang.memo.bean.ArticleBean;
import com.example.zhaoyigang.memo.event.ChangeEvent;
import com.example.zhaoyigang.memo.event.RefreshEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by zhaoyigang on 2017/5/9.
 */

public class ContentActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.mode)
    ImageView mode;

    private Intent intent;

    private boolean MODE = false;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_content);
    }

    @Override
    public void init() {
        intent = getIntent();
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        mode.setOnClickListener(this);
        //初始化toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        if (intent.getIntExtra("type", 0) == Constants.TYPE.CHANGE) {
            title.setText(intent.getStringExtra("title"));
            content.setText(intent.getStringExtra("content"));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (intent.getIntExtra("type", 0) == Constants.TYPE.ADD) {
                    showBackDialog();
                } else if (intent.getIntExtra("type", 0) == Constants.TYPE.CHANGE) {
                    finish();
                }
                break;
            case R.id.save:
                int type = intent.getIntExtra("type", 0);
                if (type == Constants.TYPE.ADD) {
                    add();
                } else if (type == Constants.TYPE.CHANGE) {
                    change();
                }
                //Toast.makeText(this, "保存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mode:
                if (MODE){
                    title.setTextColor(Color.GRAY);
                    content.setTextColor(Color.GRAY);
                    title.setBackgroundColor(Color.WHITE);
                    content.setBackgroundColor(Color.WHITE);
                    mode.setImageResource(R.drawable.morning);
                    MODE = false;
                }else {
                    title.setTextColor(Color.WHITE);
                    content.setTextColor(Color.WHITE);
                    title.setBackgroundColor(Color.BLACK);
                    content.setBackgroundColor(Color.BLACK);
                    mode.setImageResource(R.drawable.night);
                    MODE = true;
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 &&
                intent.getIntExtra("type", 0) == Constants.TYPE.ADD) {
            showBackDialog();
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    private void showBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("还未保存是否退出")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();

    }

    private void change() {
        String timeStr = String.valueOf(System.currentTimeMillis());
        String titleStr = String.valueOf(title.getText());
        String contentStr = String.valueOf(content.getText());

        if (titleStr.isEmpty() || contentStr.isEmpty()) {
            Toast.makeText(getBaseContext(), "内容不能为空", Toast.LENGTH_LONG).show();
        } else {
            ArticleBean bean = new ArticleBean();
            bean.setTitle(titleStr);
            bean.setTime(timeStr);
            bean.setContent(contentStr);
            bean.updateAll("start == ?", intent.getStringExtra("start"));

            //发送更新事件
            ChangeEvent changeEvent = new ChangeEvent();
            changeEvent.setPosition(intent.getIntExtra("position", -1));

            changeEvent.setArticleBean(bean);
            EventBus.getDefault().post(changeEvent);

            finish();
        }
    }

    private void add() {

        String timeStr = String.valueOf(System.currentTimeMillis());
        String titleStr = String.valueOf(title.getText());
        String contentStr = String.valueOf(content.getText());

        if (titleStr.isEmpty() || contentStr.isEmpty()) {
            Toast.makeText(getBaseContext(), "内容不能为空", Toast.LENGTH_LONG).show();
        } else {
            ArticleBean bean = new ArticleBean();
            bean.setStart(timeStr);
            bean.setTitle(titleStr);
            bean.setTime(timeStr);
            bean.setContent(contentStr);
            bean.save();

            //发送新建事件
            RefreshEvent refreshEvent = new RefreshEvent();
            refreshEvent.setArticleBean(bean);
            EventBus.getDefault().post(refreshEvent);

            finish();
        }
    }

    public static void actionChange(Context context, int type, String start, String title, String content, int position) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type", type);
        intent.putExtra("start", start);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    public static void actionAdd(Context context, int type) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }
}
