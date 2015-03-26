/*CustomGridView.java
* Robert Brooks*/
package com.robertbrooks.actionbarspinnerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Bob on 3/26/2015.
 */
public class CustomGridView extends BaseAdapter {

    private Context context;
    private final int[] images;

    public CustomGridView(Context ctext, int[] images) {
        context = ctext;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view = new View(context);
            view = inflater.inflate(R.layout.grid_item_layout, null);
            ImageView imgView = (ImageView) view.findViewById(R.id.grid_image);
            imgView.setImageResource(images[position]);
        } else {
            view = (View) convertView;
        }
        return view;
    }
}
