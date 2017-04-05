package com.example.uicomponent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peige on 2017/3/16.
 */

public class AnimalAdapter extends ArrayAdapter<Animal> {

    private int resourceId;

    public AnimalAdapter(Context context, int resource, List<Animal> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Animal animal = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.animalImage = (ImageView) view.findViewById(R.id.img_animal_avatar);
            viewHolder.animalName = (TextView) view.findViewById(R.id.tv_animal_name);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.animalImage.setImageResource(animal.getImageId());
        viewHolder.animalName.setText(animal.getName());
        return view;
    }

    private class ViewHolder {
        ImageView animalImage;
        TextView animalName;
    }

}







