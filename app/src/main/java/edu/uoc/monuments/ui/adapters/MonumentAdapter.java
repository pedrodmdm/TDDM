package edu.uoc.monuments.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uoc.library.LibraryManager;
import edu.uoc.library.model.Monument;
import edu.uoc.monuments.R;

/**
 * Created by UOC on 28/09/2016.
 */
public class MonumentAdapter extends ArrayAdapter<Monument> {

    private Context context;
    private ArrayList<Monument> monumentList;

    public MonumentAdapter(Context context, ArrayList<Monument> monumentList) {
        super(context, R.layout.adapter_monument);
        this.context = context;
        this.monumentList = monumentList;
    }

    @Override
    public int getCount() {
        return monumentList.size();
    }

    @Override
    public Monument getItem(int position) {
        return monumentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monumentList.get(position).getId();
    }

    public synchronized void setMonuments(List<Monument> monuments) {
        monumentList.clear();
        monumentList.addAll(monuments);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder viewHolder;

        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_monument, parent, false);
            viewHolder.mName = (TextView) view.findViewById(R.id.name);
            viewHolder.mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }
        // Get monument object
        Monument monument = monumentList.get(position);
        // Set text with the item name
        viewHolder.mName.setText(monument.getName());
        // Set image
        Bitmap image = LibraryManager.getInstance(context).getImage(monument.getImagePath());
        viewHolder.mThumbnail.setImageBitmap(image);
        return view;
    }

    static class ViewHolder {
        protected ImageView mThumbnail;
        protected TextView mName;
    }
}
