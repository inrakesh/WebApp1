package com.abaloneteam.webapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abalone Team on 02-08-2017.
 */

public class CustomImageListAdapter extends ArrayAdapter
{
    Context context;

    public CustomImageListAdapter(@NonNull Context context, @LayoutRes int resource , List<Integer> imageId) {
        super(context, resource,imageId);
        this.context = context;
    }

    private class ViewHolder {
        ImageView imageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int id  = (int) getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.imageView.setImageResource(id);

        return convertView;
    }
}
