### 期中作业-笔记本
#### 功能介绍
1. 新建笔记
2. 删除笔记
3. 修改笔记
4. 模糊查询笔记
5. 夜间模式

#### 详细分析

##### 用到的开源库
> butterknife http://jakewharton.github.io/butterknife/

> eventbus https://github.com/greenrobot/EventBus

> 万能adapter https://github.com/hongyangAndroid/baseAdapter

> litePal https://github.com/LitePalFramework/LitePal#latest-downloads/

##### 重要源码分析
> BaseActivity
>> 所有的activity都继承BaseActivity,initContentView()用来初始化布局界面，init()用来初始化其他的数据,oncreate设置butterknife


    public abstract class BaseActivity extends     AppCompatActivity {
    
        /**
         * 初始化布局
         */
        public abstract void initContentView();
    
        /**
         * 初始化
         */
        public abstract void init();
    
        @Override
        protected void onCreate(@Nullable Bundle     savedInstanceState) {
            super.onCreate(savedInstanceState);
    
            initContentView();
            //butterknife注入
            ButterKnife.bind(this);
            init();
        }
    }

> 添加笔记
>> 项目用了litepal的数据库，相比于sqlite它的使用更加灵活方便，它采用了对象关系映射(ORM)的模式，可以看到代码中添加数据到数据库的过程是创建一个对象，然后调用save()就保存到了数据库中，使用非常的方便，这里的save()方法是对象拓展自DataSupport。
    
    public class ArticleBean extends DataSupport{
    ...
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
    
> 修改笔记
>> 修改笔记后发送eventbus到mainactivity执行ui的更新

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
    
    
> 删除笔记
>> 监听recyclerview长按操作弹出dialog执行删除操作

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

> 查询笔记
>> 查询的内容为空时，返回的是全部笔记。如果不为空，获取笔记的所有标题，通过输入的查询内容，模糊匹配对应的标题得到查询的结果。

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
    
> 界面更新
>> 不管添加删除还是修改笔记，回到主界面总是更新数据，这里我用到eventbus来通知更新，如下面的代码，在contentactivity中新建一个refreshevent然后post出去，在mainactivity中接收到执行更新操作。

    //发送新建事件
    RefreshEvent refreshEvent = new RefreshEvent();
    refreshEvent.setArticleBean(bean);
    EventBus.getDefault().post(refreshEvent);
    
    
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event) {

        datas.add(event.getArticleBean());

        //数据更新
        adapter.notifyDataSetChanged();
    }
    
> 夜间模式
>> 夜间模式修改了笔记的背景颜色和字体颜色

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
        
#### 软件截图
![主界面](https://github.com/YigangZhao/MobileSoftwareDevelopment/blob/master/Memo/%E5%9B%BE%E7%89%87/%E4%B8%BB%E7%95%8C%E9%9D%A2.png)

![删除笔记](https://github.com/YigangZhao/MobileSoftwareDevelopment/blob/master/Memo/%E5%9B%BE%E7%89%87/%E5%88%A0%E9%99%A4.png)

![夜间模式](https://github.com/YigangZhao/MobileSoftwareDevelopment/blob/master/Memo/%E5%9B%BE%E7%89%87/%E5%A4%9C%E9%97%B4%E6%A8%A1%E5%BC%8F.png)

![新建编辑笔记](https://github.com/YigangZhao/MobileSoftwareDevelopment/blob/master/Memo/%E5%9B%BE%E7%89%87/%E6%96%B0%E5%BB%BA.png)

![查询笔记](https://github.com/YigangZhao/MobileSoftwareDevelopment/blob/master/Memo/%E5%9B%BE%E7%89%87/%E6%9F%A5%E6%89%BE.png)
