package com.example.uicomponent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peige on 2017/4/5.
 */

public class ActionModeActivity extends AppCompatActivity
        implements ListView.OnItemLongClickListener {

    private List<Animal> mItemList = new ArrayList<>();
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_mode);
        startSupportActionMode(mCallback);
        initItem();
        ActionmodeItemAdapter actionModeItemAdapter = new ActionmodeItemAdapter(ActionModeActivity
                .this, R.layout.item_action_mode, mItemList);
        ListView listView = (ListView) findViewById(R.id.action_mode_list_view);
        listView.setAdapter(actionModeItemAdapter);
        listView.setOnItemLongClickListener(this);

    }

    private ActionMode.Callback mCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
            mode.setTitle(cnt + " selected");
            getMenuInflater().inflate(R.menu.action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
            mode.setTitle(cnt + " selected");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            mode.setTitle(cnt + " selected");
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    };

    private void initItem() {
        Animal one = new Animal("1", R.mipmap.ic_launcher);
        Animal two = new Animal("22", R.mipmap.ic_launcher);
        Animal three = new Animal("333", R.mipmap.ic_launcher);
        Animal four = new Animal("4444", R.mipmap.ic_launcher);
        Animal five = new Animal("55555", R.mipmap.ic_launcher);
        Animal six = new Animal("666666", R.mipmap.ic_launcher);
        Animal seven = new Animal("7777777", R.mipmap.ic_launcher);
        mItemList.add(one);
        mItemList.add(two);
        mItemList.add(three);
        mItemList.add(four);
        mItemList.add(five);
        mItemList.add(six);
        mItemList.add(seven);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        cnt++;
        return false;
    }

}
