package com.example.zhaoyigang.firsttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button linear;
    private Button relative;
    private Button table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        linear.setOnClickListener(this);
        relative.setOnClickListener(this);
        table.setOnClickListener(this);
    }

    private void init(){
        linear = (Button) findViewById(R.id.linear);
        relative = (Button) findViewById(R.id.relative);
        table = (Button) findViewById(R.id.table);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear:
                Intent intent1 = new Intent(this, LinearActivity.class);
                startActivity(intent1);
                break;
            case R.id.relative:
                Intent intent2 = new Intent(this, RelativeActivity.class);
                startActivity(intent2);
                break;
            case R.id.table:
                Intent intent3 = new Intent(this, TableLayout.class);
                startActivity(intent3);
                break;
        }
    }
}
