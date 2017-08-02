package com.abaloneteam.webapp;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Abalone Team on 02-08-2017.
 */

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{
    Context context;
    List<Integer> imageList;

    public ImageAdapter(Context context,List<Integer> imageList)
    {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position)
    {
        holder.imageView.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        public ImageViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
