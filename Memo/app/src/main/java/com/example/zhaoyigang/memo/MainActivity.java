package com.example.zhaoyigang.memo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhaoyigang.memo.base.BaseActivity;
import com.example.zhaoyigang.memo.bean.ArticleBean;
import com.example.zhaoyigang.memo.event.ChangeEvent;
import com.example.zhaoyigang.memo.event.RefreshEvent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.add)
    FloatingActionButton add;
    @BindView(R.id.edit_query)
    EditText editQuery;
    @BindView(R.id.img_query)
    ImageView imgQuery;
    private List<ArticleBean> datas;

    //适配器
    private CommonAdapter<ArticleBean> adapter;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void init() {
        add.setOnClickListener(this);
        imgQuery.setOnClickListener(this);
        //初始化toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        setupRecyclerview();
    }

    private void setupRecyclerview() {
        datas = new ArrayList<>();

        datas = DataSupport.findAll(ArticleBean.class);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);

        adapter = new CommonAdapter<ArticleBean>(this, R.layout.item_title, datas) {
            @Override
            protected void convert(ViewHolder holder, ArticleBean bean, int position) {

                holder.setText(R.id.title, bean.getTitle());

                holder.setText(R.id.time, getStrTime(bean.getTime()));
            }
        };

        recyclerview.setAdapter(adapter);

        //recyclerview点击事件
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //ContentActivity.actionAdd(getBaseContext(), Constants.TYPE.CHANGE);
                ArticleBean articleBean = new ArticleBean();
                articleBean = datas.get(position);
                ContentActivity.actionChange(getBaseContext(), Constants.TYPE.CHANGE, articleBean.getStart(),
                        articleBean.getTitle(), articleBean.getContent(), position);
            }

            //长按删除
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, final int position) {
                final String[] items = {"删除"};
                AlertDialog.Builder listDialog = new AlertDialog.Builder(MainActivity.this);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            DataSupport.deleteAll(ArticleBean.class, "start == ?", datas.get(position).getStart());

                            datas.remove(position);

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                listDialog.show();
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                //新建笔记
                ContentActivity.actionAdd(getBaseContext(), Constants.TYPE.ADD);
                break;
            case R.id.img_query:
                datas.clear();
                String editStr = String.valueOf(editQuery.getText());
                if (editStr == null){
                    datas = DataSupport.findAll(ArticleBean.class);
                }else {
                    List<ArticleBean> tmpList = new ArrayList<>();
                    tmpList = DataSupport.findAll(ArticleBean.class);
                    for (int i = 0; i < tmpList.size(); i++){
                        if (tmpList.get(i).getTitle().contains(editStr)){
                            datas.add(tmpList.get(i));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event) {

        datas.add(event.getArticleBean());

        //数据更新
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeEvent(ChangeEvent event) {

        datas.set(event.getPosition(), event.getArticleBean());

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //eventbus注册
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //eventbus注销
        EventBus.getDefault().unregister(this);
    }

    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time));
        return re_StrTime;
    }
}
