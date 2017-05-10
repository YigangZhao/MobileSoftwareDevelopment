package com.example.zhaoyigang.memo.event;

import com.example.zhaoyigang.memo.bean.ArticleBean;

/**
 * Created by zhaoyigang on 2017/5/10.
 */

public class ChangeEvent {

    private ArticleBean articleBean;
    private int position;

    public ArticleBean getArticleBean() {
        return articleBean;
    }

    public void setArticleBean(ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
