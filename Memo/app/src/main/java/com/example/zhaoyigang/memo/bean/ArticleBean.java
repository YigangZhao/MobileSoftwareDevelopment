package com.example.zhaoyigang.memo.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by zhaoyigang on 2017/5/9.
 */

public class ArticleBean extends DataSupport{

    private String start;
    private String title;
    private String time;
    private String content;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
