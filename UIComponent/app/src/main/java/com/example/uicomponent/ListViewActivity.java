package com.example.uicomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peige on 2017/3/16.
 */

public class ListViewActivity extends AppCompatActivity
        implements ListView.OnItemClickListener {

    private ListView listView;
    private List<Animal> mAnimalList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        init();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        mAnimalList = new ArrayList<>();
        AnimalAdapter animalAdapter = new AnimalAdapter(this, R.layout.item_animal, mAnimalList);
        initAnimals();

        listView.setAdapter(animalAdapter);
    }

    private void initAnimals() {
        for(int i = 0; i < 2; ++i) {
            mAnimalList.add(new Animal("Cat", R.drawable.cat));
            mAnimalList.add(new Animal("Dog", R.drawable.dog));
            mAnimalList.add(new Animal("Elephant", R.drawable.elephant));
            mAnimalList.add(new Animal("Lion", R.drawable.lion));
            mAnimalList.add(new Animal("Monkey", R.drawable.monkey));
            mAnimalList.add(new Animal("Tiger", R.drawable.tiger));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Animal animal = mAnimalList.get(position);
        Toast.makeText(this, animal.getName(), Toast.LENGTH_SHORT).show();
    }

}
