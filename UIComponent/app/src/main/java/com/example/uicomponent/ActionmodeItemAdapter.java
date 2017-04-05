package com.example.uicomponent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ActionmodeItemAdapter extends ArrayAdapter<Animal> {
    private int resourseId;

    public ActionmodeItemAdapter(Context mContext, int textViewResourseId, List<Animal> objects) {
        super(mContext, textViewResourseId, objects);
        resourseId = textViewResourseId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Animal item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourseId, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.tv_actionmode_image);
        TextView textView = (TextView) view.findViewById(R.id.tv_actionmode_text);

        imageView.setImageResource(item.getImageId());
        textView.setText(item.getName());
        return view;
    }
}