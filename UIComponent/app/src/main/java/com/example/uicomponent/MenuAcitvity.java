package com.example.uicomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by peige on 2017/4/5.
 */

public class MenuAcitvity extends AppCompatActivity {

    TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tvMsg = (TextView) findViewById(R.id.menu_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.one:
                tvMsg.setTextSize(10);
                break;
            case R.id.two:
                tvMsg.setTextSize(20);
                break;
            case R.id.three:
                tvMsg.setTextSize(30);
                break;
            case R.id.dark:
                tvMsg.setTextColor(0xFF000000);
                break;
            case R.id.red:
                tvMsg.setTextColor(0xFFFF0000);
                break;
            case R.id.men_normalMenu:
                Toast.makeText(this, "You clicked menu", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
