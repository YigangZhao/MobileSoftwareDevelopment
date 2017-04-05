package com.example.uicomponent;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_listView;
    Button btn_alertDialog;
    Button btn_menu;
    Button btn_actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn_listView = (Button) findViewById(R.id.btn_listView);
        btn_listView.setOnClickListener(this);
        btn_alertDialog = (Button) findViewById(R.id.btn_alertDialog);
        btn_alertDialog.setOnClickListener(this);
        btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);
        btn_actionMode = (Button) findViewById(R.id.btn_action_mode);
        btn_actionMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_listView:
            {
                Intent intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
            } break;

            case R.id.btn_alertDialog:
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setView(R.layout.dialog_signin);
                dialog.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Clicked Sign in.", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Clicked Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            } break;

            case R.id.btn_menu:
            {
                Intent intent = new Intent(this, MenuAcitvity.class);
                startActivity(intent);
            } break;

            case R.id.btn_action_mode:
            {
                Intent intent = new Intent(this, ActionModeActivity.class);
                startActivity(intent);
            } break;
        }
    }
}
