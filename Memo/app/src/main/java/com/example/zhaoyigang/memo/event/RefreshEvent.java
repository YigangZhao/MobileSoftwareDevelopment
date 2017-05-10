package com.example.zhaoyigang.memo.event;

import com.example.zhaoyigang.memo.bean.ArticleBean;

/**
 * Created by zhaoyigang on 2017/5/10.
 */

public class RefreshEvent {

    private ArticleBean articleBean;

    public ArticleBean getArticleBean() {
        return articleBean;
    }

    public void setArticleBean(ArticleBean articleBean) {
        this.articleBean = articleBean;
    }
}
